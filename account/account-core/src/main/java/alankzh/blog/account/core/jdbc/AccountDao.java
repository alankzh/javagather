package alankzh.blog.account.core.jdbc;

import alankzh.blog.account.core.entity.AccountEntity;
import alankzh.blog.base.core.jdbc.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDao {
    public static int insertAccount(AccountEntity accountEntity){
        Connection connection = ConnectionPool.getConnection();

        String sql = "insert into t_account(pwd, login_account, email, mobile)" +
                " values(?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountEntity.getPwd());
            preparedStatement.setString(2, accountEntity.getLoginAccount());
            preparedStatement.setString(3, accountEntity.getEmail());
            preparedStatement.setString(4, accountEntity.getMobile());

            int ef = preparedStatement.executeUpdate();
            return ef;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.close(preparedStatement, connection);
        }
    }

}
