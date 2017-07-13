package at.trainingstool.config;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import java.util.HashMap;

import at.trainingstool.controller.LoginController;
import at.trainingstool.utils.AuthentificationUtils;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class LoginConfig {

  private LoginController loginController;

  public LoginConfig(LoginController loginController) {
    this.loginController = loginController;
    setupRoutes();
  }

  private void setupRoutes() {
    before("/app/*", (req, res) -> loginController.checkIfAlreadyLoggedIn(req, res));

    before("/", (req, res) -> {
      res.redirect("/login");
      halt();
    });

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
