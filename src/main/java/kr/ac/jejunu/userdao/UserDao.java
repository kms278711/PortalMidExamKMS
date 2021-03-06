package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User get(Integer id) throws ClassNotFoundException, SQLException {
        String sql = "select * from userinfo where id = ?";
        Object[] params = new Object[]{id};
        return jdbcContext.get(sql, params);
    }

    public void insert(User user) throws SQLException, ClassNotFoundException {
        String sql = "insert into userinfo(name, password) values(?,?)";
        Object[] params = new Object[]{user.getName(), user.getPassword()};
        jdbcContext.insert(sql, params, user);
    }

    public void update(User user) throws SQLException, ClassNotFoundException {
        String sql = "update userinfo set name=?,password=? where id=?";
        Object[] params = new Object[]{user.getName(), user.getPassword(), user.getId()};
        jdbcContext.update(sql, params);
    }

    public void delete(Integer id) throws SQLException, ClassNotFoundException {
        String sql = "delete from userinfo where id=?";
        Object[] params = new Object[]{id};
        jdbcContext.update(sql, params);
    }
}
