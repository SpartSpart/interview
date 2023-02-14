package models



import java.lang.annotation.Documented
import java.sql.Date


@Documented
case class User(id: String, name: String, age: String, date: String )
