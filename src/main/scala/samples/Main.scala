package samples
import scala.concurrent.{Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Try, Success, Failure}
import slick.driver.{H2Driver, MySQLDriver}

import database.profile.DatabaseLayer
import model.types.User

object Main {

  val mysqlLayer = new DatabaseLayer(MySQLDriver, "mysql-db-config") 
  val h2Layer = new DatabaseLayer(H2Driver, "h2-db-config") 

  // Let's go! ----------------------------------

  def main(args: Array[String]): Unit = {
    // Try(runWith(mysqlLayer)) match {
    //   case Success(results) => showResults("MySql", results)
    //   case Failure(_) => println("\n\t\tErrors run for Mysql\n")
    // }

    Try(runWith(h2Layer)) match {
      case Success(results) => showResults("H2", results)
      case Failure(e) => {
        println("\n\t\tErrors run for H2\n")
        println(e)
      }
    }
  }

  def showResults[A](dbLayerName: String, results: Future[(Seq[A], Seq[A])]) = {
    results onComplete {
      case Failure(t) => println(t.getMessage)
      case Success((user, allUsers)) => 
        val message = s"Successfully Run for ${dbLayerName}"
        val nOfStars = message.length
        println("="*nOfStars)
        println(message) 
        println("="*nOfStars)

        user.foreach(println)
        println
        allUsers.foreach(println)
    }
  }

  def runWith(databaseLayer: DatabaseLayer[_]) = {
    import databaseLayer._

    val seedingData = 
      createUser(User("user1", "email1", "http://myUrl.com")) andThen
      createUser(User("user2", "email2", "http://myUrl.com")) andThen
      createUser(User("user3", "email3", "http://myUrl.com")) andThen
      createUser(User("user4", "email4", "http://myUrl.com"))
    
    exec(createUsersTable)
    exec(seedingData)

    val results = for {
      user <- db.run(findUserById(3))
      all <- db.run(selectAll)
    } yield (user, all)

    results
  }
}