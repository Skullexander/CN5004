package gr.azormpas.cn5004.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer extends User implements Serializable
{
    private String customerName, address, country;
    private ArrayList<Purchase> purchase;

    public Customer (Customer customer)
    {
        super(customer.getUsername(), customer.getPassword());
        this.setCustomerName(customer.getCustomerName());
        this.setPurchases(customer.getPurchases());
        this.setAddress(customer.getAddress());
        this.setCountry(customer.getCountry());
    }

    public Customer(String username, String password, String name, String address, String country)
    {
        super(username, password);
        this.setCustomerName(name);
        this.setAddress(address);
        this.setCountry(country);
        this.setPurchases(new ArrayList<>());
    }

    public Customer(String username, String password, String name, String address, String country, ArrayList<Purchase> purchase)
    {
        super(username, password);
        this.setCustomerName(name);
        this.setAddress(address);
        this.setCountry(country);
        this.setPurchases(purchase);
    }

    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerName(String name)
    {
        this.customerName = name;
    }
    public ArrayList<Purchase> getPurchases()
    {
        return purchase;
    }
    public void setPurchases(ArrayList<Purchase> purchase)
    {
        this.purchase = purchase;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getCountry()
    {
        return country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return String.format("%s (%s @%s)", getCustomerName(), getAddress(), getCountry());
    }
}
