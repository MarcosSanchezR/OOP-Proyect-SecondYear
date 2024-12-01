package upm.app.data.repositorios.mysql;

import upm.app.data.modelos.Rol;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.UserRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositorySql extends GenericRepositorySql<User> implements UserRepository {

    public UserRepositorySql(Connection connection) {
        super(connection);
        this.initializeTable();
    }

    private void initializeTable() {
        this.executeUpdate("CREATE TABLE IF NOT EXISTS UserApp (" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT ," +
                "dni VARCHAR(20) UNIQUE NOT NULL," +
                "password VARCHAR(20)," +
                "name VARCHAR(20)," +
                "birthdate VARCHAR(20)," +
                "rol VARCHAR(20))");
    }


    @Override
    public Optional<User> findByDni(String dni) {
        return this.executeQueryConvert("SELECT id, dni, password, name, birthdate, rol FROM UserApp WHERE dni = ?", dni).stream()
                .findFirst();
    }

    @Override
    public User create(User entity) {
        int id = executeInsertGeneratedKey("INSERT INTO UserApp (dni, password, name, birthdate, rol) VALUES (?,?,?,?,?)",
                entity.getDni(), entity.getPassword(), entity.getName(), entity.getBirthdate(), entity.getRol().name());
        return this.read(id).orElseThrow(
                () -> new RuntimeException("Error de la  base de datos inesperado debido a una entidad no encontrada: " + id));
    }

    @Override
    public void deleteById(Integer id) {
        this.executeUpdate("DELETE FROM UserApp WHERE id = ?", id);
    }

    @Override
    public List<User> findAll() {
        return this.executeQueryConvert("SELECT id, dni, password, name, birthdate, rol FROM UserApp");
    }

    @Override
    public Optional<User> read(Integer id) {
        return this.executeQueryConvert("SELECT id, dni, password, name, birthdate, rol FROM UserApp WHERE id = ?", id).stream()
                .findFirst();

    }

    @Override
    protected User convertToEntity(ResultSet resultSet) {
        try {
            User userBd = new User(resultSet.getString("name"), resultSet.getDate("birthdate").toLocalDate(),
                    resultSet.getString("dni"), resultSet.getString("password"));
            userBd.setId(resultSet.getInt("id"));
            userBd.setRol(Rol.valueOf(resultSet.getString("rol")));
            return userBd;
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Retriever user error: " + e.getMessage());
        }
    }

}
