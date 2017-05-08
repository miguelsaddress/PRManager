package model.types

case class PullRequest(
    creatorId: Long,
    status: Status,
    source: Branch,
    destination: Branch,
    priority: Priority,
    id: Long = 0L
)
