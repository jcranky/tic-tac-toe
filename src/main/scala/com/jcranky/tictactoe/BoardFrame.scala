package com.jcranky.tictactoe

import scala.swing._

class BoardFrame(actor: PlayerActor) extends SimpleSwingApplication {
  def b(x: Int, y: Int): Button = Button("_") {
    board(x)(y).text = "X"
    actor.play(x, y)
  }
  
  val board = List(
    List(b(0,0), b(0,1), b(0,2)),
    List(b(1,0), b(1,1), b(1,2)),
    List(b(2,0), b(2,1), b(2,2))
  )
  
  def top = new MainFrame {
    title = "Tic Tac Toe - " + actor.playerName
    preferredSize = new Dimension(300, 300)
    
    contents = new GridPanel(3,3) {
      board.foreach(_.foreach(
          contents += _
        ))
    }
  }
  
  def otherPlayed(x: Int, y: Int) = {
    println("other player played at [x= " + x + ", y= " + y + "]")
    board(x)(y).text = "O"
  }
}

object BoardFrame {
  def run(actor: PlayerActor): BoardFrame = {
    val bf = new BoardFrame(actor)
    bf.startup(Array(""))
    
    bf
  }
}
