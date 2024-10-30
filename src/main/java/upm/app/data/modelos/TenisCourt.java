package upm.app.data.modelos;

public class TenisCourt extends Entity{
    private String name;
    private String surfaceType;
    private String location;

    public TenisCourt(String name, String surfaceType, String location) {
        this.name = name;
        this.surfaceType = surfaceType;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(String surfaceType) {
        if(!surfaceType.equalsIgnoreCase("arcilla") && !surfaceType.equalsIgnoreCase("cesped") && !surfaceType.equalsIgnoreCase("dura")){
            throw new RuntimeException("No existe ese tipo de pista");
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
