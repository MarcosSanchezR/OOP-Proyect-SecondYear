package upm.app.data.modelos;

import java.time.LocalDate;
import java.time.Period;


public class User extends Entity {
    private static final int MAX_AGE = 30;
    private static final int MINIMUM_AGE = 18;
    private final String name;
    private LocalDate birthdate;
    private String dni;


    public User(String name, LocalDate birthdate, String dni) {
        this.name = name;
        this.setBirthdate(birthdate);
        this.setDni(dni); 
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
            throw new IllegalArgumentException("El DNI no es valido");
        }
        this.dni = dni;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        int age = Period.between(birthdate, LocalDate.now()).getYears();
        if (age < MINIMUM_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException("La edad debe estar entre 18 y 30 años.");
        }
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", dni='" + dni + '\'' +
                '}';
    }
}

