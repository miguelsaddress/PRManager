package model.tables

import database.Db
import model.types.RejectionMessage
import model.types.adt.RejectionReason

trait RejectionMessagesTable {
  this: Db =>

  import config.driver.api._

  implicit val reasonColumnType: BaseColumnType[RejectionReason] =
    MappedColumnType.base[RejectionReason, Int](RejectionReason.toInt, RejectionReason.fromInt)

  // Table
  class RejectionMessagesTable(tag: Tag) extends Table[RejectionMessage](tag, "rejection_messages") {

    def text = column[String]("text")

    def reason = column[RejectionReason]("reason")

    def prId = column[Long]("pull_request_id")

    def userId = column[Long]("user_id")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def * = (text, reason, prId, userId, id) <> (RejectionMessage.tupled, RejectionMessage.unapply)

    def uniqueIdx = index("unique_rejection_reason_per_pr_and_user_idx", (reason, prId, userId), unique = true)
  }

  lazy val RejectionMessagesTable = TableQuery[RejectionMessagesTable]
}
