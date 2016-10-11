package controllers

import play.api.mvc.{Action, Controller}

/**
  * Created by acer on 10/11/2016.
  */
class SwaggerController extends Controller{

    def swagger = Action{
      Ok(views.html.index("swagger"))
    }
}
