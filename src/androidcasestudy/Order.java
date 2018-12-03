package androidcasestudy;

import java.util.ArrayList;
import java.util.List;

/**
 * Order class
 * Defines what a typical order contains
 */
public class Order {
    Location pick_up;
    Location drop_off;
    double   distance;
    List<Order> pairList;
    double combination_distance;
    double pick_distance;
    double drop_distance;
    String  orderId;
    String  combine_sequence;
    double  distance_to_first_drop;
    double  distance_to_nearest_order;
    double    distanceIndex;
    public Order(Location pick_loc,Location drop_loc,String id){
        this.pick_up=pick_loc;
        this.drop_off=drop_loc;
        this.distance=distanceBetween(pick_loc,drop_loc);
        this.combination_distance=distance;
        this.pairList= new ArrayList<>();
        this.pairList.add(this);
        pick_distance=0;
        drop_distance=distanceBetween(pick_loc,drop_loc);
        orderId=id;
        combine_sequence=id;
        distance_to_nearest_order=Integer.MAX_VALUE;
        distanceIndex=Integer.MIN_VALUE;
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

    public List<Order> getPair_order() {
        return pairList;
    }
    public double getPair_distance() {
        return combination_distance;
    }

    public void setPair_distance(double pair_distance) {
        this.combination_distance = pair_distance;
    }
    private int  distanceBetween(Location a, Location b){

        double sumSquares=Math.pow(a.getLat()-b.getLat(),2)+ Math.pow(a.getLon()-b.getLon(),2);
        return  (int)Math.sqrt(sumSquares);

    }
    @Override
    public  String toString(){
        // return "Pick up location : "+this.pick_up.toString()+" Drop off location : "+this.drop_off.toString()+" Distance : "+this.getDistance();
        return  orderId;
    }
    @Override
    public boolean equals(Object order){
        Order o=(Order) order;
        return  this.getDistance()==o.getDistance() && this.drop_off.equals(o.drop_off)&& this.pick_up.equals(o.pick_up);
    }


}
