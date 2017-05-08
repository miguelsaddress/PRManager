package model.tables

import java.net.URL
import database.profile.DatabaseProfile

trait UsersModule {  self: DatabaseProfile =>
    import profile.api._
    import model.types.User
    
    implicit def String2URL(url: String): URL = new URL(url)
    implicit def URL2String(url: URL): String = url.toString

    implicit val imageColumnType: BaseColumnType[URL] =
      MappedColumnType.base[URL, String](URL2String, String2URL)

    // Table
    class UserTable(tag: Tag) extends Table[User](tag, "users") {
      def username  = column[String]("username", O.Length(255, varying=true))
      def email     = column[String]("email", O.Length(255, varying=true))
      def image     = column[URL]("image_url", O.Length(255, varying=true))
      def id        = column[Long]("id", O.PrimaryKey, O.AutoInc)

      def uniqueUserName  = index("unique_username" , username  , unique=true)
      def uniqueEmail     = index("unique_email"    , email     , unique=true)

      def * = (username, email, image, id) <> (User.tupled, User.unapply)
    }

    lazy val UsersTable = TableQuery[UserTable]

    def dropUsersTable = UsersTable.schema.drop
    def createUsersTable = UsersTable.schema.create
    def createUser(user: User) = UsersTable += user
    def selectAll = UsersTable.result
    def findUserById(id: Long) = UsersTable.filter(_.id === id).result
}
