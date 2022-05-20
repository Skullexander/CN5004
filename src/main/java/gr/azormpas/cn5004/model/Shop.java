package gr.azormpas.cn5004.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop extends User implements Serializable
{
    private String name;
    private String type;
    private String info;
    private ArrayList<Product> inventory;

    public Shop (Shop shop)
    {
        super(shop.getUsername(), shop.getPassword());
    }

    public Shop(String username, String password, String name, String type)
    {
        super(username, password);
        this.setName(name);
        this.setType(type);
        this.setInfo(null);
        this.setInventory(new ArrayList<>());
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getInfo()
    {
        return info;
    }
    public void setInfo(String info)
    {
        this.info = info;
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
        return String.format("%s (%s)", getName(), getType());
    }
}
