package com.jcranky.tictactoe

sealed trait TicTacToeMessage

case object ConnectToBoard                extends TicTacToeMessage
case class JoinGame(playerName: String)   extends TicTacToeMessage
case class JoinedGame(playerNumber: Int)  extends TicTacToeMessage
case object GameFull                      extends TicTacToeMessage

case class PlayAt(x: Int, y: Int)         extends TicTacToeMessage
