package com.booleanuk.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Basket {
    private ArrayList<ItemInterface> items;
    private Inventory inventory;
    private int basketCapacity;
    private int sumCosts;
    private HashMap<String, Integer> itemsMap;
//    private LocalDateTime dateTime;
//    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//    private String dateString = dtf.format(dateTime);


    public Basket(Inventory inventory) {
        this.setItems(new ArrayList<>());
        this.setBasketCapacity(100);
        this.setInventory(inventory);
        this.setItemsMap(new HashMap<>());
    }

    public boolean addItem(String sku){
        if (this.getItems().size() < this.getBasketCapacity()) {
            if (getInventory().searchItem(sku) == null){
                System.out.println("Can not add item because this item does not exist!");
                return false;
            } else if(getInventory().searchItem(sku).getSku().equals(sku)) {
                this.getItems().add(getInventory().searchItem(sku.toUpperCase()));
                setSumCosts((int) (getSumCosts() + (this.getInventory().searchItem(sku).getPrice() * 100)));
                return true;
            }
        System.out.println("Basket is full, could not add bagel!");
        }
            return false;
    }

    public boolean removeItem(String sku) {
        boolean itemExistsInBasket = false;
        for (int i = 0; i < this.getItems().size(); i++) {
            if (this.getItems().get(i).getSku().equals(sku)) {
                itemExistsInBasket = true;
                setSumCosts((int) (getSumCosts() - (this.getInventory().searchItem(sku).getPrice() * 100)));
                break;
            }
        }

        if(itemExistsInBasket) {
            this.getItems().remove(this.getInventory().searchItem(sku));
            return true;
        }
            System.out.println("The bagel does not exist in the basket!");
            return false;

    }

    public boolean updateBasketCapacity(int newCapacity) {
        if(newCapacity <= 0) {
            System.out.println("Cannot update basket capacity to zero or less.");
            return false;
        } else if (newCapacity < this.getItems().size()) {
            System.out.println("Cannot update basket capacity to a size smaller than current basket size.");
            return false;
        }
        this.setBasketCapacity(newCapacity);
        return true;
    }

    public double totalCost () {
        int countOnionBagels = 0;
        int numOnionBagelDiscounts = 0;
        int totalOnionBagelDiscount = 0;
        int countPlainBagels = 0;
        int numPlainBagelDiscounts = 0;
        int totalPlainBagelDiscount = 0;
        int remainingPlainBagels = 0;
        int countEverythingBagels = 0;
        int numEverythingBagelDiscounts = 0;
        int totalEverythingBagelDiscount = 0;
        int countBlackCoffee = 0;
        int numBlackCoffeeDiscounts = 0;
        int totalBlackCoffeeDiscount = 0;


        for (int i = 0; i < items.size(); i++) {
            if (this.getItems().get(i).getSku().equals("BGLO")) {
                countOnionBagels++;
                numOnionBagelDiscounts = Math.round((float) countOnionBagels / 6);
                totalOnionBagelDiscount = (int) (numOnionBagelDiscounts * 0.45 * 100);
            } else if (this.getItems().get(i).getSku().equals("BGLP")) {
                countPlainBagels++;
                numPlainBagelDiscounts = Math.round((float) countPlainBagels / 12);
                totalPlainBagelDiscount = (int) (numPlainBagelDiscounts * 0.69 * 100);
            } else if (this.getItems().get(i).getSku().equals("BGLE")) {
                countEverythingBagels++;
                numEverythingBagelDiscounts = Math.round((float) countEverythingBagels / 12);
                totalEverythingBagelDiscount = (int) (numEverythingBagelDiscounts * 0.45 * 100);
            } else if (this.getItems().get(i).getSku().equals("COFB")) {
                countBlackCoffee++;
                if (countPlainBagels % 12 > 0) {
                    remainingPlainBagels = countPlainBagels % 12;
                    numBlackCoffeeDiscounts = Math.min(countBlackCoffee, remainingPlainBagels);
                    totalBlackCoffeeDiscount = (int) (numBlackCoffeeDiscounts * 0.13 * 100);
                }
            }
        }
        return (double) ((getSumCosts() - totalOnionBagelDiscount - totalPlainBagelDiscount - totalEverythingBagelDiscount - totalBlackCoffeeDiscount) / 100.0);
    }

    public double itemPrice(String sku) {
        if(this.getInventory().searchItem(sku) == null){
            return 0.00;
        }
        return this.getInventory().searchItem(sku).getPrice();
    }



    public ArrayList<ItemInterface> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemInterface> items) {
        this.items = items;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getBasketCapacity() {
        return basketCapacity;
    }

    public void setBasketCapacity(int basketCapacity) {
        this.basketCapacity = basketCapacity;
    }

    public int getSumCosts() {
        return sumCosts;
    }

    public void setSumCosts(int sumCosts) {
        this.sumCosts = sumCosts;
    }

    public HashMap<String, Integer> getItemsMap() {
        return itemsMap;
    }

    public void setItemsMap(HashMap<String, Integer> itemsMap) {
        this.itemsMap = itemsMap;
    }

//    public String getDateString() {
//        return dateString;
//    }
//
//    public void setDateString(String dateString) {
//        this.dateString = dateString;
//    }
}
