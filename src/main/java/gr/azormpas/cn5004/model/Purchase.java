package gr.azormpas.cn5004.model;

import java.io.Serializable;
import java.util.HashMap;

public class Purchase extends Customer implements Serializable
{
    private int ID;
    private String date;
    private HashMap<Integer, Product> items;
    private double cost;
    private int state;

    public Purchase (Customer customer, int amount, Product product, double cost)
    {
        super(customer);
        this.setDate(String.valueOf(new java.util.Date()));
        this.setItems(new HashMap<>());
        this.getItems().put(amount, product);
        this.setCost(cost);
        this.setState(0);
        customer.getPurchases().add(this);
    }

    public Purchase (Customer customer, HashMap<Integer, Product> items, double cost)
    {
        super(customer);
        this.setDate(String.valueOf(new java.util.Date()));
        this.setItems(items);
        this.setCost(cost);
        this.setState(0);
        customer.getPurchases().add(this);
    }

    public int getID()
    {
        return ID;
    }
    public void setID(int ID)
    {
        this.ID = ID;
    }
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public HashMap<Integer, Product> getItems()
    {
        return items;
    }
    public void setItems(HashMap<Integer, Product> items)
    {
        this.items = items;
    }
    public double getCost()
    {
        return cost;
    }
    public void setCost(double cost)
    {
        this.cost = cost;
    }
    public int getState()
    {
        return state;
    }
    public void setState(int state)
    {
        this.state = state;
    }

    @Override
    public String toString()
    {
        return "Purchase{" + "ID=" + ID + ", date='" + date + '\'' + ", cost=" + cost + ", state=" + state + '}';
    }

    public String getStatus()
    {
        switch (this.state)
        {
            case (-1):
                return "Cancelled";
            case (0):
                return "Pending";
            case (1):
                return "Processed";
            case (2):
                return "Prepared";
            case (3):
                return "Outgoing";
            case (4):
                return "In transit";
            case (5):
                return "Delivered";
            default:
                return "Unknown";
        }
    }

    public void updateStatus(String symbol)
    {
        if (symbol.equals("+")) this.setState(Integer.sum(this.getState(), 1));
        else this.setState(Integer.sum(this.getState(), -1));
    }
}
