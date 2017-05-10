package model.types.adt

sealed case class Branch(name: String)

object Branch {
  def toString(branch: Branch) = branch.name

  def fromString(string: String) = Branch(string)
}
