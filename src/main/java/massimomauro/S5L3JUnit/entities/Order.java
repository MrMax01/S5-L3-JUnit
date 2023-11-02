package massimomauro.S5L3JUnit.entities;

import massimomauro.S5L3JUnit.enums.OrderStatus;

import java.time.LocalTime;
import java.util.List;

public class Order {
    private long id;
    private Table table;
    private List<MenuElement> orderList;

    private OrderStatus status;
    private LocalTime time;
    private int personNum;
    private double totalCost;


    private double coverCharge;

    public Order(long id, Table table, List<MenuElement> orderList, OrderStatus status, LocalTime time, int personNum) {
        this.id = id;
        this.table = table;
        this.orderList = orderList;
        this.status = status;
        this.time = time;
        this.personNum = personNum;
        this.coverCharge=1.50;
        setTotalCost();

    }

    public void setTotalCost() {

        this.totalCost = this.orderList.stream().mapToDouble(MenuElement::getPrice).sum()+(this.coverCharge*this.personNum);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", table=" + table +
                ", orderList=" + orderList +
                ", status=" + status +
                ", time=" + time +
                ", personNum=" + personNum +
                ", totalCost=" + totalCost +
                ", coverCharge=" + coverCharge +
                '}';
    }
}
