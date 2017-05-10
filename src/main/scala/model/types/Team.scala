package model.types

sealed case class Team(
                        name: String,
                        projectId: Long,
                        id: Long = 0L
                      )
