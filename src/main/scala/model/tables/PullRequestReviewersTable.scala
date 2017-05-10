package model.tables

import database.Db

trait PullRequestReviewersTable {
  this: Db =>

  import config.driver.api._
  import model.types.PullRequestReviewer

  // Table
  class PullRequestReviewersTable(tag: Tag) extends Table[PullRequestReviewer](tag, "pull_request_reviewers") {
    def prId = column[Long]("pull_request_id")

    def userId = column[Long]("user_id")

    def * = (prId, userId) <> (PullRequestReviewer.tupled, PullRequestReviewer.unapply)

    def uniqueIdx = index("unique_pr_rev_id_idx", (prId, userId), unique = true)
  }

  lazy val PullRequestReviewersTable = TableQuery[PullRequestReviewersTable]
}
