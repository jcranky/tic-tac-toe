package com.jcranky.tictactoe

import akka.actor._
import akka.actor.Actor._

class BoardActor extends Actor {
  var p1: Option[(String, ActorRef)] = None
  var p2: Option[(String, ActorRef)] = None
  
  def receive = {
    case JoinGame(name) => {
        println("[board] received JoinGame request from " + name)
        if (p1.isEmpty) {
          p1 = buildPlayer(name, self.channel)
          self.reply(JoinedGame(1))
          
        } else if (p2.isEmpty) {
          p2 = buildPlayer(name, self.channel)
          self.reply(JoinedGame(2))
          
        } else {
          self.reply(GameFull)
        }
      }
    case play: PlayAt => {
        println("[board] playing at " + play.x + " " + play.y)
        
        if (self.channel == p1.get._2) p2.get._2.forward(play)
        else p1.get._2.forward(play)
    }
  }
  
  def buildPlayer(name: String, channel: UntypedChannel): Option[(String, ActorRef)] = {
    channel match {
      case ref: ActorRef => Some((name, ref))
    }
  }
}

object BoardActor {
  def run = {
    println("[system] starting board actor at 2552")
    remote.start("localhost", 2552).register("board-service", actorOf[BoardActor])
    
    // return a reference to the actor, useful when test-running from the console
    remote.actorFor("board-service", "localhost", 2552)
  }
}
