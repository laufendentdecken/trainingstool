package at.trainingstool.utils;

import at.trainingstool.model.User;
import spark.Request;

public class AuthentificationUtils {
  private static final String USER_SESSION_ID = "user";

  public static void addAuthenticatedUser(Request request, User u) {
    request.session().attribute(USER_SESSION_ID, u);

  }

  public static void removeAuthenticatedUser(Request request) {
    request.session().removeAttribute(USER_SESSION_ID);

  }

  public static User getAuthenticatedUser(Request request) {
    return request.session().attribute(USER_SESSION_ID);
  }
}
