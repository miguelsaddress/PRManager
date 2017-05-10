package model.types.adt

abstract class RejectionReason(value: Int)

object RejectionReason {

  final case object ArchitectureFlaws extends RejectionReason(1)

  final case object NotAsAgreed extends RejectionReason(2)

  final case object Performance extends RejectionReason(3)

  final case object Security extends RejectionReason(4)

  final case object Other extends RejectionReason(5)

  def fromInt(value: Int): RejectionReason = value match {
    case 1 => ArchitectureFlaws
    case 2 => NotAsAgreed
    case 3 => Performance
    case 4 => Security
    case 5 => Other
  }

  def toInt(rejectionReason: RejectionReason): Int = rejectionReason match {
    case ArchitectureFlaws => 1
    case NotAsAgreed => 2
    case Performance => 3
    case Security => 4
    case Other => 5
  }

}