package model.types

sealed case class Tag(value: String)
object Tag {
    def toString(tag: Tag) = tag.value
    def fromString(string: String) = Tag(string)
}
