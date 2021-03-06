package androidcasestudy;

/**
 * Represents a typical location
 * It has latitude as well longitude coordinates
 */
public class Location {
    private double lat;
    private double lon;
    public Location (double lat, double lon){
        this.lat=lat;
        this.lon=lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {

        this.lat = lat;
    }

    public double getLon() {

        return lon;
    }

    public void setLon(double lon) {

        this.lon = lon;
    }

    @Override
    public boolean equals(Object object){

        Location loc=(Location)object;
        return this.lat==loc.lat && this.lon==loc.lon;

    }
    @Override
    public String toString(){

         return "lat : "+lat +"  lon : "+lon;
    }
}
