package gr.azormpas.cn5004.model;

import java.io.Serializable;

public class Settings implements Serializable
{
    private boolean rememberUser;
    private boolean useDefaultData;
    private User loadedUser;

    public Settings()
    {
        setRememberUser(false);
        setUseDefaultData(true);
        loadedUser = new User();
    }

    public boolean isRememberUser()
    {
        return rememberUser;
    }

    public void setRememberUser(boolean rememberUser)
    {
        this.rememberUser = rememberUser;
    }

    public boolean isUseDefaultData()
    {
        return useDefaultData;
    }

    public void setUseDefaultData(boolean useDefaultData)
    {
        this.useDefaultData = useDefaultData;
    }

    public User getLoadedUser()
    {
        return loadedUser;
    }

    public void setLoadedUser(User loadedUser)
    {
        this.loadedUser = loadedUser;
    }
}

