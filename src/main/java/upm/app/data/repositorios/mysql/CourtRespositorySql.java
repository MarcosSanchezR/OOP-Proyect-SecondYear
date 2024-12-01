package upm.app.data.repositorios.mysql;

import upm.app.data.modelos.TennisCourt;
import upm.app.data.repositorios.CourtRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CourtRespositorySql extends GenericRepositorySql<TennisCourt> implements CourtRepository {

    public CourtRespositorySql(Connection connection) {
        super(connection);
        this.initializeTable();
    }

    private void initializeTable() {
        this.executeUpdate("CREATE TABLE IF NOT EXISTS Court (" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT ," +
                "name VARCHAR(20) UNIQUE NOT NULL," +
                "surfaceType VARCHAR(20)," +
                "location VARCHAR(20))");
    }

    @Override
    public Optional<TennisCourt> findByName(String name) {
        return this.executeQueryConvert("SELECT id, name, surfaceType, location FROM Court WHERE name = ?", name).stream()
                .findFirst();
    }

    @Override
    public TennisCourt create(TennisCourt entity) {
        int id = this.executeInsertGeneratedKey("INSERT INTO Court (name, surfaceType, location) VALUES (?,?,?)",
                entity.getName(), entity.getSurfaceType(), entity.getLocation());
        return this.read(id).orElseThrow(
                () -> new RuntimeException("Unexpected database error due to entity not found: " + id));
    }

    @Override
    public void deleteById(Integer id) {
        this.executeUpdate("DELETE FROM Court WHERE id = ?", id);
    }

    @Override
    public List<TennisCourt> findAll() {
        return this.executeQueryConvert("SELECT id, name, surfaceType, location FROM Court");
    }

    @Override
    public Optional<TennisCourt> read(Integer id) {
        return this.executeQueryConvert("SELECT id, name, surfaceType, location FROM Court WHERE id = ?", id).stream()
                .findFirst();

    }

    @Override
    protected TennisCourt convertToEntity(ResultSet resultSet) {
        try {
            TennisCourt court = new TennisCourt(resultSet.getString("name"), resultSet.getString("surfaceType"),
                    resultSet.getString("location"));
            court.setId(resultSet.getInt("id"));
            return court;
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Retriever court error: " + e.getMessage());
        }
    }
}
