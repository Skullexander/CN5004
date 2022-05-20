package gr.azormpas.cn5004.model;

import java.io.Serializable;

public class Product extends Shop implements Serializable
{
    private String name;
    private String info;
    private double cost;
    private boolean isActive;

    public Product(Shop shop, String name, double cost) {
        super(shop);
        this.setName(name);
        this.setInfo(null);
        this.setCost(cost);
        this.setActive(false);
        shop.getInventory().add(this);
    }

    public Product(Shop shop, String name, double cost, boolean isActive) {
        super(shop);
        this.setName(name);
        this.setInfo(null);
        this.setCost(cost);
        this.setActive(isActive);
        shop.getInventory().add(this);
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getInfo()
    {
        return info;
    }
    public void setInfo(String info)
    {
        this.info = info;
    }
    public double getCost()
    {
        return cost;
    }
    public void setCost(double cost)
    {
        this.cost = cost;
    }
    public boolean isActive()
    {
        return isActive;
    }
    public void setActive(boolean active)
    {
        isActive = active;
    }

    @Override
    public String toString()
    {
        return String.format("%s: %.2f€", this.getName(), this.getCost());
    }
}
