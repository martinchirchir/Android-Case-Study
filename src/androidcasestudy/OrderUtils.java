package androidcasestudy;

import java.util.*;

/**
 * Created by Chirchir on 10/3/2018.
 *
 * contains  general utilities  for manipulating a collection of orders
 */
public class OrderUtils {
    public static  void main(String[] args){

        /**
         * Sample orders
         * */
        Order o1 = new Order(new Location(10, 10), new Location(30, 30),"Order1");
        Order o2 = new Order(new Location(15, 15), new Location(40, 60),"Order2");
        Order o9 = new Order(new Location(20, 20), new Location(40, 40),"Order9");
        Order o3 = new Order(new Location(30, 20), new Location(50, 20),"Order3");
        Order o4 = new Order(new Location(40, 20), new Location(70, 20),"Order4");
        Order o5 = new Order(new Location(30, 30), new Location(80, 20),"Order5");
        Order o6 = new Order(new Location(40, 10), new Location(80, 30),"Order6");
        Order o7 = new Order(new Location(60, 60), new Location(90, 90),"Order7");
        Order o8 = new Order(new Location(40, 60), new Location(60, 40),"Order8");
        Order o10 = new Order(new Location(30, 30), new Location(10, 10),"Order10");
        /**
         * Sample demo on routing the orders
         * */
        List<Order> orders1 = Arrays.asList(o1, o2, o3,o4,o5,o6,o7,o8,o9,o10);
        Map <String,List<Order>> oc=RouteOrders(orders1,2);
        Set<String> keys =oc.keySet();
        Iterator<String> ki=keys.iterator();
        while(ki.hasNext()){
            List<Order> o =oc.get(ki.next());
            for(Order order: o){
                System.out.print(" "+order);
            }
            System.out.println();
        }


        }
    /**
     * calculates distance between two locations
     * */
    public static  double  distanceBetween(Location loc1, Location loc2){

        double sumSquares=Math.pow(loc1.getLat()-loc2.getLat(),2)+ Math.pow(loc1.getLon()-loc2.getLon(),2);

        return   Math.sqrt(sumSquares);

    }
    /**
     * Returns the direction of an order.
     * */
    public static String  directionOf(Order order){
        Location pic=order.getPick_up();
        Location drop=order.getDrop_off();
        double lat_diff=drop.getLat()-pic.getLat();
        double lon_diff=drop.getLon()-pic.getLon();
        if(lat_diff==0 && lon_diff>0){
            return "3";
        } if(lat_diff==0 && lon_diff<0){
            return "7";
        }
        if(lon_diff==0 && lat_diff>0){
            return "1";
        }
        if(lon_diff==0 && lat_diff<0){
            return "5";
        }
        if(lon_diff>0 && lat_diff>0){
            return "2";
        } if(lon_diff<0 && lat_diff<0){
            return "6";
        }
        if(lon_diff<0 && lat_diff>0){
            return "4";
        } if(lon_diff>0 && lat_diff<0){
            return "8";
        }else {
            return "0";
        }
    }
    public static  Map<String,List<Order>>  sameDirectionOrders( List<Order> orderList){
        Map<String,List<Order>> samedirectionOrders= new HashMap<>();
        for(Order o: orderList){
            String  direction= directionOf(o);
            if(samedirectionOrders.containsKey(direction)){
                List<Order>  keyOrders= samedirectionOrders.get(direction);
                keyOrders.add(o);
                samedirectionOrders.put(direction,keyOrders);

            }else {
                List<Order> keyOrders= new ArrayList<>();
                keyOrders.add(o);
                samedirectionOrders.put(direction,keyOrders);
            }
        }
        return  samedirectionOrders;
    }
    /**
     * Checks whether two orders are headed in the same direction
     * */
    public static boolean hasTheSameDirection(Order o1,Order o2){

        if(directionOf(o1)=="0" ||  directionOf(o2)=="0"){

            return false;
        }else {

            return (directionOf(o1).equals(directionOf(o2)));
        }

    }
    /**
     * Returns a list od orders headed in the same direction with order o1 from orderList
     * */
    public static  List<Order>  sameDirectionOrders(Order o1,List<Order> orderList){
        List<Order> orders= new ArrayList<>();
        for(int i=0;i<orderList.size();i++){
            Order order=orderList.get(i);
            if(!(o1.orderId.equals(order.orderId))){
                if(hasTheSameDirection(o1,orderList.get(i))){
                    orders.add(orderList.get(i));
                }else{
                    //System.out.println("They do not have the same direction");
                }
            }
        }
        return  orders;
    }

