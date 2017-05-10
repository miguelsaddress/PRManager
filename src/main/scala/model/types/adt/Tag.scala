package model.types.adt

sealed case class Tag(name: String)

object Tag {
  def toString(tag: Tag) = tag.name

  def fromString(string: String) = Tag(string)
}

