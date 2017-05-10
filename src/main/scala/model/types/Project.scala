package model.types

import java.net.URL
import model.types.adt.CVSHosting

sealed case class Project(
                           name: String,
                           admin: Long,
                           repoUrl: URL,
                           cvs: CVSHosting,
                           id:Long = 0L
                         )
