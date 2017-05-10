package model.tables

import database.Db

trait PullRequestsTable {
  this: Db =>

  import config.driver.api._
  import model.types.PullRequest
  import model.types.adt.{Branch, Priority, Status}

  implicit def statusColumnType: BaseColumnType[Status] =
    MappedColumnType.base[Status, Int](Status.toInt, Status.fromInt)

  implicit def priorityColumnType: BaseColumnType[Priority] =
    MappedColumnType.base[Priority, Int](Priority.toInt, Priority.fromInt)

  implicit def branchColumnType: BaseColumnType[Branch] =
    MappedColumnType.base[Branch, String](Branch.toString, Branch.fromString)

  // Table
  class PullRequestsTable(tag: Tag) extends Table[PullRequest](tag, "pull_requests") {

    def creatorId = column[Long]("creator_id")

    def status = column[Status]("status")

    def source = column[Branch]("source")

    def destination = column[Branch]("destination")

    def priority = column[Priority]("priority")

    def projectId = column[Long]("projectd")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def * = (creatorId, status, source, destination, priority, projectId, id) <> (PullRequest.tupled, PullRequest.unapply)

    def uniqueIdx = index("unique_pr_is_idx", (source, destination), unique = true)
  }

  lazy val PullRequestsTable = TableQuery[PullRequestsTable]
}
