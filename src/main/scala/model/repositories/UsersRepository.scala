package model.repositories

import database.Db
import model.tables.UsersTable
import model.types.User
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile


class UsersRepository(val config: DatabaseConfig[JdbcProfile]) extends Db with UsersTable {
  import config.driver.api._

  def dropTable = UsersTable.schema.drop
  def createTable = UsersTable.schema.create
  def create(user: User) = UsersTable += user
  def findById(id: Long) = UsersTable.filter(_.id === id).result
  def selectAll = UsersTable.result
}
