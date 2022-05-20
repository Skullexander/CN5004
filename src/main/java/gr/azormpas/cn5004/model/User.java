package gr.azormpas.cn5004.model;

import java.time.format.DateTimeFormatter;

public class User
{
    private String username;
    private String password;
    private String lastLogin;
    private boolean isActive;

    public User(String username, String password)
    {
        this.setUsername(username);
        this.setPassword(password);
        this.setLastLogin(DateTimeFormatter.ISO_LOCAL_TIME.toString());
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
