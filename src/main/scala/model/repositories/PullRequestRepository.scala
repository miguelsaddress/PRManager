package model.repositories

import database.Db
import model.tables.{PullRequestReviewersTable, PullRequestTagsTable, PullRequestsTable, UsersTable}
import model.types.adt.RejectionReason
import model.types.{PullRequest, PullRequestReviewer, User}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile


class PullRequestRepository(val config: DatabaseConfig[JdbcProfile]) extends Db
  with PullRequestsTable
  with PullRequestReviewersTable
  with PullRequestTagsTable
  with UsersTable {

  import config.driver.api._

  def dropTable: DBIO[Unit] =
    (PullRequestsTable.schema ++
        PullRequestReviewersTable.schema ++
        PullRequestTagsTable.schema
      ).drop

  def createTable: DBIO[Unit] =
    (PullRequestsTable.schema
      ++ PullRequestReviewersTable.schema
      ++ PullRequestTagsTable.schema
      ).create

  def create(pr: PullRequest): DBIO[Long] =
    PullRequestsTable returning PullRequestsTable.map(_.id) += pr

  def findById(id: Long): DBIO[Seq[PullRequest]] =
    PullRequestsTable.filter(_.id === id).result

  def selectAll = PullRequestsTable.result

  // TODO The creator cannot be reviewer
  def addReviewer(pr: PullRequest, u: User): DBIO[Long] =
    PullRequestReviewersTable returning PullRequestReviewersTable.map(_.id) += PullRequestReviewer(pr.id, u.id)

  def getReviewers(pr: PullRequest): DBIO[Seq[User]] = {
    val reviewerIds = PullRequestReviewersTable.filter(_.prId === pr.id).map(_.userId)
    UsersTable.filter(_.id in reviewerIds).result
  }

  def getCreatedBy(u: User) = ???
  def addTag(tag: Tag): DBIO[Long] = ???
  def addTags(tags: Seq[Tag]) = ???
  def approve(pr: PullRequest) = ???
  def reject(pr: PullRequest, reason: RejectionReason, msg: String) = ???

}
