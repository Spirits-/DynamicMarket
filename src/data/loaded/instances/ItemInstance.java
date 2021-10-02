package data.loaded.instances;

import data.loaded.Item;

import java.util.Objects;

public class ItemInstance {

    private final Item type;
    private int amount;

    public ItemInstance(Item type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public Item getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return type.name() + ": " + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemInstance that = (ItemInstance) o;
        return Objects.equals(type, that.type); //amount shouldn't matter for two Item instances to be the same.
    }

    public void increaseAmount(int i) {
        amount += i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, amount);
    }
}
