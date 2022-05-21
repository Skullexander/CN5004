package gr.azormpas.cn5004.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop extends User implements Serializable
{
    private String shopName;
    private String type;
    private String details;
    private ArrayList<Product> inventory;

    public Shop (Shop shop)
    {
        super(shop.getUsername(), shop.getPassword());
    }

    public Shop(String username, String password, String name, String type)
    {
        super(username, password);
        this.setShopName(name);
        this.setType(type);
        this.setDetails(null);
        this.setInventory(new ArrayList<>());
    }

    public String getShopName()
    {
        return shopName;
    }
    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getDetails()
    {
        return details;
    }
    public void setDetails(String details)
    {
        this.details = details;
    }
    public ArrayList<Product> getInventory()
    {
        return inventory;
    }
    public void setInventory(ArrayList<Product> inventory)
    {
        this.inventory = inventory;
    }

    @Override
    public String toString ()
    {
        return String.format("%s (%s)", getShopName(), getType());
    }
}
