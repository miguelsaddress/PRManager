package Implicits

import java.net.URL

object TypeConversion {
  implicit def stringToUrl(str: String): URL = new URL(str)
  implicit def url2String(url: URL): String = url.toString
}

