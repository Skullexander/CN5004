package gr.azormpas.cn5004.controller;

import gr.azormpas.cn5004.model.Customer;
import gr.azormpas.cn5004.model.Product;
import gr.azormpas.cn5004.model.Purchase;
import gr.azormpas.cn5004.model.Shop;
import gr.azormpas.cn5004.model.User;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataController
{
    private final User ADMIN_USER = new User("admin", "admin");
    private final ArrayList<Shop> shops = new ArrayList<>();
    private final ArrayList<Customer> customers = new ArrayList<>();
    private final HashMap<String, String> users = new HashMap<>();

    private final File DATA_FOLDER = new File("../data");
    private final HashMap<String, FileController> file = new HashMap<>();

    public DataController()
    {
        try
        {
            file.put("customer", new FileController(DATA_FOLDER, "customerData.txt"));
            file.put("shop", new FileController(DATA_FOLDER, "shopData.txt"));
            init();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void init()
        throws IOException, ClassNotFoundException
    {
        loadAll(DATA_FOLDER.mkdir());
        saveAll();
    }

    public void loadAll(boolean exists)
        throws IOException, ClassNotFoundException
    {
        if (exists) System.out.println("Data folder initialized.");
        loadShops(file.get("shop").create());
        loadCustomers(file.get("customer").create());
        loadUsers();
    }

    public void loadShops(boolean exists)
        throws IOException, ClassNotFoundException
    {
        shops.clear();
        if (exists)
        {
            addDefaultData("shop");
        }
        else
        {
            try
            {
                objectLoad("shop");
                System.out.println(shops.size());
                System.out.println(shops.get(0).getInventory().size());
                System.out.println(shops.get(1).getInventory().size());
                System.out.println(shops.get(2).getInventory().size());
            }
            catch (EOFException ignored)
            {
                System.out.println("Could not find Shop data in file. Loading defaults...");
                addDefaultData("shop");
            }
        }
    }

    public void loadCustomers(boolean exists)
        throws IOException, ClassNotFoundException
    {
        customers.clear();
        if (exists)
        {
            addDefaultData("customer");
        }
        else
        {
            try
            {
                objectLoad("customer");
                System.out.println(customers.size());
                System.out.println(customers.get(0).getPurchases().size());
                System.out.println(customers.get(1).getPurchases().size());
                System.out.println(customers.get(2).getPurchases().size());
            }
            catch (EOFException ignored)
            {
                System.out.println("Could not find Customer data in file. Loading defaults...");
                addDefaultData("customers");
            }
        }
    }

    private void objectLoad(String type)
        throws IOException, ClassNotFoundException
    {
        if (type.equals("customer")) customers.addAll((ArrayList<Customer>) file.get(type).load());
        else shops.addAll((ArrayList<Shop>) file.get(type).load());
        file.get(type).loadClose();
    }

    public void loadUsers()
    {
        users.clear();
        for (Shop shop : shops)
        {
            users.put(shop.getUsername(), shop.getPassword());
        }
        for (Customer customer : customers)
        {
            users.put(customer.getUsername(), customer.getPassword());
        }
    }

    public void saveAll()
        throws IOException
    {
        saveShops();
        saveCustomers();
    }

    public void saveShops()
        throws IOException
    {
        file.get("shop").save(shops);
    }

    public void saveCustomers()
        throws IOException
    {
        file.get("customer").save(customers);
    }

    public boolean hasUser(String value)
    {
        return users.containsKey(value);
    }

    public boolean verifyUser(String username, String password)
    {
        if (hasUser(username)) return users.get(username).equals(password);
        else return checkIfAdmin(username, password);
    }

    private boolean checkIfAdmin(String username, String password)
    {
        return (ADMIN_USER.getUsername().equals(username) && ADMIN_USER.getPassword().equals(password));
    }

    public void addDefaultData(String target)
    {
        if (target.equals("shop"))
        {
            shops.add(new Shop("test1", "test1", "Shop1", "retail"));
            shops.add(new Shop("test3", "test3", "Shop2", "producer"));
            shops.add(new Shop("test5", "test5", "Shop3", "mixed"));

            shops.get(0).getInventory().add(new Product(shops.get(0), "abc", 2.5, true));
            shops.get(0).getInventory().add(new Product(shops.get(0), "def", 12.45672));
            shops.get(1).getInventory().add(new Product(shops.get(1), "ghi", 0.54, true));
            shops.get(1).getInventory().add(new Product(shops.get(1), "jkl", 4.57, true));
            shops.get(2).getInventory().add(new Product(shops.get(2), "mno", 1));
            shops.get(2).getInventory().add(new Product(shops.get(2), "pqr", 27.645, true));
        }
        else
        {
            customers.add(new Customer("test2", "test2", "George"));
            customers.add(new Customer("test4", "test4", "Mary"));
            customers.add(new Customer("test6", "test6", "David"));

            customers.get(0).getPurchases().add(new Purchase(customers.get(0), 5, shops.get(0).getInventory().get(0), 15));
            customers.get(0).getPurchases().add(new Purchase(customers.get(0), 4, shops.get(0).getInventory().get(1), 20.4));
            customers.get(1).getPurchases().add(new Purchase(customers.get(1), 1, shops.get(1).getInventory().get(0), 4.535));
            customers.get(1).getPurchases().add(new Purchase(customers.get(1), 12, shops.get(1).getInventory().get(1), 8.5));
            customers.get(2).getPurchases().add(new Purchase(customers.get(2), 7, shops.get(2).getInventory().get(0), 32.4));
            customers.get(2).getPurchases().add(new Purchase(customers.get(2), 5047, shops.get(2).getInventory().get(1), 127.1057542));
        }
    }
}
