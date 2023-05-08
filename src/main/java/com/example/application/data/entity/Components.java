package com.example.application.data.entity;

import jakarta.persistence.Entity;

@Entity
public class Components extends AbstractEntity {

    private String filtered_products;
    private String ursprungsland;

    public String getFiltered_products() {
        return filtered_products;
    }
    public void setFiltered_products(String filtered_products) {
        this.filtered_products = filtered_products;
    }
    public String getUrsprungsland() {
        return ursprungsland;
    }
    public void setUrsprungsland(String ursprungsland) {
        this.ursprungsland = ursprungsland;
    }

}