    public static Map<String,List<Order>> RouteOrders(List<Order> orderList,int maxCombination){

        Map<String,List<Order>>  orderCombinations=new HashMap<>();

        for(int i=0; i<orderList.size();i++){

            Order parentOrder=orderList.get(i);
            List<Order> ordersInSameDirection=sameDirectionOrders(parentOrder,orderList);
            double  combinationIndex=0;
            for(int size=0; size<maxCombination;size++) {
                ordersInSameDirection=subList(ordersInSameDirection,parentOrder.pairList);
                Order  possibleOrder=null;
                for (int j = 0; j < ordersInSameDirection.size(); j++) {

                    Order order = ordersInSameDirection.get(j);
                    List<Order> possibleCombination = new ArrayList<>();
                    possibleCombination.addAll(parentOrder.pairList);
                    int insertionIndex=parentOrder.pairList.size();
                    // possibleCombination.add(insertionIndex,order);
                    //possibleCombination.add(order);
                    // possibleCombination.add(size,order);
                    possibleCombination.add(order);
                    double sum_individual_cost = sum_individual_cost(possibleCombination);
                    double sum_combined_order_cost = sum_pick_cost(possibleCombination) + sum_drop_off_cost(possibleCombination);
                    //System.out.println(" IC "+sum_individual_cost+" CC "+sum_combined_order_cost+" comb diff "+(sum_individual_cost-sum_combined_order_cost)+" combination Index "+combinationIndex);
                    if (sum_combined_order_cost < sum_individual_cost) {
                        double comb_diff = sum_individual_cost - sum_combined_order_cost;
                        if (comb_diff > combinationIndex) {
                            // parentOrder.pairList = possibleCombination;
                            //System.out.println("Added "+order.orderId+" to the order "+parentOrder.orderId);
                            combinationIndex = comb_diff;
                            possibleOrder=order;
                        }
                    }

                }
                if(possibleOrder!=null)
                    parentOrder.pairList.add(possibleOrder);
                //orderCombinations.put(parentOrder.orderId,parentOrder.pairList);
            }

            orderCombinations.put(parentOrder.orderId,parentOrder.pairList);
        }

        return  orderCombinations;
    }
    /**
     * Calculates the cost of delivering the orders  individually
     * */
    public static double sum_individual_cost(List<Order> orderList){
        double sum=0;
        for(Order order: orderList){
            sum=sum+order.distance;
        }
        return  sum;
    }
    /**
     * Calculates the cost of picking up  the orders
     * */
    public static double sum_pick_cost(List<Order> orderList){
        if(orderList.size()<=1)
            return 0;
        double pick_cost=0;
        Location start=orderList.get(0).getPick_up();
        for(int i=1;i<orderList.size();i++){
            pick_cost=pick_cost+distanceBetween(start,orderList.get(i).pick_up);
            start=orderList.get(i).getPick_up();
        }
        return pick_cost;
    }
    /**
     * Calculates the cost of dropping off    the orders
     * */
    public static double sum_drop_off_cost(List<Order> orderList){
        if(orderList.size()==1)
            return  orderList.get(0).distance;
        if(orderList.size()==0)
            return 0;
        double drop_cost=0;
        int lastIndex=orderList.size()-1;
        Location start=orderList.get(lastIndex).getPick_up();
        for(int i=0;i<orderList.size();i++){
            drop_cost=drop_cost+distanceBetween(start,orderList.get(i).drop_off);
            start=orderList.get(i).getDrop_off();
        }
        return drop_cost;
    }
    /**
     * Returns the difference between the two lists
     * */
    public static List<Order> subList(List<Order> orderList, List<Order> sublist){
        List<Order>  unroutedSublist= new ArrayList<>();
        String idStr="";
        for(Order order: sublist){
            idStr=idStr+order.orderId;
        }
        for(Order order:orderList){
            if(!idStr.contains(order.orderId)){

                unroutedSublist.add(order);

            }
        }
        return unroutedSublist;
    }
}
