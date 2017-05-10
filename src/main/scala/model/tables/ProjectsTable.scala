package model.tables

import java.net.URL

import database.Db
import model.types.Project
import model.types.adt.CVSHosting

import Implicits.TypeConversion._

trait ProjectsTable {
  this: Db =>

  import config.driver.api._

  implicit val urlColumn: BaseColumnType[URL] =
    MappedColumnType.base[URL, String](url2String, stringToUrl)

  implicit val cvsColumn: BaseColumnType[CVSHosting] =
    MappedColumnType.base[CVSHosting, Int](CVSHosting.toInt, CVSHosting.fromInt)
  
  // Table
  class ProjectsTable(tag: Tag) extends Table[Project](tag, "projects") {
    def name = column[String]("name", O.Length(255, varying = true))

    def admin = column[Long]("admin", O.PrimaryKey, O.AutoInc)

    def repoUrl   = column[URL]("repo_url")

    def cvs       = column[CVSHosting]("cvs")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def uniqueTeamName = index("unique_url_per_project", repoUrl, unique = true)

    def * = (name, admin, repoUrl, cvs, id) <> (Project.tupled, Project.unapply)
  }

  lazy val ProjectsTable = TableQuery[ProjectsTable]
}
