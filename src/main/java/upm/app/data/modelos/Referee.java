package upm.app.data.modelos;

import java.time.LocalDate;
import java.util.Objects;

public class Referee extends User {
    private String category;

    public Referee(String name, LocalDate birthdate, String dni, String password, String category) {
        super(name, birthdate, dni, password);
        this.category = category;
        this.setRol(Rol.REFEREE);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Referee referee = (Referee) o;
        return Objects.equals(category, referee.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category);
    }

    @Override
    public String toString() {
        return "Referee{" +
                "category='" + category + '\'' +
                '}';
    }
}
