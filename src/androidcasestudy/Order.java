package androidcasestudy;

/**
 * represents a typical order.
 * It has a pick up and drop off locations as well as  the distance between
 */
public class Order {

    private Location pick_up;
    private Location drop_off;
    private double distance;
    public Order(Location pick, Location drop){
         pick_up=pick;
         drop_off=drop;
         this.distance=getDistanceBetween(pick_up,drop_off);
    }
    public Location getPick_up() {
        return pick_up;
    }

    public void setPick_up(Location pick_up) {
        this.pick_up = pick_up;
    }

    public Location getDrop_off() {
        return drop_off;
    }

    public void setDrop_off(Location drop_off) {
        this.drop_off = drop_off;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    private double getDistanceBetween(Location a, Location b){

        double sumSquares=Math.pow(a.getLat()-b.getLat(),2)+Math.pow(a.getLon()-b.getLon(),2);
        return  Math.sqrt(sumSquares)* 100;

    }
    @Override
    public boolean equals(Object order){
        Order o=(Order) order;
           return  this.distance==o.distance && this.drop_off.equals(o.drop_off)&& this.pick_up.equals(o.pick_up);
    }
    @Override
    public  String toString(){
        return "Pick-Up : "+pick_up.toString()+" Drop-off : "+drop_off.toString()+" Distance : "+distance;
    }
}
