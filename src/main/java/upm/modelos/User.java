package upm.modelos;

import java.time.LocalDate;


public class User {
    private static final int MAX_AGE = 30;
    private static final int MINIMUM_AGE = 18;
    private String name;
    private LocalDate birthdate;
    private int age;
    private String id;
    private String email;



    public User(String name, LocalDate birthdate, String id, String email) {
        this.name = name;
        this.birthdate = birthdate;
        this.id = id;
        this.email = email;
        this.age= LocalDate.now().getYear()- birthdate.getYear();
    }

    public boolean isId(String id) {
        char[] characters = id.toCharArray();
        boolean digits = false;
        boolean leter = Character.isAlphabetic(characters[characters.length - 1]);
        for (int i = 0; i < characters.length - 1; i++) {
            digits = Character.isDigit(characters[i]);
        }
        return digits && leter;
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
        return age;
    }

    public void setAge(int age) {
        if (age > MAX_AGE) {
            throw new IllegalArgumentException("La edad no puede ser mayor que 30");
        }
        if (age < MINIMUM_AGE) {
            throw new IllegalArgumentException("La edad no puede ser menor que 18");
        }
        this.age = age;
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
        if (email.endsWith("@gmail.com") || email.endsWith("@hotmail.com")){
            throw new IllegalArgumentException("El mail no es valido");
        }
        this.email = email;
    }


    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}

