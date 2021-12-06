package net.seanomik.energeticstorage.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.AbstractMap;
import java.util.Map;

public class ItemSerialization {
    public static String serializeItem(ItemStack item, Long amount) {
        YamlConfiguration yaml = new YamlConfiguration();

        yaml.set("amount", amount);
        yaml.set("item", item);

        return yaml.saveToString();
    }

    public static Map.Entry<ItemStack, Long> deserializeItem(String item) throws InvalidConfigurationException {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.loadFromString(item);

        ItemStack itemStack = yaml.getItemStack("item");
        Long amount = yaml.getLong("amount");

        Map.Entry<ItemStack, Long> itemEntry = new AbstractMap.SimpleEntry<>(itemStack, amount);

        return itemEntry;
    }
}
