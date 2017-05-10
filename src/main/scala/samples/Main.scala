package samples

import Implicits.TypeConversion._
import database.{Db, TestDbConfiguration}
import model.repositories.{PullRequestRepository, UsersRepository}
import model.types._
import model.types.adt.{Branch, Priority, Status}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Main extends TestDbConfiguration with Db {

  // Let's go! ----------------------------------

  def main(args: Array[String]): Unit = {

    val usersRepository = new UsersRepository(config)
    val prRepository = new PullRequestRepository(config)

    val u1 = User("username1", "email1", "http://example.com", 1)
    val u2 = User("username2", "email2", "http://example.com", 2)
    val u3 = User("username3", "email3", "http://example.com", 3)
    val u4 = User("username4", "email4", "http://example.com", 4)

    execSync(usersRepository.createTable)
    execSync(prRepository.createTable)
    execSync(usersRepository.create(u1))
    execSync(usersRepository.create(u2))
    execSync(usersRepository.create(u3))
    execSync(usersRepository.create(u4))
    execSync(usersRepository.selectAll).foreach(println)

    val pr1 = PullRequest(2, Status.Open, Branch("feature"), Branch("develop"), Priority.High, 1)

    execSync(prRepository.create(pr1))
    execSync(prRepository.addReviewer(pr1, u3))
    execSync(prRepository.addReviewer(pr1, u4))

    println()
    println()
    execSync(usersRepository.findById(3)).foreach(println)
    execSync(prRepository.findById(1)).foreach(println)
    execSync(prRepository.getReviewers(pr1)).foreach(println)


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