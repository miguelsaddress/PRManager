package model.types

import java.net.URL

final case class User(
    username: String, 
    email:String, 
    image: URL, 
    id: Long = 0L
)
