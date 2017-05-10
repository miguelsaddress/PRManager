package model.types

import model.types.adt.Tag

sealed case class PullRequestTag(
                                  tagName: Tag,
                                  prId: Long,
                                  id: Long = 0L
                                )
