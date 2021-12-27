package data.loaded;

import java.util.ArrayList;
import java.util.List;

public record Item(String name, boolean multipleRolls, int minRollBound, int maxRollBound) {

    private static final List<Item> master = new ArrayList<>();
    private static final List<Item> stranger = new ArrayList<>();
    private static final List<Item> wonders = new ArrayList<>();
    private static final List<Item> necklace = new ArrayList<>();
    private static final List<Item> featherCloaks = new ArrayList<>();
    private static final List<Item> tickerParts = new ArrayList<>();
    private static final List<Item> rope = new ArrayList<>();

    public static final String STRANGE_ITEM_CODE = "StrangeRoll";
    public static final String GEMSTONE_CARVING_CODE = "gemstone carving";
    public static final String WONDERS_ITEM_CODE = "WondrousRoll";
    public static final String ROPE_CODE = "RopeRoll";
    public static final String FEATHER_CLOAKS_CODE = "FeatherCloakRoll";
    public static final String TICKER_PARTS_CODE = "TickerPartsRoll";


    public static List<Item> getMaster() {
        return master;
    }

    public static List<Item> getStranger() {
        return stranger;
    }

    public static List<Item> getWonders() {
        return wonders;
    }

    public static List<Item> getNecklace() {
        return necklace;
    }

    public static List<Item> getFeatherCloaks() {
        return featherCloaks;
    }

    public static List<Item> getTickerParts() {
        return tickerParts;
    }

    public static List<Item> getRope() {
        return rope;
    }

    public static Item getFromMasterByName(String name) {
        for (Item it : master) {
            if (it.name.equals(name)) {
                return it;
            }
        }
        throw new IllegalArgumentException("Item with name \"" + name + "\" not found!");
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", multipleRolls=" + multipleRolls +
                ", minRollBound=" + minRollBound +
                ", maxRollBound=" + maxRollBound +
                '}';
    }

    public static class Builder {

        private String name = "PLACEHOLDER";
        private boolean multipleRolls = false;
        private int minRolls = Integer.MAX_VALUE;
        private int maxRolls = Integer.MIN_VALUE;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setMultipleRolls(boolean multipleRolls) {
            this.multipleRolls = multipleRolls;
            return this;
        }

        public Builder setMinRolls(int minRolls) {
            this.minRolls = minRolls;
            return this;
        }

        public Builder setMaxRolls(int maxRolls) {
            this.maxRolls = maxRolls;
            return this;
        }

        public int getMinRolls() {
            return minRolls;
        }

        public int getMaxRolls() {
            return maxRolls;
        }

        public Item build() {
            return new Item(name, multipleRolls, minRolls, maxRolls);
        }
    }
}
