package com.example.crud_lite.data.entity;

public class ShoppingItem {
    private Long id;
    private String name;
    private Integer amount;

    public ShoppingItem(Long id, String name, Integer amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public ShoppingItem(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public ShoppingItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
