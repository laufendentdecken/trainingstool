package at.trainingstool.utils;

import static spark.Spark.halt;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import spark.Request;

public class ConvertUtils {

  public static <T> T convert(Request req, T object) {
    try {
      MultiMap<String> params = new MultiMap<String>();
      UrlEncoded.decodeTo(req.body(), params, "UTF-8");
      BeanUtils.populate(object, params);
    } catch (Exception e) {
      halt(501);
      return null;
    }

    return object;
  }
}
