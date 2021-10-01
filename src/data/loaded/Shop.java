package data.loaded;

import java.util.*;

public record Shop (String name, List<Item> globalStock, Map<Region, List<Item>> regionalStock, int itemRolls) {

    private static final List<Shop> globalShops = new ArrayList<>();

    public Shop {
        if(regionalStock == null) {
            regionalStock = new HashMap<>();
        }
        for (Region r : Region.values()) {
            if (!regionalStock.containsKey(r)) {
                regionalStock.put(r, new ArrayList<>());
            }
        }
    }

    public static List<Shop> getGlobalShops() {
        return globalShops;
    }

    public boolean hasRegionalItems() {
        for (Map.Entry<Region, List<Item>> e : regionalStock.entrySet()) {
            if (!e.getValue().isEmpty()) {
                return true;
            }
        }
        return false;
    }



    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", globalStock=" + globalStock +
                ", regionalStock=" + regionalItemsToString() +
                ", itemRolls=" + itemRolls +
                '}';
    }

    private String regionalItemsToString() {
        if (!hasRegionalItems()) {
            return "NO_REGIONAL_ITEMS";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Region, List<Item>> entry : regionalStock.entrySet()) {
                sb.append(entry.getKey());
                sb.append(Arrays.toString(entry.getValue().toArray(new Item[0])));
            }
            return sb.toString();
        }
    }

    public static class Builder {

        private String name;
        List<Item> globalStock;
        Map<Region, List<Item>> regionalStock;
        int itemRolls;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setGlobalStock(List<Item> globalStock) {
            this.globalStock = globalStock;
            return this;
        }

        public Builder setRegionalStock(Map<Region, List<Item>> regionalStock) {
            this.regionalStock = regionalStock;
            return this;
        }

        public Builder setItemRolls(int itemRolls) {
            this.itemRolls = itemRolls;
            return this;
        }



        public Shop build() {
            return new Shop(name, globalStock, regionalStock, itemRolls);
        }
    }
}
