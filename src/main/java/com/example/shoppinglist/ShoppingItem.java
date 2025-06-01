package com.example.shoppinglist;

public class ShoppingItem {
    private Long id;
    private String name;
    private boolean purchased;

    public ShoppingItem(){}

    public ShoppingItem(Long id, String name, boolean purchased)
    {
        this.id = id;
        this.name = name;
        this.purchased = purchased;
    }

    public Long getId() {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public boolean getPurchased()
    {
        return purchased;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPurchased(boolean purchased)
    {
        this.purchased = purchased;
    }
}
