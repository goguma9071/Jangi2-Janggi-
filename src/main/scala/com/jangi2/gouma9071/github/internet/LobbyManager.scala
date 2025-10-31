package com.jangi2.gouma9071.github.internet

import akka.actor.Timers
import akka.actor.typed.scaladsl.{ActorContext, Behaviors, TimerScheduler}
import akka.actor.typed.{ActorRef, Behavior}
import akka.io.{IO, Udp}
import akka.util.ByteString

import java.net.InetSocketAddress
import scala.concurrent.duration._
import akka.actor.typed.scaladsl.adapter._ // 클래식 <-> 타입 액터 전환용

object LobbyManager {

  // --- 외부에서 받을 메시지 ---
  sealed trait Command
  case object StartDiscovery extends Command

  // --- 내부에서 사용할 메시지 ---
  private case object BroadcastTick extends Command
  private case class Bound(address: InetSocketAddress, socket: ActorRef[Udp.Command]) extends Command
  private case class BindFailed(command: Udp.Command) extends Command
  private case class Packet(data: ByteString, from: InetSocketAddress) extends Command


  // --- '포트 열리기 전'의 초기 행동 ---
  def apply(): Behavior[Command] =
    Behaviors.withTimers { timers =>
      Behaviors.setup { context =>

        // Udp.Bind의 결과를 처리할 임시 핸들러. classic actor로 만들어야 sender()를 호출할 수 있다.
        val classicBindHandler = context.actorOf(akka.actor.Props(new akka.actor.Actor {
          override def receive: Receive = {
            case Udp.Bound(localAddress) =>
              // 성공 시, 부모(LobbyManager)에게 소켓 액터의 주소(sender())를 담아 Bound 메시지 전송
              context.self ! Bound(localAddress, sender().toTyped[Udp.Command])
              context.stop(self) // 임무 완수 후 자신을 중지

            case Udp.CommandFailed(cmd) =>
              context.self ! BindFailed(cmd)
              context.stop(self)
          }
        }))

        val udpManager = IO(Udp)(context.system.classicSystem)

        Behaviors.receiveMessage {
          case StartDiscovery =>
            context.log.info("Starting network discovery...")
            // udpManager에게 Bind를 요청. 응답은 classicBindHandler가 받게 됨.
            udpManager ! Udp.Bind(classicBindHandler, new InetSocketAddress("0.0.0.0", 9071))
            timers.startTimerWithFixedDelay(BroadcastTick, 2.seconds)
            Behaviors.same

          case Bound(address, socket) =>
            context.log.info(s"Successfully bound to UDP port $address. Transitioning to 'bound' behavior.")
            // '포트 열린 후'의 행동으로 전환하면서, 타이머와 소켓 액터, 그리고 로컬 주소를 전달
            bound(context, timers, socket, address)

          case BindFailed(_) =>
            context.log.error("Failed to bind UDP port. It might already be in use. Stopping.")
            Behaviors.stopped

          // 'bound' 행동으로 넘어가기 전에는 아래 메시지들을 처리할 필요가 없음
          case Packet(_, _) | BroadcastTick =>
            Behaviors.unhandled
        }
      }
    }

  // --- '포트 열린 후'의 행동 ---
  private def bound(context: ActorContext[Command], timers: TimerScheduler[Command], socket: ActorRef[Udp.Command], localAddress: InetSocketAddress): Behavior[Command] =
    Behaviors.receiveMessage {
      case Packet(data, from) =>
        // 내 자신이 보낸 방송 메시지는 무시
        if (from != localAddress) {
            context.log.info(s"Received a packet from $from: '${data.utf8String}'")
        }
        Behaviors.same

      case BroadcastTick =>
        context.log.info("Broadcasting discovery request...")
        val data = ByteString("JANGI_DISCOVERY_REQUEST")
        val destination = new InetSocketAddress("255.255.255.255", 9071)
        socket ! Udp.Send(data, destination)
        Behaviors.same

      // 'bound' 상태에서는 아래 메시지들을 받을 일이 없음
      case StartDiscovery | Bound(_, _) | BindFailed(_) =>
        Behaviors.unhandled
    }
}
