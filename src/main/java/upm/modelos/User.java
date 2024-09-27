package upm.modelos;

import java.time.LocalDate;
import java.time.Period;


public class User {
    private static final int MAX_AGE = 30;
    private static final int MINIMUM_AGE = 18;
    private String name;
    private LocalDate birthdate;
    private String id;
    private String email;



    public User(String name, LocalDate birthdate, String id, String email) {
        this.name = name;
        this.setBirthdate(birthdate);
        this.setId(id);
        this.setEmail(email);
    }

    public boolean isId(String id) {
        char[] characters = id.toCharArray();
        boolean digits = true;
        boolean letter = Character.isAlphabetic(characters[characters.length - 1]);
        int i=0;
        while (i<characters.length-1 && digits){
            if (!Character.isDigit(characters[i])){
                digits=false;
            }
            i++;
        }
        return digits && letter;
    }

    public void changeName(String nuevo){
        setName(nuevo);
    }

    public void changeEmail(String nuevo){
        setEmail(nuevo);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthdate.getYear();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (!isId(id)) {
            throw new IllegalArgumentException("El DNI no es valido");
        }
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email=email.trim();
        if (!(email.endsWith("@gmail.com") || email.endsWith("@hotmail.com"))){
            throw new IllegalArgumentException("El mail no es valido");
        }
        this.email = email;
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
}

