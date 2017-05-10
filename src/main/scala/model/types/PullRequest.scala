package model.types

import model.types.adt.{Branch, Priority, Status}

case class PullRequest(
                        creatorId: Long,
                        status: Status,
                        source: Branch,
                        destination: Branch,
                        priority: Priority,
                        projectId: Long,
                        id: Long = 0L
                      )
