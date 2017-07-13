package at.trainingstool.config;

import static spark.Spark.get;

import at.trainingstool.controller.ApplicationController;
import spark.template.freemarker.FreeMarkerEngine;

public class AppConfig {

  private ApplicationController applicationController;

  public AppConfig(ApplicationController applicationController) {
    this.applicationController = applicationController;
    setupRoutes();
  }

  private void setupRoutes() {
    get("/app/welcome", (req, res) -> {
      return applicationController.createCalendarOverview(req, res);
    }, new FreeMarkerEngine());

  }

}
