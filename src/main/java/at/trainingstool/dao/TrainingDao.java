package at.trainingstool.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import at.trainingstool.model.Training;
import at.trainingstool.model.User;

@Repository
public class TrainingDao {

  private NamedParameterJdbcTemplate template;

  @Autowired
  public TrainingDao(DataSource ds) {
    template = new NamedParameterJdbcTemplate(ds);
  }

  public List<Training> getTrainings(User user, Date from, Date to) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("name", user.getId());
    params.put("from", new SimpleDateFormat("yyyy-MM-dd").format(from));
    params.put("to", new SimpleDateFormat("yyyy-MM-dd").format(to));

    List<Training> list = template.query("SELECT * FROM training WHERE user_Id=:name and schedule_date > :from and schedule_date < :to", params, (rs, rowNum) -> {
      Training t = new Training();

      t.setId(rs.getInt("id"));
      t.setType(rs.getString("training_type"));
      t.setDescription(rs.getString("description"));
      t.setScheduleDate(rs.getDate("schedule_date"));

      return t;
    });

    return list;
  }

}
