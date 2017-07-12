package at.trainingstool.controller;

import static spark.Spark.halt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.trainingstool.dao.UserDao;
import at.trainingstool.model.User;
import at.trainingstool.utils.AuthentificationUtils;
import at.trainingstool.utils.ConvertUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Service
public class LoginController {

  @Autowired
  UserDao userDao;

  public void checkIfAlreadyLoggedIn(Request req, Response res) {
    User user = AuthentificationUtils.getAuthenticatedUser(req);
    if (user == null) {
      res.redirect("/login");
      halt();
    }
  }

  public ModelAndView doLogin(Request req, Response res) {
    Map<String, Object> map = new HashMap<>();
    User user = new User();

    user = ConvertUtils.<User> convert(req, user);

    User result = userDao.getUser(user.getUsername(), user.getPassword());
    if (result != null) {
      AuthentificationUtils.addAuthenticatedUser(req, result);
      res.redirect("/app/welcome");
      halt();
    }
    map.put("username", user.getUsername());
    return new ModelAndView(map, "login.ftl");
  }
}
