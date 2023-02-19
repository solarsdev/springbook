package springbook.user.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy strategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = strategy.makePreparedStatement(connection);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void executeSql(final String query, final Object... params) throws SQLException {
        workWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        for (int i = 0; i < params.length; i++) {
                            preparedStatement.setObject(i + 1, params[i]);
                        }
                        return preparedStatement;
                    }
                }
        );
    }
}
