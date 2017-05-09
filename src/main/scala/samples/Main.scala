package samples

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import model.repositories.UsersRepository
import model.types.User
import database.{Db, TestDbConfiguration}
import Implicits.TypeConversion._

object Main extends TestDbConfiguration with Db {

  // Let's go! ----------------------------------

  def main(args: Array[String]): Unit = {

    val usersRepository = new UsersRepository(config)

    execSync(usersRepository.createTable)
    execSync(usersRepository.create(User("username1", "email1", "http://example.com")))
    execSync(usersRepository.create(User("username2", "email2", "http://example.com")))
    execSync(usersRepository.create(User("username3", "email3", "http://example.com")))
    execSync(usersRepository.create(User("username4", "email4", "http://example.com")))
    execSync(usersRepository.selectAll).foreach(println)
    println()
    println()
    execSync(usersRepository.findById(3)).foreach(println)

    val user2 = db.run(usersRepository.findById(2))
    user2 onComplete {
      case Failure(e) => println(e.getMessage)
      case Success(users) if users.length == 1 => println(users.head)
      case Success(users) => "Many users with the same Id... Check your Table Definition"
    }

    val user27 = db.run(usersRepository.findById(27))
    user27.foreach {
      case e if e.nonEmpty => println(e)
      case e => println("User 27 Not found")
    }
  }
}