package org.vm.item;

import java.util.*;

public class ItemManager {
    private final Set<String> duplicatedCheckSet = new HashSet<>();
    private final Map<Integer, Item> indexMap = new HashMap<>();
    private int index = 0;

    private Item getItem(int index) {
        Item item = indexMap.get(index);
        if (item == null) {
            throw new RuntimeException("존재하지 않는 아이템이 선택되었습니다.");
        }

        return item;
    }

    public void addItem(String name, int price, int stock) {
        if (duplicatedCheckSet.contains(name)) {
            throw new RuntimeException("이미 등록된 동일한 이름의 아이템이 존재합니다.");
        }

        Item item = new Item(name, price, stock);
        duplicatedCheckSet.add(name);
        indexMap.put(index, item);
        index++;
    }

    public boolean isSoldOut(int index) {
        Item item = getItem(index);

        return item.isSoldOut();
    }

    public boolean consumeStock(int index) {
        Item item = getItem(index);
        item.decreaseStock();
        return true;
    }

    public  boolean isItemExists(int index) {
        return indexMap.containsKey(index);
    }

    public Set<Integer> getAllIndexes() {
        return indexMap.keySet();
    }

    public int getStock(int index) {
        return indexMap.get(index).getStock();
    }

    public int getPrice(int index) {
        return indexMap.get(index).getPrice();
    }

    public String getName(int index) {
        return indexMap.get(index).getName();
    }

    static class Item {
        private static int INDEX_NUMBER;

        private final String name;
        private final int price;

        private int stock;

        public Item(String name, int price, int stock) {
            this.name = name;
            this.price = price;
            this.stock = stock;

            INDEX_NUMBER++;
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

        public boolean isSoldOut() {
            return stock == 0;
        }

        public boolean decreaseStock() {
            if (stock == 0) return false;
            stock--;
            return true;
        }
    }
}

