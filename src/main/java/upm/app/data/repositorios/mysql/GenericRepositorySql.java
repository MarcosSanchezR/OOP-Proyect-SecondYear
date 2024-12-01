package upm.app.data.repositorios.mysql;

import upm.app.data.modelos.Entity;
import upm.app.data.repositorios.GenericRepository;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public abstract class GenericRepositorySql <T extends Entity> implements GenericRepository<T> {
    private final Connection connection;

    protected GenericRepositorySql(Connection connection) {
        this.connection = connection;
    }

    private static String message(String sql, SQLException e) {
        return "SQL: " + sql + " ===>>> " + e;
    }

    protected Integer executeInsertGeneratedKey(String sql, Object... values) {
        LogManager.getLogger(this.getClass()).debug(() -> "Sql: " + sql);
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            this.assignValues(preparedStatement, values);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            throw new UnsupportedOperationException("SQL: " + sql + " ===>>> No se ha podido generar la id");
        } catch (SQLException e) {
            throw new UnsupportedOperationException(message(sql, e));
        }
    }

    private void assignValues(PreparedStatement preparedStatement, Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);
        }
    }

    protected void executeUpdate(String sql, Object... values) {
        LogManager.getLogger(this.getClass()).debug(() -> "Sql: " + sql);
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            this.assignValues(preparedStatement, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UnsupportedOperationException(message(sql, e));
        }
    }

    protected List<T> executeQueryConvert(String sql, Object... values) {
        LogManager.getLogger(this.getClass()).debug(() -> ("Sql: " + sql));
        List<T> entities = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            this.assignValues(preparedStatement, values);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entities.add(this.convertToEntity(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            throw new UnsupportedOperationException(message(sql, e));
        }
    }

    protected abstract T convertToEntity(ResultSet resultSet);

    protected <S> List<S> executeQueryFunctional(String sql, Function<ResultSet, S> convert, Object... values) {
        LogManager.getLogger(this.getClass()).debug(() -> ("Sql: " + sql));
        List<S> entities = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            this.assignValues(preparedStatement, values);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entities.add(convert.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException(message(sql, e));
        }
        return entities;
    }


}
