package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User get(Integer id) throws ClassNotFoundException, SQLException {
        StatementStrategy statementStrategy = new GetStatementStrategy(id);
        return jdbcContext.JdbcContextForGet(statementStrategy);
    }

    public void insert(User user) throws SQLException, ClassNotFoundException {
        StatementStrategy statementStrategy = new InsertStatementStrategy(user);
        jdbcContext.JdbcContextForInsert(statementStrategy, user);
    }

    public void update(User user) throws SQLException, ClassNotFoundException {
        StatementStrategy statementStrategy = new UpdateStatementStrategy(user);
        jdbcContext.JdbcContextForUpdate(statementStrategy);
    }

    public void delete(Integer id) throws SQLException, ClassNotFoundException {
        StatementStrategy statementStrategy = new DeleteStatementStrategy(id);
        jdbcContext.JdbcContextForDelete(statementStrategy);
    }
}
