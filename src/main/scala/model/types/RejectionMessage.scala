package model.types

sealed case class RejectionMessage(text: String)
object RejectionMessage {
    def toString(rejectionMsg: RejectionMessage) = rejectionMsg.text
    def fromString(text: String) = RejectionMessage(text)
}
