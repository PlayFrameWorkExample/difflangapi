package controllers

import javax.inject.Inject
import models.Hospital
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import services.HospitalRepository


class HospitalController  @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends  Controller with MongoController with ReactiveMongoComponents {

  def hospitalService = new HospitalRepository(reactiveMongoApi)


  //TODO GET Hospital
  def index = Action.async {
    implicit request =>
      hospitalService.findAllHospital().map(hospital => Ok(Json.toJson(hospital)))
  }

  //TODO Add Hospitalss
  def  addHospital = Action.async(BodyParsers.parse.json){
    implicit request =>
      val hospital = (request.body).as[Hospital]
      hospitalService.addHospital(hospital).map(result => Created)
  }

  //TODO GET Hospital By ID
  def findHospitalById(id: String) = Action.async {
    hospitalService.findHospitalById(BSONDocument("_id" -> BSONObjectID(id))).map(hospital => Ok(Json.toJson(hospital)))
  }


  //TODO UPDATE HOSPITAL
  def updateHospital(id: String) = Action.async(BodyParsers.parse.json) {
    { implicit request =>
      val hospital = (request.body).as[Hospital]
      val selector = BSONDocument("_id" -> BSONObjectID(id))

      hospitalService.updateHospital(selector, hospital).map(result => Accepted)
    }
  }

  def deleteHospital(id: String) = Action.async {
    hospitalService.deleteHospital(BSONDocument("_id" -> BSONObjectID(id)))
      .map(result => Accepted)
  }

}