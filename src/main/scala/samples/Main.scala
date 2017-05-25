package samples

import Implicits.TypeConversion._
import database.{Db, TestDbConfiguration}
import model.repositories.{ProjectsRepository, PullRequestRepository, TeamsRepository, UsersRepository}
import model.types._
import model.types.adt.CVSHosting.GitHub
import model.types.adt.{Branch, Priority, Status}
import slick.dbio.DBIO

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Main extends TestDbConfiguration with Db {

  // Let's go! ----------------------------------

  def main(args: Array[String]): Unit = {

    val usersRepository   = new UsersRepository(config)
    val prRepository      = new PullRequestRepository(config)
    val projectRepository = new ProjectsRepository(config)
    val teamRepository    = new TeamsRepository(config)

    val u1 = User("username1" , "email1" , "http://example.com" , 1)
    val u2 = User("username2" , "email2" , "http://example.com" , 2)
    val u3 = User("username3" , "email3" , "http://example.com" , 3)
    val u4 = User("username4" , "email4" , "http://example.com" , 4)
    val u5 = User("username5" , "email5" , "http://example.com" , 5)

    val project1 = Project("My project" , u1.id , "http://github.com/aUser/aRepo" , GitHub , 1)
    val pr1 = PullRequest(2 , Status.Open     , Branch("feature")  , Branch("develop"), Priority.High   , project1.id , 1)
    val pr2 = PullRequest(5 , Status.Approved , Branch("feature2") , Branch("develop"), Priority.Normal , project1.id , 2)

    val team1 = Team("My Team 1" , project1.id , 1)
    val team2 = Team("My Team 2" , project1.id , 2)

    //Create tables
    val createTableActions =
      usersRepository.createTable   andThen
      projectRepository.createTable andThen
      prRepository.createTable      andThen
      teamRepository.createTable

    // Create users
    val createUsersAction =
      usersRepository.createMany(
        List(u1, u2, u3, u4, u5) ++
        List(
          User("username6" , "email6" , "http://example.com"),
          User("username7" , "email7" , "http://example.com"),
          User("username8" , "email8" , "http://example.com"),
          User("username9" , "email9" , "http://example.com")
        )
      )

    //Create project
    val createProjectsAction =
      projectRepository.create(project1)

    //Create Teams
    val createTeamsAction =
      teamRepository.create(team1) andThen
      teamRepository.create(team2) andThen
      //Add users to teams
      teamRepository.addMember(u1, team1) andThen
      teamRepository.addMember(u2, team1) andThen
      teamRepository.addMember(u3, team1) andThen
      teamRepository.addMember(u4, team2) andThen
      teamRepository.addMember(u5, team2)

    //Create Pull Requests
    val createPullRequestsAction =
      prRepository.create(pr1)          andThen
      prRepository.addReviewer(pr1, u3) andThen
      prRepository.addReviewer(pr1, u4) andThen
      prRepository.create(pr2)          andThen
      prRepository.addReviewer(pr2, u1) andThen
      prRepository.addReviewer(pr2, u2)

    val createFixturesAction =
      createTableActions    andThen
      createUsersAction     andThen
      createProjectsAction  andThen
      createTeamsAction     andThen
      createPullRequestsAction

    execSync(createFixturesAction)

    // Display some
    execSyncWithMsgSeq("Teams of Project 1", projectRepository.getTeams(project1))

    execSyncWithMsgSeq("All the users are", usersRepository.selectAll)

    execSyncWithMsgSeq("This are the reviewers of PR1", prRepository.getReviewers(pr1))

    execSync(usersRepository.findById(2)) match {
      case Some(user) => println(s"User[id=2]: $user")
      case None       => println("User[id=2] Not found")
    }

    execSync(usersRepository.findById(27)) match {
      case Some(user) => println(user)
      case None       => println("User[id=27] Not found")
    }
  }

  def execSyncWithMsgSeq(msg: String, action: DBIO[Seq[_]]) = {
    println(msg)
    println("-" * msg.length)
    execSync(action).foreach(println)
    println()
    println("=" * 120)
  }

  def execSyncWithMsg(msg: String, action: DBIO[_]) = {
    println(msg)
    println("-" * msg.length)
    val res = execSync(action)
    println(res)
    println()
    println("=" * 120)
  }
}
