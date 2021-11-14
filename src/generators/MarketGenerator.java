package generators;

import data.loaded.Item;
import data.loaded.Region;
import data.loaded.Shop;
import data.loaded.instances.ItemInstance;
import data.loaded.instances.ShopInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarketGenerator {

    private final Random random;
    private static final int MARKET_NUM = 4;

    public MarketGenerator() {
        random = new Random();
    }

    public List<ShopInstance> generateMarket(List<Region> regions) {
        List<ShopInstance> result = new ArrayList<>();
        List<Shop> shopsList = new ArrayList<>(Shop.getGlobalShops());
        for (int i = 0; i < MARKET_NUM; i++) {
            int shopChoice = getRandomIntInBounds(0, shopsList.size() - 1);
            Shop shop = shopsList.get(shopChoice);
            if (shop.name().equalsIgnoreCase("none")) {
                shopsList.remove(shop);
                continue;
            }
            result.add(generateStall(regions, shop));
            shopsList.remove(shop);
        }
        return result;
    }

    private ShopInstance generateStall(List<Region> regions, Shop shop) {
        List<Item> possibleProduce = getPossibleProduce(regions, shop);
        List<ItemInstance> actualStock = new ArrayList<>();
        for (int i = 0; i < shop.itemRolls(); i++) {
            ItemInstance it = generateItemInstance(possibleProduce);
            if (actualStock.contains(it)) {
                int itemIndex = getIndexOfItem(it, actualStock);
                actualStock.get(itemIndex).increaseAmount(it.getAmount());
            } else {
                actualStock.add(it);
            }
        }
        return new ShopInstance(shop, actualStock);
    }

    private int getIndexOfItem(ItemInstance it, List<ItemInstance> actualStock) {
        for (int i = 0, actualStockSize = actualStock.size(); i < actualStockSize; i++) {
            ItemInstance instance = actualStock.get(i);
            if (instance.equals(it)) {
                return i;
            }
        }
        return -1; //Shouldn't happen.
    }

    private ItemInstance generateItemInstance(List<Item> possibleProduce) {
        int itemChoice = getRandomIntInBounds(0, possibleProduce.size() - 1);
        Item it = possibleProduce.get(itemChoice);
        String type = isItemSpecial(it.name());
        if (type != null) {
            return generateSpecialItem(type);
        }
        if (!it.multipleRolls()) {
            possibleProduce.remove(it);
        }
        int amount = getRandomIntInBounds(it.minRollBound(), it.maxRollBound());
        return new ItemInstance(it, amount);
    }

    private ItemInstance generateSpecialItem(String type) {
        return switch (type) {
            case Item.STRANGE_ITEM_CODE -> generateItemInstance(Item.getStranger());
            case Item.GEMSTONE_CARVING_CODE -> generateItemInstance(Item.getNecklace());
            case Item.WONDERS_ITEM_CODE -> generateItemInstance(Item.getWonders());
            default -> null;
        };
    }

    private String isItemSpecial(String name) {
        return switch (name) {
            case Item.STRANGE_ITEM_CODE -> Item.STRANGE_ITEM_CODE;
            case Item.GEMSTONE_CARVING_CODE -> Item.GEMSTONE_CARVING_CODE;
            case Item.WONDERS_ITEM_CODE -> Item.WONDERS_ITEM_CODE;
            default -> null;
        };
    }

    private List<Item> getPossibleProduce(List<Region> regions, Shop shop) {
        List<Item> regionalStock = new ArrayList<>();
        for (Region r : regions) {
            regionalStock.addAll(shop.regionalStock().get(r));
        }
        if (regionalStock.isEmpty()) {
            return shop.globalStock();
        }
        ArrayList<Item> result = new ArrayList<>(shop.globalStock());
        result.addAll(regionalStock);
        return result;
    }

    //[min...max]
    private int getRandomIntInBounds(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min cannot be over max!");
        }
        if (min == max) {
            return min;
        }
        return random.nextInt((max - min) + 1) + min;
    }

}
