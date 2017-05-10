package model.types

import model.types.adt.RejectionReason

case class RejectionMessage(
                             text: String,
                             reason: RejectionReason,
                             prId: Long,
                             userId: Long,
                             id: Long = 0L
                           )
