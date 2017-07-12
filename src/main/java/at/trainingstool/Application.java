package at.trainingstool;

import static spark.Spark.port;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import at.trainingstool.config.WebConfig;
import at.trainingstool.controller.LoginController;
import at.trainingstool.utils.HerokuUtils;

@Configuration
@ComponentScan({ "at.trainingstool" })
public class Application {

  public static void main(String[] args) {
    port(HerokuUtils.getHerokuAssignedPort());
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
    new WebConfig(ctx.getBean(LoginController.class));
    ctx.registerShutdownHook();
  }

}
