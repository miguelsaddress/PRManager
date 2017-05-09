package model.tables

import java.net.URL

import database.Db

trait UsersTable { this: Db =>

    import config.driver.api._
    import model.types.User
    import Implicits.TypeConversion._

    implicit val imageColumnType: BaseColumnType[URL] =
      MappedColumnType.base[URL, String](url2String, stringToUrl)

    // Table
    class UserTable(tag: Tag) extends Table[User](tag, "users") {
      def username = column[String]("username", O.Length(255, varying=true))
      def email    = column[String]("email", O.Length(255, varying=true))
      def image    = column[URL]("image_url", O.Length(255, varying=true))
      def id       = column[Long]("id", O.PrimaryKey, O.AutoInc)

      def uniqueUserName = index("unique_username" , username  , unique=true)
      def uniqueEmail    = index("unique_email"    , email     , unique=true)

      def * = (username, email, image, id) <> (User.tupled, User.unapply)
    }

    lazy val UsersTable = TableQuery[UserTable]
}
