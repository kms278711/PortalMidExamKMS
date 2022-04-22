package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User JdbcContextForGet(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } finally {
            try{
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public void JdbcContextForInsert(StatementStrategy statementStrategy, User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            user.setId(resultSet.getInt(1));
        } finally {
            try{
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void JdbcContextForUpdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);

            preparedStatement.executeUpdate();
        } finally {
            try{
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public User get(String sql, Object[] params) throws SQLException {
        return JdbcContextForGet(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            for(int i=0; i< params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        });
    }

    public void insert(String sql, Object[] params, User user) throws SQLException {
        JdbcContextForInsert(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for(int i=0; i< params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        }, user);
    }

    public void update(String sql, Object[] params) throws SQLException {
        JdbcContextForUpdate(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            for(int i=0; i< params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        });
    }
}
