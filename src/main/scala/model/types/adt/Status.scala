package model.types.adt

sealed abstract class Status(value: Int)

object Status {

  final case object Open extends Status(1)

  final case object Closed extends Status(2)

  final case object PartiallyApproved extends Status(3)

  final case object Approved extends Status(4)

  final case object Rejected extends Status(5)

  def fromInt(value: Int): Status = value match {
    case 1 => Open
    case 2 => Closed
    case 3 => PartiallyApproved
    case 4 => Approved
    case 5 => Rejected
  }

  def toInt(status: Status): Int = status match {
    case Open => 1
    case Closed => 2
    case PartiallyApproved => 3
    case Approved => 4
    case Rejected => 5
  }
}
