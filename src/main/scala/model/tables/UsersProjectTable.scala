package model.tables

import database.Db
import model.types.UsersProject

trait UsersProjectTable {
  this: Db =>

  import config.driver.api._
  
  // Table
  class UsersProjectTable(tag: Tag) extends Table[UsersProject](tag, "users_project") {
    def userId = column[Long]("user_id", O.Length(255, varying = true))

    def projectId = column[Long]("project_id")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    
    def * = (userId, projectId, id) <> (UsersProject.tupled, UsersProject.unapply)
  }

  lazy val UsersProjectTable = TableQuery[UsersProjectTable]
}
