package services


import javax.inject.Inject


import models.Hospital
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.ReadPreference
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection


import scala.concurrent.{ExecutionContext, Future}
trait HospitalService {

  def findAllHospital()(implicit ec: ExecutionContext): Future[List[JsObject]]
  def findHospitalById(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]
  def addHospital(hospital: Hospital)(implicit ec: ExecutionContext): Future[WriteResult]
  def deleteHospital(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]
  def updateHospital(id: BSONDocument, update: Hospital)(implicit ec: ExecutionContext): Future[WriteResult]
}


class HospitalRepository @Inject() (reactiveMongoApi: ReactiveMongoApi) extends HospitalService{

  // def collection(implicit ec: ExecutionContext) = reactiveMongoApi.db.collection[JSONCollection]("hospital");
  def collection (implicit ec: ExecutionContext)  = reactiveMongoApi.database.map(db => db.collection[JSONCollection]("hospital"))

  //TODO GET ALL HOSPITAL
  override def findAllHospital()(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    /* val genericQueryBuilder = collection.find(Json.obj());
     val cursor = genericQueryBuilder.cursor[JsObject](ReadPreference.Primary);
     cursor.collect[List]()*/
    val genericQueryBuilder = collection.map(_.find(Json.obj()))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List]())
  }

  //TODO GET HOSPITAL BY ID
  override def findHospitalById(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(id).one[JsObject])
  }

  //TODO ADD HOSPITAL
  override def addHospital(hospital: Hospital)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(hospital))
  }

  //TODO DELETE HOSPITAL
  override def deleteHospital(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.remove(id))
  }

  //TODO UPDATE HOSPITAL
  override def updateHospital(id: BSONDocument, update: Hospital)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.update(id, update))
  }

}
