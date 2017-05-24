package model.types

import java.net.URL

case class User(
  username : String,
  email    : String,
  image    : URL,
  id       : Long = 0L)
