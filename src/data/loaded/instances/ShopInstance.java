package data.loaded.instances;

import data.loaded.Shop;

import java.util.List;

public class ShopInstance {

    private Shop type;
    private List<ItemInstance> stock;

    public ShopInstance(Shop type, List<ItemInstance> stock) {
        this.type = type;
        this.stock = stock;
    }

    public Shop getType() {
        return type;
    }

    public void setType(Shop type) {
        this.type = type;
    }

    public List<ItemInstance> getStock() {
        return stock;
    }

    public void setStock(List<ItemInstance> stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        String starter = type.name() + '\n';
        StringBuilder sb = new StringBuilder(starter);
        for (ItemInstance it : stock) {
            sb.append("\t");
            sb.append(it.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
