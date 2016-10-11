package models

import play.api.libs.json._

/**
  * Created by acer on 10/11/2016.
  */

//TODO Use Case Class for to be JSON
case class Hospital(
                     name : String,
                     email:String,
                     address: String,
                     phone:String

                   )

//TODO Making JSON FORMAT
object Hospital
{
  //TODO Implicit Write Object
  implicit object HospitalWrites extends OWrites[Hospital]
  {
    def writes(hospital: Hospital): JsObject = Json.obj(
      "name" -> hospital.name,
      "email" -> hospital.email,
      "address" -> hospital.address,
      "phone" -> hospital.phone
    )
  }
  //TODO Implicit Read Object
  implicit object HospitalReads extends Reads[Hospital]
  {
    def reads(json: JsValue): JsResult[Hospital] = json match
    {
      case obj: JsObject => try
      {
        val name = (obj \ "name").as[String]
        val email = (obj \ "email").as[String]
        val address = (obj \ "address").as[String]
        val phone = (obj \ "phone").as[String]
        JsSuccess(Hospital( name, email,address,phone))

      }
      catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }
}