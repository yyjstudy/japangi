package org.vm.item;

public class Item {
    private static int INDEX_NUMBER;

    private final String name;
    private final int price;
    private final int index;

    private int stock;

    public Item(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;

        INDEX_NUMBER++;
        this.index = INDEX_NUMBER;
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getIndex() {
        return index;
    }

    public boolean isSoldOut() {
        return stock == 0;
    }

    public boolean decreaseStock() {
        if (stock == 0) return false;
        stock--;
        return true;
    }
}
