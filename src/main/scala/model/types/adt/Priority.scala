package model.types.adt

sealed abstract class Priority(value: Int)

object Priority {

  final case object High extends Priority(1)

  final case object Normal extends Priority(2)

  def fromInt(value: Int): Priority = value match {
    case 1 => High
    case 2 => Normal
  }

  def toInt(priority: Priority): Int = priority match {
    case High => 1
    case Normal => 2
  }
}
