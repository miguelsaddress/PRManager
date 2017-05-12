package model.tables

import database.Db

trait UsersTeamsTable {
  this: Db =>

  import config.driver.api._
  import model.types.UsersTeams

  // Table
  class UsersTeamsTable(tag: Tag) extends Table[UsersTeams](tag, "users_teams") {
    def userId = column[Long]("user_id")

    def teamId = column[Long]("team_id")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def uniqueUserPerTeam = index("unique_team_per_user", userId, unique = true)

    def * = (userId, teamId, id) <> (UsersTeams.tupled, UsersTeams.unapply)
  }

  lazy val UsersTeamsTable = TableQuery[UsersTeamsTable]
}
