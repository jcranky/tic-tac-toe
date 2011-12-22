package com.jcranky.tictactoe

import akka.actor._
import akka.actor.Actor._

class PlayerActor(val playerName: String) extends Actor {
  val boardActor = remote.actorFor("board-service", "localhost", 2552)
  var boardFrame: Option[BoardFrame] = None
  
  def receive = {
    case ConnectToBoard =>
        println("[" + playerName + "] about to connect to the board")
        boardActor ! JoinGame(playerName)
      
    case JoinedGame(playerNumber) =>
        println("[" + playerName + "] received join confirmation as player " + playerNumber)
        boardFrame = Some(BoardFrame.run(this))
      
    case GameFull =>
        println("[" + playerName + "] too bad, board said that the game is already full")
      
    case PlayAt(x, y) =>
        boardFrame.get.otherPlayed(x, y)
  }
  
  def play(x: Int, y: Int) = boardActor ! PlayAt(x, y)
}

object PlayerActor {
  def run(playerName: String) = {
    val player = actorOf(new PlayerActor(playerName)).start()
    player ! ConnectToBoard
    
    player
  }
}
