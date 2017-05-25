package model.repositories

import database.Db
import model.tables.{TeamsTable, UsersTeamsTable}
import model.types._
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile


class TeamsRepository(val config: DatabaseConfig[JdbcProfile]) extends Db with TeamsTable with UsersTeamsTable {

  import config.driver.api._

  def dropTable = (TeamsTable.schema ++ UsersTeamsTable.schema).drop

  def createTable = (TeamsTable.schema ++ UsersTeamsTable.schema).create

  def create(team: Team) = TeamsTable returning TeamsTable.map(_.id) += team

  def findById(id: Long) = TeamsTable.filter(_.id === id).result

  def addMember(u: User, t: Team) = {
    UsersTeamsTable returning UsersTeamsTable.map(_.id) += UsersTeams(u.id, t.id)
  }

  def getMembers(t: Team) = UsersTeamsTable.filter(_.teamId === t.id).map(_.userId).result


  def selectAll = TeamsTable.result
}
