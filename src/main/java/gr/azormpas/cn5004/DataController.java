package gr.azormpas.cn5004;

import gr.azormpas.cn5004.model.Customer;
import gr.azormpas.cn5004.model.Product;
import gr.azormpas.cn5004.model.Purchase;
import gr.azormpas.cn5004.model.Settings;
import gr.azormpas.cn5004.model.Shop;
import gr.azormpas.cn5004.model.User;
import org.jetbrains.annotations.NotNull;

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
    private int[] userDetails;
    private Settings settings;

    public DataController()
    {
        try
        {
            file.put("settings", new FileController(DATA_FOLDER, "settings.txt"));
            file.put("customer", new FileController(DATA_FOLDER, "customerData.txt"));
            file.put("shop", new FileController(DATA_FOLDER, "shopData.txt"));
            loadData(DATA_FOLDER.mkdir());
            saveData();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void loadData(boolean exists)
        throws IOException, ClassNotFoundException
    {
        if (exists) System.out.println("Data folder initialized.");
        loadSettings(file.get("settings").create());
        loadShops(file.get("shop").create());
        loadCustomers(file.get("customer").create());
        loadUsers();
    }

    private void loadSettings(boolean exists)
        throws IOException, ClassNotFoundException
    {
        if (exists)
        {
            settings = new Settings();
        }
        else
        {
            try
            {
                objectLoad("settings");
            }
            catch (EOFException ignored)
            {
                System.out.println("Could not find Settings data in file. Loading defaults...");
                settings = new Settings();
            }
        }
    }

    public void loadShops(boolean exists)
        throws IOException, ClassNotFoundException
    {
        shops.clear();
        if (!exists)
        {
            try
            {
                objectLoad("shop");
            }
            catch (EOFException ignored)
            {
                System.out.println("Could not find Shop data in file. Loading defaults...");
            }
            if (settings.isUseDefaultData()) addDefaultData("shop");
        }
    }

    public void loadCustomers(boolean exists)
        throws IOException, ClassNotFoundException
    {
        customers.clear();
        if (!exists)
        {
            try
            {
                objectLoad("customer");
            }
            catch (EOFException ignored)
            {
                System.out.println("Could not find Customer data in file. Loading defaults...");
            }
            if (settings.isUseDefaultData()) addDefaultData("customer");
        }
    }

    private void objectLoad(@NotNull String type)
        throws IOException, ClassNotFoundException
    {
        if (type.equals("customer")) customers.addAll((ArrayList<Customer>) file.get(type).load());
        else if (type.equals("settings")) settings = (Settings) file.get(type).load();
        else shops.addAll((ArrayList<Shop>) file.get(type).load());
        file.get(type).loadClose();
    }

    public void loadUsers()
    {
        users.clear();
        if (shops.size() != 0)
        {
            for (Shop shop : shops)
            {
                users.put(shop.getUsername(), shop.getPassword());
            }
        }
        if (customers.size() != 0)
        {
            for (Customer customer : customers)
            {
                users.put(customer.getUsername(), customer.getPassword());
            }
        }
    }

    public void saveData()
        throws IOException
    {
        saveSettings();
        saveShops();
        saveCustomers();
    }

    public void saveSettings()
        throws IOException
    {
        file.get("settings").save(settings);
    }

    public void saveShops()
        throws IOException
    {
        if (shops.size() != 0) file.get("shop").save(shops);
    }

    public void saveCustomers()
        throws IOException
    {
        if (customers.size() != 0) file.get("customer").save(customers);
    }

    public void getUserLocation(String username)
    {
        userDetails = new int[]{-1, -1};
        if (ADMIN_USER.getUsername().equals(username))
        {
            userDetails = new int[]{0, 0};
        }
        for (Shop shop : shops)
        {
            if (shop.getUsername().equals(username))
            {
                userDetails = new int[]{1, shops.indexOf(shop)};
            }
        }
        for (Customer customer : customers)
        {
            if (customer.getUsername().equals(username))
            {
                userDetails = new int[]{2, customers.indexOf(customer)};
            }
        }
    }

    public int getLoggedUserType()
    {
        return userDetails[0];
    }

    public int getLoggedUserLocation()
    {
        return userDetails[1];
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

    public void addDefaultData(@NotNull String target)
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

    public ArrayList<Shop> getShops()
    {
        return shops;
    }

    public ArrayList<Customer> getCustomers()
    {
        return customers;
    }

    public Settings getSettings()
    {
        return settings;
    }
}
