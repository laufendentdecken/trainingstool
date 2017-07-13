package at.trainingstool.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import at.trainingstool.model.User;
import at.trainingstool.tools.period.AbstractPeriod;
import at.trainingstool.tools.period.monthly.MonthlyPeriod;
import at.trainingstool.utils.AuthentificationUtils;
import at.trainingstool.utils.CalendarUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

@Service
public class ApplicationController {

  public ModelAndView createCalendarOverview(Request req, Response res) {
    User user = AuthentificationUtils.getAuthenticatedUser(req);
    AbstractPeriod period = new MonthlyPeriod(user, req.queryParams("period"));

    Map<String, Object> map = new HashMap<>();
    map.put("user", user);
    map.put("currentPeriod", period.getName());
    map.put("weekdays", CalendarUtils.getWeekDays());
    map.put("period", period.getValues());

    map.put("previous", period.previous());
    map.put("next", period.next());

    return new ModelAndView(map, "welcome.ftl");
  }

}
