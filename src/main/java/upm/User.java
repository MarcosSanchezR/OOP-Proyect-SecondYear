package upm;

import java.time.LocalDate;


public class User {
    private static final int EDAD_MAXIMA = 30;
    private static final int EDAD_MINIMA = 18;
    private String nombre;
    private LocalDate fechaNacimineto;
    private int edad;
    private String dni;
    private String email;
    private String genero;

    public User(String nombre, LocalDate fechaNacimineto, String dni, String email, String genero) {
        this.nombre = nombre;
        this.fechaNacimineto = fechaNacimineto;
        this.dni = dni;
        this.email = email;
        this.genero = genero;
        this.edad= LocalDate.now().getYear()- fechaNacimineto.getYear();
    }

    public boolean esDni(String dni) {
        char[] caracteres = dni.toCharArray();
        boolean digitos = false;
        boolean letra = Character.isAlphabetic(caracteres[caracteres.length - 1]);
        for (int i = 0; i < caracteres.length - 1; i++) {
            digitos = Character.isDigit(caracteres[i]);
        }
        return digitos && letra;
    }

    public void cambiarNombre(String nuevo){
        setNombre(nuevo);
    }

    public void cambiarEmail(String nuevo){
        setEmail(nuevo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad > EDAD_MAXIMA) {
            throw new IllegalArgumentException("La edad no puede ser mayor que 30");
        }
        if (edad < EDAD_MINIMA) {
            throw new IllegalArgumentException("La edad no puede ser menor que 18");
        }
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (!esDni(dni)) {
            throw new IllegalArgumentException("El DNI no es valido");
        }
        this.dni = dni;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (!(genero.equalsIgnoreCase("M") || genero.equalsIgnoreCase("H"))){
            throw new IllegalArgumentException("El genero no es valido");
        }
        this.genero = genero;
    }

    public LocalDate getFechaNacimineto() {
        return fechaNacimineto;
    }

    public void setFechaNacimineto(LocalDate fechaNacimineto) {
        this.fechaNacimineto = fechaNacimineto;
    }
}

