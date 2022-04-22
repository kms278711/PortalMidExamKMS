package kr.ac.jejunu.userdao;

public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }
    public ConnectionMaker connectionMaker() {
        return new JejuConnectionMaker();
    }
}
