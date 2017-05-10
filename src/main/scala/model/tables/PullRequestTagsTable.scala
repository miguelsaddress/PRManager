package model.tables

import database.Db
import model.types.PullRequestTag
import model.types.adt.Tag

trait PullRequestTagsTable {
  this: Db =>

  import config.driver.api.{Tag => SlickTag, _}

  implicit val tagColumnType: BaseColumnType[Tag] =
    MappedColumnType.base[Tag, String](Tag.toString, Tag.fromString)

  // Table
  class PullRequestTagsTable(tag: SlickTag) extends Table[PullRequestTag](tag, "pull_request_tags") {
    def tagName = column[Tag]("tag_name", O.Length(255, varying = true))

    def prId = column[Long]("pull_request_id")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def uniqueTagPerPR = index("unique_tag_per_pull_request", (tagName, prId), unique = true)

    def * = (tagName, prId, id) <> (PullRequestTag.tupled, PullRequestTag.unapply)
  }

  lazy val PullRequestTagsTable = TableQuery[PullRequestTagsTable]
}
