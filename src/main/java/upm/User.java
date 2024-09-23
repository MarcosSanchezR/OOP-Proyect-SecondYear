package upm;

public class User {
    private static final int EDAD_MAXIMA = 30;
    private static final int EDAD_MINIMA = 18;
    private String nombre;
    private int edad;
    private String dni;
    private String email;
    private String genero;

    public User(String nombre, int edad, String dni, String email, String genero) {
        this.nombre = nombre;
        this.edad = edad;
        this.dni = dni;
        this.email = email;
        this.genero= genero;
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

    public boolean esEmail(String email){
        boolean resul = false;
        if (email.contains("@")){
            String[] partes = email.split("@");
            if (partes.length == 2 && partes[1].contains(".")){
                String[] dominio= partes[1].split("\\.");
                if (dominio.length == 2){
                    resul=true;
                }
            }

        }
        return resul;
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
        if (esEmail(email)){
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
}

