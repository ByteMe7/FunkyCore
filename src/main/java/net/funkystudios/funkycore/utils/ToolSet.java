package net.funkystudios.funkycore.utils;

import com.google.common.collect.Maps;
import net.funkystudios.funkycore.item.HammerItem;
import net.minecraft.item.*;

import java.util.HashMap;

public class ToolSet {
    final HashMap<SetItem, Item> variants = Maps.<ToolSet.SetItem, Item>newHashMap();

    ToolSet() {

    }

    public HashMap<SetItem, Item> getVariants() {
        return variants;
    }

    public SwordItem getSword() {
        return (SwordItem) this.variants.get(SetItem.SWORD);
    }

    public PickaxeItem getPickaxe() {
        return (PickaxeItem) this.variants.get(SetItem.PICKAXE);
    }
    public AxeItem getAxe() {
        return (AxeItem) this.variants.get(SetItem.AXE);
    }

    public ShovelItem getShovel() {
        return (ShovelItem) this.variants.get(SetItem.SHOVEL);
    }

    public HoeItem getHoe() {
        return (HoeItem) this.variants.get(SetItem.HOE);
    }

    public HammerItem getHammer(){
        return (HammerItem) this.variants.get(SetItem.HAMMER);
    }

    public ArmorItem getHelmet(){
        return (ArmorItem) this.variants.get(SetItem.HELMET);
    }
    public ArmorItem getChestplate(){
        return (ArmorItem) this.variants.get(SetItem.CHESTPLATE);
    }
    public ArmorItem getLeggings(){
        return (ArmorItem) this.variants.get(SetItem.LEGGINGS);
    }
    public ArmorItem getBoots(){
        return (ArmorItem) this.variants.get(SetItem.BOOTS);
    }

    public static class Builder {
        private final ToolSet toolSet;

        public Builder(){
            this.toolSet = new ToolSet();
        }

        public ToolSet build(){
            return this.toolSet;
        }

        public ToolSet.Builder sword(SwordItem sword){
            this.toolSet.variants.put(SetItem.SWORD, sword);
            return this;
        }
        public ToolSet.Builder pickaxe(PickaxeItem pickaxe){
            this.toolSet.variants.put(SetItem.PICKAXE, pickaxe);
            return this;
        }
        public ToolSet.Builder axe(AxeItem axe){
            this.toolSet.variants.put(SetItem.AXE, axe);
            return this;
        }
        public ToolSet.Builder shovel(ShovelItem shovel){
            this.toolSet.variants.put(SetItem.SHOVEL, shovel);
            return this;
        }

        public ToolSet.Builder hoe(HoeItem hoe){
            this.toolSet.variants.put(SetItem.HOE, hoe);
            return this;
        }

        public ToolSet.Builder hammer(HammerItem hammer){
            this.toolSet.variants.put(SetItem.HAMMER, hammer);
            return this;
        }

        public ToolSet.Builder helmet(ArmorItem helmet) {
            this.toolSet.variants.put(SetItem.HELMET, helmet);
            return this;
        }
        public ToolSet.Builder chestplate(ArmorItem chestplate) {
            this.toolSet.variants.put(SetItem.CHESTPLATE, chestplate);
            return this;
        }
        public ToolSet.Builder leggings(ArmorItem leggings) {
            this.toolSet.variants.put(SetItem.LEGGINGS, leggings);
            return this;
        }
        public ToolSet.Builder boots(ArmorItem boots) {
            this.toolSet.variants.put(SetItem.BOOTS, boots);
            return this;
        }
    }


    public static enum SetItem {
        HELMET("helmet"),
        CHESTPLATE("chestplate"),
        LEGGINGS("leggings"),
        BOOTS("boots"),
        SWORD("sword"),
        PICKAXE("pickaxe"),
        AXE("axe"),
        SHOVEL("shovel"),
        HOE("hoe"),
        HAMMER("hammer");

        private final String name;

        private SetItem(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

