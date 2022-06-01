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
import java.util.Arrays;
import java.util.HashMap;

public class DataController
{
    private final User ADMIN_USER = new User("admin", "admin");
    private final ArrayList<Shop> DEFAULT_SHOPS = populateShops();
    private final ArrayList<Customer> DEFAULT_CUSTOMERS = populateCustomers();
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
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadData()
        throws IOException, ClassNotFoundException
    {
        if (DATA_FOLDER.mkdir()) System.out.println("Data folder initialized.");
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

    private boolean hasUser(String value)
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

    private ArrayList<Shop> populateShops()
    {
        ArrayList<Shop> arrayList = new ArrayList<>(3);

        arrayList.add(new Shop("test1", "test1", "Shop1", "retail"));
        arrayList.get(0).getInventory().add(new Product(arrayList.get(0), "abc", 2.5, true));
        arrayList.get(0).getInventory().add(new Product(arrayList.get(0), "def", 12.45672));

        arrayList.add(new Shop("test3", "test3", "Shop2", "producer"));
        arrayList.get(1).getInventory().add(new Product(arrayList.get(1), "ghi", 0.54, true));
        arrayList.get(1).getInventory().add(new Product(arrayList.get(1), "jkl", 4.57, true));

        arrayList.add(new Shop("test5", "test5", "Shop3", "mixed"));
        arrayList.get(2).getInventory().add(new Product(arrayList.get(2), "mno", 1));
        arrayList.get(2).getInventory().add(new Product(arrayList.get(2), "pqr", 27.645, true));

        return arrayList;
    }

    private ArrayList<Customer> populateCustomers()
    {
        ArrayList<Customer> arrayList = new ArrayList<>(3);

        arrayList.add(new Customer("test2", "test2", "George"));
        arrayList.get(0).getPurchases().add(new Purchase(arrayList.get(0), 5, DEFAULT_SHOPS.get(0).getInventory().get(0), 15));
        arrayList.get(0).getPurchases().add(new Purchase(arrayList.get(0), 4, DEFAULT_SHOPS.get(0).getInventory().get(1), 20.4));

        arrayList.add(new Customer("test4", "test4", "Mary"));
        arrayList.get(1).getPurchases().add(new Purchase(arrayList.get(1), 1, DEFAULT_SHOPS.get(1).getInventory().get(0), 4.535));
        arrayList.get(1).getPurchases().add(new Purchase(arrayList.get(1), 12, DEFAULT_SHOPS.get(1).getInventory().get(1), 8.5));

        arrayList.add(new Customer("test6", "test6", "David"));
        arrayList.get(2).getPurchases().add(new Purchase(arrayList.get(2), 7, DEFAULT_SHOPS.get(2).getInventory().get(0), 32.4));
        arrayList.get(2).getPurchases().add(new Purchase(arrayList.get(2), 5047, DEFAULT_SHOPS.get(2).getInventory().get(1), 127.1057542));

        return arrayList;
    }

    public void addDefaultData()
    {
        System.out.println("Before: ");
        System.out.println(Arrays.toString(shops.toArray()));
        System.out.println(Arrays.toString(customers.toArray()));
        System.out.println("After: ");
        shops.addAll(DEFAULT_SHOPS);
        System.out.println(Arrays.toString(shops.toArray()));
        customers.addAll(DEFAULT_CUSTOMERS);
        System.out.println(Arrays.toString(customers.toArray()));
        loadUsers();
    }

    public void removeDefaultData()
    {
        System.out.println("Before: ");
        System.out.println(Arrays.toString(shops.toArray()));
        System.out.println(Arrays.toString(customers.toArray()));
        System.out.println("After: ");
        shops.removeIf(shop -> hasUser(shop.getUsername()));
        System.out.println(Arrays.toString(shops.toArray()));
        customers.removeIf(customer -> hasUser(customer.getUsername()));
        System.out.println(Arrays.toString(customers.toArray()));
        loadUsers();
    }

    public boolean defaultDataExists()
    {
        return defaultShopsExists() || defaultCustomersExists();
    }

    private boolean defaultShopsExists()
    {
        return DEFAULT_SHOPS.stream().anyMatch(shop -> hasUser(shop.getUsername()));
    }

    private boolean defaultCustomersExists()
    {
        return DEFAULT_CUSTOMERS.stream().anyMatch(customer -> hasUser(customer.getUsername()));
    }

    public ArrayList<Shop> getShops()
    {
        return shops;
    }

    public ArrayList<Customer> getCustomers()
    {
        return customers;
    }

    public ArrayList<Product> getProducts()
    {
        return shops.get(getLoggedUserLocation()).getInventory();
    }

    public ArrayList<Purchase> getPurchases()
    {
        return customers.get(getLoggedUserLocation()).getPurchases();
    }

    public Settings getSettings()
    {
        return settings;
    }
}
