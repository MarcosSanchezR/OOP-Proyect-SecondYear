package upm;

public class User {
    private static final int EDAD_MAXIMA=30;
    private static final int EDAD_MINIMA=18;
    private String nombre;
    private int edad;
    private String dni;

    public User(String nombre, int edad, String dni) {
        this.nombre = nombre;
        this.edad = edad;
        this.dni = dni;
    }

    public boolean esDni (String dni){ //8numeros una letra
       /* boolean esLetra;
        char[] caracteres=dni.toCharArray();
        char letra= caracteres[caracteres.length-1];
        for (int i=0; i<caracteres.length-1; i++){
        }
*/

        char[] caracteres=dni.toCharArray();
        boolean digitos = false;
        boolean letra = Character.isAlphabetic(caracteres[caracteres.length-1]);
        for (int i=0; i<caracteres.length-1; i++){
            digitos= Character.isDigit(caracteres[i]);
        }
    return digitos&&letra;
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
        if (edad>EDAD_MAXIMA){
            throw new IllegalArgumentException("La edad no puede ser mayor que 30");
        }
        if (edad<EDAD_MINIMA){
            throw new IllegalArgumentException("La edad no puede ser menor que 18");
        }
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (!esDni(dni)){
            throw new IllegalArgumentException("El DNI no es valido");
        }
        this.dni = dni;
    }
}

