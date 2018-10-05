package androidcasestudy;

import java.util.*;

/**
 * Created by Chirchir on 10/3/2018.
 *
 * contains  general utilities  for manipulating a collection of orders
 */
public class OrderUtils {
    public static  void main(String[] args){

        //list of probable orders
        Order order5= new Order(new Location(2.71,4.7),new Location(0.22,2.75));
        Order order6= new Order(new Location(2.72,4.7),new Location(0.22,2.75));
        Order order7= new Order(new Location(3.40,3.7),new Location(4.2,.75));
        Order order8= new Order(new Location(2.42,4.7),new Location(2.2,2.75));
        Order order4= new Order(new Location(4.45,6.7),new Location(3.2,1.7));
        Order order9= new Order(new Location(3.45,3.7),new Location(0.2,2.7));
        // A probable list of Orders toi be fed into the algorithm
        List<Order> orderList=Arrays.asList(order4,order6,order8,order5,order9,order7);
        // Usage of the algorithm
        Map<String, List<Order>> mergerdOrder=getMergeableOrders(orders_with_same_pick_up_location(orderList));
        // Output of the results from the algorithm
         mergerdOrder.forEach((x,y)-> System.out.println(x+"   "+y));

    }
    /**checks if two locations are near each other
     * Note the value here has been hard coded... For dynamism it could passed it as an argument
     *  * */
    public static  boolean on_the_same_point(Location a, Location b){

          return !(getDistanceBetween(a,b)>50);

    }
/** Outputs the distance between two locations
 * */
    public static double getDistanceBetween(Location a, Location b){

             double sumSquares=Math.pow(a.getLat()-b.getLat(),2)+Math.pow(a.getLon()-b.getLon(),2);
              return  Math.sqrt(sumSquares)* 100;

    }
     /**
      * groups Orders which have the same pick up location
      * */
    public static List<List<Order>> orders_with_same_pick_up_location(List<Order> orders){

           Order current_order0;
           List<List<Order>> mergeableOrders= new ArrayList<>();
            while(orders.size()>0){
                List<Order> orders_sharing_pl=new ArrayList<>();
                current_order0=orders.get(0);
                orders=getSubOrderList(current_order0,orders);
                orders_sharing_pl.add(current_order0);
                for(int j=0;j<orders.size();j++){
                   Order current_order1=orders.get(j);
                   if(on_the_same_point(current_order0.getPick_up(),current_order1.getPick_up())){
                         orders_sharing_pl.add(current_order1);
                         orders=getSubOrderList(current_order1,orders);
                   }
               }
               mergeableOrders.add(orders_sharing_pl);
            }
           return mergeableOrders;


    }
    /** Return a Map with Orders that can be delivered cheaply
     * */
    public static Map<String,List<Order>> getMergeableOrders(List<List<Order>> orders){

         Map<String,List<Order>> mergeableOrders= new HashMap<>();
         for(int i=0;i<orders.size();i++){
             List<List<Order>> pairedOrderList=ordersOnTheSamePath(orders.get(i));
             for(int j=0;j<pairedOrderList.size();j++){
                 mergeableOrders.put("order"+i+""+j,pairedOrderList.get(j));
             }
         }
          return mergeableOrders;

    }
    public static  List<List<Order>> pairOrdersHelper(List<Order> orders){

         List<List<Order>> mergedOrdersList= new ArrayList<>();
         orders.sort((o1, o2) -> {
             if(o1.getDistance()>o2.getDistance())
                  return 1;
             else if(o1.getDistance()==o2.getDistance())
                 return 0;

             return -1;
         });
         for(int k=0;k<orders.size();k++){
             List<Order> mergedOrders= new ArrayList<>();
              Order order= orders.get(k);
              mergedOrders.add(order);
              //orders.remove(order);
              double shortest_distance=order.getDistance();
              for(Order order1: orders){
                     double min_distance=0;
                     Order min_distance_order=null;
                     min_distance= getDistanceBetween(order.getDrop_off(),order1.getDrop_off());
                         if(min_distance<shortest_distance){

                               shortest_distance=min_distance;
                               min_distance_order=order1;

                         }

                      if(min_distance_order!=null)
                            mergedOrders.add(min_distance_order);

              }

         }

          return  mergedOrdersList;

    }
    /**Receives as argument a list of Order objects and returns a list of list of Order objects whose drop off locations are near each other.
     * */
    public static List<List<Order>> ordersOnTheSamePath(List<Order> orders){
        List<List<Order>> ordersOnthesamepath= new ArrayList<>();
        for(int i=0;i<orders.size();i++){
            List<Order> orderList;
            orderList = getPeerOrders(orders.get(i),orders);
            //orderList.add(0,orders.get(i));
            ordersOnthesamepath.add(orderList);
        }
        return ordersOnthesamepath;
    }
       /**Receives as arguments Order and a list of Order objects  and returns a list of Order objects whose drop off locations are near each other.
        * */
    public static List<Order> getPeerOrders(Order order, List<Order> orderList){
        double orderDistance=order.getDistance();
        double distanceBetween=0;
        Order  peerOrder= null;
        List<Order>  peerOrders= new ArrayList<>();
        for(int k=0;k<orderList.size();k++){

            distanceBetween=getDistanceBetween(order.getDrop_off(), orderList.get(k).getDrop_off());
            if(distanceBetween<orderDistance){
                 orderDistance=distanceBetween;
                 peerOrder=orderList.get(k);

            }
        }
        if(peerOrder==null){

            return  peerOrders;

        }else{

             peerOrders.add(peerOrder);
             List<Order> subList= getSubOrderList(peerOrder,orderList);
             peerOrders.addAll(getPeerOrders(peerOrder,subList));
             return  peerOrders;

        }

    }
   /**Receives as arguments an Order and a list of Order objects and return a list with less the Order param
    *
    * */
    public static  List<Order> getSubOrderList(Order order,List<Order>orderList){
        List<Order> orders= new ArrayList<>();
        for(Order order1: orderList){
            if(!order1.equals(order)){
               orders.add(order1);
            }
        }
         return  orders;
    }




}
