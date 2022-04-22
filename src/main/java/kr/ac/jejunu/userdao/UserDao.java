package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User get(Integer id) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new GetStatementStrategy();
            preparedStatement = statementStrategy.makeStatement(connection, id);

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

    public void insert(User user) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new InsertStatementStrategy();
            preparedStatement = statementStrategy.makeStatement(connection, user);

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

    public void update(User user) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = dataSource.getConnection();

            StatementStrategy statementStrategy = new UpdateStatementStrategy();
            preparedStatement = statementStrategy.makeStatement(connection, user);

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

    public void delete(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = dataSource.getConnection();

            StatementStrategy statementStrategy = new DeleteStatementStrategy();
            preparedStatement = statementStrategy.makeStatement(connection, id);

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
}
