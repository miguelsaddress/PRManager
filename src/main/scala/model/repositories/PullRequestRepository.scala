package model.repositories

import database.Db
import model.tables.{PullRequestReviewersTable, PullRequestTable}
import model.types.{PullRequest, PullRequestReviewer, User}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile


class PullRequestRepository(val config: DatabaseConfig[JdbcProfile]) extends Db
  with PullRequestTable with PullRequestReviewersTable {

  import config.driver.api._

  def dropTable = PullRequestTable.schema.drop
  def createTable = (PullRequestTable.schema ++ PullRequestReviewersTable.schema).create
  def create(pr: PullRequest) = PullRequestTable += pr
  def findById(id: Long) = PullRequestTable.filter(_.id === id).result
  // TODO The creator cannot be owner
  def addReviewer(pr: PullRequest, u: User) = PullRequestReviewersTable += PullRequestReviewer(pr.id, u.id)
  def getReviewers(pr: PullRequest) = PullRequestReviewersTable.filter(_.prId === pr.id).map(_.userId).result
  def selectAll = PullRequestTable.result
}
