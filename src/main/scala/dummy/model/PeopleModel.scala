package dummy.model

import java.util.{List=>JList,Map=>JMap}
import collection.JavaConverters._


// ----------------------------------------------------------------
// DOMAIN MODEL


case class Someone(
    name:String,
    age:Int,
    nicknames:JList[String]=List.empty[String].asJava,
    attributes:JMap[String,String]=Map.empty[String,String].asJava
)

case class Car(someone:Someone, model:String, year:Int, color:Color)

case class Color(name:String)

object Color {
  val red = Color("red")
  val blue = Color("blue")
  val green = Color("green")
  val black = Color("black")
}

case class Address(street:String, town:String, country:String)

case class Home(someone:Someone, address:Option[Address]) 


trait Information {
  val someone : Someone
}

case class InformationRequest(someone:Someone, message:String) extends Information

case class InformationRemarkable(someone:Someone, info:String) extends Information
