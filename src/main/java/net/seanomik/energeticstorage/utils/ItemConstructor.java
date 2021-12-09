package net.seanomik.energeticstorage.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import net.seanomik.energeticstorage.Skulls;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemConstructor {
    private static final Material DRIVE_MATERIAL = Material.BLUE_DYE;

    public static ItemStack createSystemBlock() {
        ItemStack systemBlock = Skulls.Computer.getItemStack();

        NBTItem systemNBT = new NBTItem(systemBlock);
        systemNBT.setBoolean("ES_SYSTEM", true);
        systemBlock = systemNBT.getItem();

        ItemMeta systemMeta = systemBlock.getItemMeta();
        systemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "ES System");
        systemBlock.setItemMeta(systemMeta);

        return systemBlock;
    }

    public static ItemStack createDrive(Long size, Long filledItems, Long filledTypes) {
        Long smallSize;
        if(size > 2147482624L)
        {
            size = Long.valueOf(2147482624);
            //if (smallSize != 1 && smallSize != 4 && smallSize != 16 && smallSize != 64 && smallSize != 256) {return null;}
        }
        smallSize = size / Long.valueOf(1024);
        ItemStack drive = new ItemStack(DRIVE_MATERIAL, 1);

        // Save the items data in NBT
        NBTItem driveNBT = new NBTItem(drive);
        driveNBT.setBoolean("ES_Drive", true);
        driveNBT.setLong("ES_DriveMaxItemAmount", size);
        driveNBT.setLong("ES_DriveMaxTypeAmount", Reference.MAX_DRIVE_TYPES);
        driveNBT.setString("ES_DriveUUID", UUID.randomUUID().toString());
        drive = driveNBT.getItem();

        ItemMeta driveMeta = drive.getItemMeta();
        driveMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "ES " + smallSize + "k Drive");

        // Get color of items text
        ChatColor itemsColor = ChatColor.GREEN;
        if (filledItems >= size) {
            itemsColor = ChatColor.RED;
        } else if (filledItems >= size * 0.8) {
            itemsColor = ChatColor.YELLOW;
        }

        // Get color of types text
        ChatColor typesColor = ChatColor.GREEN;
        if (filledTypes >= Reference.MAX_DRIVE_TYPES) {
            typesColor = ChatColor.RED;
        } else if (filledTypes >= Reference.MAX_DRIVE_TYPES * 0.8) {
            typesColor = ChatColor.YELLOW;
        }
        // Handles Custom model changes based of how filled. Just like in the modded version of a computer storage system
        driveMeta.setCustomModelData(1001);
        if(filledItems >= size * 0.8 || filledTypes >= Reference.MAX_DRIVE_TYPES * 0.8) {
            driveMeta.setCustomModelData(1002);
        }else if(filledItems >= size || filledTypes >= Reference.MAX_DRIVE_TYPES) {
            driveMeta.setCustomModelData(1003);
        }

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLUE + "Filled Items: " + itemsColor + filledItems + ChatColor.BLUE + "/" + ChatColor.GREEN + size);
        lore.add(ChatColor.BLUE + "Filled Types: " + typesColor + filledTypes + ChatColor.BLUE + "/" + ChatColor.GREEN + Reference.MAX_DRIVE_TYPES);
        driveMeta.setLore(lore);

        drive.setItemMeta(driveMeta);

        return drive;
    }
    public static ItemStack CreateCustomItem(Material Item, ChatColor TextColor, String Name,int Quantity, int CustomModelData)
    {
        ItemStack CustomItem = new ItemStack(Item, Quantity);
        ItemMeta CustomitemMeta = CustomItem.getItemMeta();
        CustomitemMeta.setDisplayName(TextColor+Name);
        CustomitemMeta.setCustomModelData(CustomModelData);
        CustomItem.setItemMeta(CustomitemMeta);
        return CustomItem;
    }
}
