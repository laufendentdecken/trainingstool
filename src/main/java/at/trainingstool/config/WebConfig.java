package at.trainingstool.config;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

import at.trainingstool.controller.LoginController;
import at.trainingstool.utils.AuthentificationUtils;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class WebConfig {

  private LoginController loginController;

  public WebConfig(LoginController loginController) {
    this.loginController = loginController;
    staticFileLocation("/public");
    setupRoutes();
  }

  private void setupRoutes() {
    before("/app/*", (req, res) -> loginController.checkIfAlreadyLoggedIn(req, res));

    before("/", (req, res) -> {
      res.redirect("/login");
      halt();
    });

    get("/app/welcome", (req, res) -> {
      Map<String, Object> map = new HashMap<>();
      map.put("user", AuthentificationUtils.getAuthenticatedUser(req));
      return new ModelAndView(map, "welcome.ftl");
    }, new FreeMarkerEngine());

    // Routes
    get("/login", (req, res) -> {
      return new ModelAndView(new HashMap<>(), "login.ftl");
    }, new FreeMarkerEngine());

    post("/login", (req, res) -> loginController.doLogin(req, res), new FreeMarkerEngine());

    get("/logout", (req, res) -> {
      AuthentificationUtils.removeAuthenticatedUser(req);
      res.redirect("/login");
      return null;
    });
  }

}
