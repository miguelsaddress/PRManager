package model.types

sealed case class UsersProject(
                                  userIs: Long,
                                  projectId: Long,
                                  id: Long = 0L
                                )
