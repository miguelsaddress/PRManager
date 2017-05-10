package model.repositories

import database.Db
import model.tables.{PullRequestReviewersTable, PullRequestsTable}
import model.types.{PullRequest, PullRequestReviewer, User}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile


class PullRequestRepository(val config: DatabaseConfig[JdbcProfile]) extends Db
  with PullRequestsTable with PullRequestReviewersTable {

  import config.driver.api._

  def dropTable = PullRequestsTable.schema.drop

  def createTable = (PullRequestsTable.schema ++ PullRequestReviewersTable.schema).create

  def create(pr: PullRequest) = PullRequestsTable += pr

  def findById(id: Long) = PullRequestsTable.filter(_.id === id).result

  // TODO The creator cannot be owner
  def addReviewer(pr: PullRequest, u: User) = PullRequestReviewersTable += PullRequestReviewer(pr.id, u.id)

  def getReviewers(pr: PullRequest) = PullRequestReviewersTable.filter(_.prId === pr.id).map(_.userId).result

  def selectAll = PullRequestsTable.result
}
