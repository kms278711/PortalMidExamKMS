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
            preparedStatement =
                    connection.prepareStatement("select * from userinfo where id = ?");
            preparedStatement.setInt(1, id);

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
            preparedStatement =
                    connection.prepareStatement("insert into userinfo(name, password) values(?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());

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
}
