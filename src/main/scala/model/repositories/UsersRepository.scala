package model.repositories

import database.Db
import model.tables.UsersTable
import model.types.User
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext

class UsersRepository(val config: DatabaseConfig[JdbcProfile]) extends Repository[User] with UsersTable {

  import config.driver.api._

  override def dropTable = UsersTable.schema.drop

  override def createTable = UsersTable.schema.create

  override def create(user: User)(implicit ec: ExecutionContext) = {
    for {
      uid <- UsersTable returning UsersTable.map(_.id) += user
    } yield user.copy(id=uid)
  }

  override def findById(id: Long) = UsersTable.filter(_.id === id).result.headOption

  override def selectAll = UsersTable.result
}
