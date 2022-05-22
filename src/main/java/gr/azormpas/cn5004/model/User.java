package gr.azormpas.cn5004.model;

import java.io.Serializable;

public class User implements Serializable
{
    private String username;
    private String password;
    private String lastLogin;
    private boolean isActive;

    public User()
    {
        this.setUsername(null);
        this.setPassword(null);
        this.setLastLogin(null);
        this.setActive(true);
    }

    public User(String username, String password)
    {
        this.setUsername(username);
        this.setPassword(password);
        this.setLastLogin(String.valueOf(new java.util.Date()));
        this.setActive(false);
    }

    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getLastLogin()
    {
        return lastLogin;
    }
    public void setLastLogin(String lastLogin)
    {
        this.lastLogin = lastLogin;
    }
    public boolean isActive()
    {
        return isActive;
    }
    public void setActive(boolean active)
    {
        this.isActive = active;
    }

    @Override
    public String toString()
    {
        return null;
    }
}
