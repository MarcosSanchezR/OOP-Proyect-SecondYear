package upm.app.data.modelos;

import java.util.Objects;

public class TennisCourt extends Entity {
    private final String name;
    private String surfaceType;
    private String location;

    public TennisCourt(String name, String surfaceType, String location) {
        this.name = name;
        setSurfaceType(surfaceType);
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(String surfaceType) {
        if (!surfaceType.equalsIgnoreCase("arcilla") && !surfaceType.equalsIgnoreCase("cesped") && !surfaceType.equalsIgnoreCase("dura")) {
            throw new InvalidAttributeException("No existe ese tipo de pista");
        }
        this.surfaceType = surfaceType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TennisCourt court = (TennisCourt) o;
        return Objects.equals(name, court.name) && Objects.equals(surfaceType, court.surfaceType) && Objects.equals(location, court.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surfaceType, location);
    }

    @Override
    public String toString() {
        return "TennisCourt{" +
                "name='" + name + '\'' +
                ", surfaceType='" + surfaceType + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
