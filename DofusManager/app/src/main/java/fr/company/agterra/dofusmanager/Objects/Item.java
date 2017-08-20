package fr.company.agterra.dofusmanager.Objects;

import java.util.ArrayList;

import fr.company.agterra.dofusmanager.ItemType;

/**
 * Created by Agterra on 20/08/2017.
 */

public class Item {

    private ItemType type;

    private String level;

    private ArrayList <Effect> effects;

    private String description;

    public Item() {

        this.type = ItemType.UNKNOWN_ITEM_TYPE;

        this.level = String.valueOf(0);

        this.effects = new ArrayList<>();

        this.description = "Unknown description";

    }

    public Item(ItemType type, String level, ArrayList<Effect> effects, String description) {

        this.type = type;

        this.level = level;

        this.effects = effects;

        this.description = description;

    }

    public ItemType getType() {

        return type;

    }

    public void setType(ItemType type) {

        this.type = type;

    }

    public String getLevel() {

        return level;

    }

    public void setLevel(String level) {

        this.level = level;

    }

    public ArrayList<Effect> getEffects() {

        return effects;

    }

    public void setEffects(ArrayList<Effect> effects) {

        this.effects = effects;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    @Override
    public String toString() {

        return "Item{" +
                "type=" + type +
                ", level='" + level + '\'' +
                ", effects=" + effects +
                ", description='" + description + '\'' +
                '}';

    }

}
