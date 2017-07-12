package at.trainingstool.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import at.trainingstool.model.User;

@Repository
public class UserDao {

  private NamedParameterJdbcTemplate template;

  @Autowired
  public UserDao(DataSource ds) {
    template = new NamedParameterJdbcTemplate(ds);
  }

  public User getUser(String username, String password) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("name", username);

    List<User> list = template.query("SELECT * FROM user WHERE username=:name", params, (rs, rowNum) -> {
      User u = new User();

      u.setId(rs.getInt("user_id"));
      u.setEmail(rs.getString("email"));
      u.setUsername(rs.getString("username"));
      u.setPassword(rs.getString("pw"));

      return u;
    });

    User result = null;
    if (list != null && !list.isEmpty()) {
      result = list.get(0);

      if (BCrypt.checkpw(password, result.getPassword())) {
        return result;
      }
    }
    return null;
  }

}
