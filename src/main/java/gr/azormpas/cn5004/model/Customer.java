package gr.azormpas.cn5004.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer extends User implements Serializable
{
    private String name;
    private ArrayList<Purchase> purchase;

    public Customer (Customer customer)
    {
        super(customer.getUsername(), customer.getPassword());
        this.setName(customer.getName());
        this.setPurchase(customer.getPurchases());
    }

    public Customer(String username, String password, String name)
    {
        super(username, password);
        this.setName(name);
        this.setPurchase(new ArrayList<>());
    }

    public Customer(String username, String password, String name, ArrayList<Purchase> purchase)
    {
        super(username, password);
        this.setName(name);
        this.setPurchase(purchase);
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public ArrayList<Purchase> getPurchases()
    {
        return purchase;
    }
    public void setPurchase(ArrayList<Purchase> purchase)
    {
        this.purchase = purchase;
    }
}
