package upm.app.data.modelos;

public class TennisCourt extends Entity{
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
        if(!surfaceType.equalsIgnoreCase("arcilla") && !surfaceType.equalsIgnoreCase("cesped") && !surfaceType.equalsIgnoreCase("dura")){
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
}
