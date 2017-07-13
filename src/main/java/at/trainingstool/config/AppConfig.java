package at.trainingstool.config;

import static spark.Spark.get;
import static spark.Spark.post;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import at.trainingstool.controller.ApplicationController;
import at.trainingstool.model.User;
import at.trainingstool.tools.parser.model.Activity;
import at.trainingstool.tools.parser.tcx.TCXParser;
import at.trainingstool.utils.AuthentificationUtils;
import spark.ModelAndView;
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

    get("/app/upload", (req, res) -> {
      User user = AuthentificationUtils.getAuthenticatedUser(req);

      Map<String, Object> map = new HashMap<>();
      map.put("user", user);

      return new ModelAndView(map, "upload.ftl");
    }, new FreeMarkerEngine());

    post("/app/process", "multipart/form-data", (req, res) -> {

      MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/files");
      req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

      User user = AuthentificationUtils.getAuthenticatedUser(req);

      Collection<Part> parts = req.raw().getParts();
      StringBuilder stringBuilder = new StringBuilder();

      for (Part part : parts) {
        Path targetPath = Files.createTempFile(part.getSubmittedFileName().split("\\.")[0], "tcx");

        Files.copy(part.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        for (Activity activity : new TCXParser().parse(targetPath)) {

          stringBuilder.append(part.getSubmittedFileName() + "<br/>");
          stringBuilder.append("-------------------------------------<br/>");
          stringBuilder.append("Dauer(sec): " + activity.getDuration().doubleValue() + "<br />");
          stringBuilder.append("Dauer: " + activity.getDuration().print() + "<br />");
          stringBuilder.append("Länge(M): " + activity.getDistance() + "<br />");
          stringBuilder.append("Höhe: " + activity.getElevation() + "<br />");
          stringBuilder.append("Laps: " + activity.getLaps().size() + "<br /><br />");
        }

      }

      Map<String, Object> map = new HashMap<>();
      map.put("user", user);
      map.put("result", stringBuilder);

      return new ModelAndView(map, "activity.ftl");
    }, new FreeMarkerEngine());

  }

}
