package at.trainingstool;

import static spark.Spark.exception;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import at.trainingstool.config.AppConfig;
import at.trainingstool.config.LoginConfig;
import at.trainingstool.controller.ApplicationController;
import at.trainingstool.controller.LoginController;
import at.trainingstool.utils.HerokuUtils;

@Configuration
@ComponentScan({ "at.trainingstool" })
public class Application {

  public static void main(String[] args) {
    port(HerokuUtils.getHerokuAssignedPort());
    staticFileLocation("/public");

    exception(Exception.class, (exception, request, response) -> {
      exception.printStackTrace();
    });

    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
    new LoginConfig(ctx.getBean(LoginController.class));
    new AppConfig(ctx.getBean(ApplicationController.class));
    ctx.registerShutdownHook();
  }

}
