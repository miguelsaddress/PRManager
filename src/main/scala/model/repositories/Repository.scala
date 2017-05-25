package model.repositories

import database.Db
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext
import slick.dbio.DBIO

trait Repository[A] extends Db {

  def dropTable: DBIO[Unit] = ???
  def createTable: DBIO[Unit] = ???
  def create(item: A)(implicit ec: ExecutionContext): DBIO[A] = ???
  def createMany(items: Seq[A])(implicit ec: ExecutionContext): DBIO[Seq[A]] =
    DBIO.sequence(items.map(create))

  def findById(id: Long): DBIO[Option[A]] = ???
  def selectAll: DBIO[Seq[A]] = ???
}
