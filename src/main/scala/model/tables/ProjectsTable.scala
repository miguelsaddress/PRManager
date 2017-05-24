package model.tables

import java.net.URL

import Implicits.TypeConversion._
import database.Db
import model.types.Project
import model.types.adt.CVSHosting

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

    def admin = column[Long]("admin")

    def repoUrl = column[URL]("repo_url", O.Length(255, varying = true))

    def cvs = column[CVSHosting]("cvs", O.Length(25, varying = true))

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def uniqueTeamName = index("unique_url_per_project", repoUrl, unique = true)

    def * = (name, admin, repoUrl, cvs, id) <> (Project.tupled, Project.unapply)
  }

  lazy val ProjectsTable = TableQuery[ProjectsTable]
}
