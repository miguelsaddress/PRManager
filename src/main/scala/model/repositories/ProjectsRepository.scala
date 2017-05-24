package model.repositories

import database.Db
import model.tables.{ProjectsTable, TeamsTable}
import model.types.Project
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

class ProjectsRepository(val config: DatabaseConfig[JdbcProfile]) extends Db
  with ProjectsTable with TeamsTable {

  import config.driver.api._

  def dropTable = ProjectsTable.schema.drop

  def createTable = ProjectsTable.schema.create

  // TODO: return user with Id
  def create(project: Project) = ProjectsTable returning ProjectsTable.map(_.id) += project

  def findById(id: Long) = ProjectsTable.filter(_.id === id).result

  def getTeams(project: Project) = TeamsTable.filter(_.projectId === project.id).result

  def selectAll = ProjectsTable.result
}
