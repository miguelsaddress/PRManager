package model.types.adt

sealed abstract class CVSHosting(value: Int)

object CVSHosting {

  final case object GitHub extends CVSHosting(1)

  final case object Bitbucket extends CVSHosting(2)

  def fromInt(value: Int): CVSHosting = value match {
    case 1 => GitHub
    case 2 => Bitbucket
  }

  def toInt(cvs: CVSHosting): Int = cvs match {
    case GitHub => 1
    case Bitbucket => 2
  }
}
