package upm.app.data.modelos;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;


public class User extends Entity {
    private static final int MAX_AGE = 30;
    private static final int MINIMUM_AGE = 18;
    private final String name;
    private LocalDate birthdate;
    private String dni;
    private String password;
    private Rol rol;


    public User(String name, LocalDate birthdate, String dni, String password) {
        this.name = name;
        this.setBirthdate(birthdate);
        this.setDni(dni);
        this.password = password;
        this.rol = Rol.PLAYER;
    }

    public boolean validDni(String dni) {
        char[] characters = dni.toCharArray();
        boolean digits = true;
        boolean letter = Character.isAlphabetic(characters[characters.length - 1]);
        int i = 0;
        while (i < characters.length - 1 && digits) {
            if (!Character.isDigit(characters[i])) {
                digits = false;
            }
            i++;
        }
        return digits && letter;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthdate.getYear();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (!validDni(dni)) {
            throw new InvalidAttributeException("El DNI no es valido");
        }
        this.dni = dni;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        int age = Period.between(birthdate, LocalDate.now()).getYears();
        if (age < MINIMUM_AGE || age > MAX_AGE) {
            throw new InvalidAttributeException("La edad debe estar entre 18 y 30 años.");
        }
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(birthdate, user.birthdate) && Objects.equals(dni, user.dni) && Objects.equals(password, user.password) && rol == user.rol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, birthdate, dni, password, rol);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", dni='" + dni + '\'' +
                ", rol='" + rol + '\'' +
                ", password='***'" +
                '}';
    }
}

