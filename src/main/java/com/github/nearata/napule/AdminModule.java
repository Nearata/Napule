package com.github.nearata.napule;

public final class AdminModule
{
    private String key, label, description;
    
    public AdminModule(String key, String label, String description)
    {
        this.key = key;
        this.label = label;
        this.description = description;
    }
    
    public final void setKey(final String key)
    {
        this.key = key;
    }
    
    public final void setLabel(final String label)
    {
        this.label = label;
    }
    
    public final void setDescription(final String description)
    {
        this.description = description;
    }
    
    public final String getKey()
    {
        return this.key;
    }
    
    public final String getLabel()
    {
        return this.label;
    }
    
    public final String getDescription()
    {
        return this.description;
    }
}
