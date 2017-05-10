package database

// Scala Futures
import scala.concurrent.Await
import scala.concurrent.duration._

// Slick
import slick.backend.DatabaseConfig
import slick.dbio.DBIO
import slick.driver.JdbcProfile

trait DbConfiguration {
  lazy val config: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig[JdbcProfile]("mysql-db-config")
}

trait TestDbConfiguration {
  lazy val config: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig[JdbcProfile]("h2-db-config")
}

trait Db {
  val config: DatabaseConfig[JdbcProfile]
  val db: JdbcProfile#Backend#Database = config.db

  def execSync[T](action: DBIO[T]): T = {
    Await.result(db.run(action), 2 seconds)
  }
}


