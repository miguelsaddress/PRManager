package model.tables

import database.Db

trait TeamsTable {
  this: Db =>

  import config.driver.api._
  import model.types.Team

  // Table
  class TeamsTable(tag: Tag) extends Table[Team](tag, "teams") {
    def name = column[String]("name", O.Length(255, varying = true))

    def projectId = column[Long]("project_id")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def uniqueTeamName = index("unique_team_name_per_project", (name, projectId), unique = true)

    def * = (name, projectId, id) <> (Team.tupled, Team.unapply)
  }

  lazy val TeamsTable = TableQuery[TeamsTable]
}
