package net.seanomik.energeticstorage.objects;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.seanomik.energeticstorage.utils.ItemConstructor;
import net.seanomik.energeticstorage.utils.ItemSerialization;
import net.seanomik.energeticstorage.utils.Reference;
import net.seanomik.energeticstorage.utils.Utils;
import org.apache.commons.text.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class ESDrive implements Cloneable, ConfigurationSerializable {
    private UUID uuid;
    private Long size;
    private Map<ItemStack, Long> items = new HashMap<>(); // Item, amount

    public ESDrive(Long size) {
        this.size = size;
    }

    protected ESDrive(UUID uuid, Long size, Map<ItemStack, Long> items) {
        this.uuid = uuid;
        this.size = size;
        this.items = items;
    }

    public ESDrive(Long size, Map<ItemStack, Long> items) {
        this(size);
        uuid = UUID.randomUUID();

        this.items = items;
    }

    public ESDrive(ItemStack driveItem) {
        NBTItem driveNBT = new NBTItem(driveItem);

        if (driveNBT.hasKey("ES_DriveItems")) {
            try {
                JSONParser jsonParser = new JSONParser();
                JSONArray itemJsonArray = (JSONArray) jsonParser.parse(driveNBT.getString("ES_DriveItems"));

                for (int i = 0; i < itemJsonArray.size(); i++) {
                    JSONObject itemObject = (JSONObject) itemJsonArray.get(i);

                    Map.Entry<ItemStack, Long> item = ItemSerialization.deserializeItem((String) itemObject.get("itemYAML"));

                    items.put(item.getKey(), item.getValue());
                }
            } catch (ParseException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

        size = driveNBT.getLong("ES_DriveMaxItemAmount");
        uuid = (driveNBT.hasKey("ES_DriveUUID")) ? UUID.fromString(driveNBT.getString("ES_DriveUUID")) : UUID.randomUUID();
    }

    public UUID getUUID() {
        return uuid;
    }

    public Long getSize() {
        return size;
    }

    public Long getFilledSpace() {
        Long filled = Long.valueOf(0);

        for (Long amount : items.values()) {
            filled += amount;
        }

        return filled;
    }

    public Long getFilledTypes() {
        List<Material> foundItems = new ArrayList<>();

        for (ItemStack item : items.keySet()) {
            if (!foundItems.contains(item.getType())) {
                foundItems.add(item.getType());
            }
        }

        return Long.valueOf(foundItems.size());
    }

    public Map<ItemStack, Long> getItems() { return items; }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setItems(Map<ItemStack, Long> items) { this.items = items; }

    public ESDrive clone() {
        try {
            ESDrive drive = (ESDrive) super.clone();
            if (this.items != null) {
                items = new HashMap<>(items);
            }

            return drive;
        } catch (CloneNotSupportedException var2) {
            throw new Error(var2);
        }
    }

    public boolean canAddItem(ItemStack item) {
        if (Utils.isItemValid(item)) {
            // If the item is valid, we're full on types, we have the item in the drive, and we're not full on space, return true.
            // else always just cascase down and check if we have space.
            if (Utils.containsSimilarItem(new ArrayList<>(items.keySet()), item, true)) {
                return getFilledSpace() < size;
            } else {
                if (getFilledTypes() < Reference.MAX_DRIVE_TYPES) {
                    return getFilledSpace() < size;
                }

                return false;
            }
        } else {
            return getFilledSpace() < size;
        }
    }

    public boolean addItem(ItemStack item) {
        item = item.clone();

        if (canAddItem(item)) {
            // The item is contained, then update the amount.
            if (Utils.containsSimilarItem(new ArrayList<>(items.keySet()), item, true)) {
                Long amount = (Long) items.values().toArray()[Utils.indexOfSimilarItem(new ArrayList<>(items.keySet()), item)] + item.getAmount();
                items = Utils.removeSimilarItem(items, item);
                items.put(item, amount);
            } else {
                items.put(item, (long) item.getAmount());
            }

            return true;
        }

        return false;
    }
    
    public ItemStack removeItem(ItemStack item) {
        // If there isn't enough items stored to take out the requested amount, then just take out all that we can.
        Long foundItemAmount = (Long) items.values().toArray()[Utils.indexOfSimilarItem(new ArrayList<>(items.keySet()), item)];
        if (foundItemAmount - item.getAmount() < 1) {
            items = Utils.removeSimilarItem(items, item);
            item.setAmount(Math.toIntExact(foundItemAmount));
        } else {
            Long newAmount = foundItemAmount - item.getAmount();

            items = Utils.removeSimilarItem(items, item);
            items.put(item, newAmount);
        }

        return item;
    }

    private String exceptionMessage(Exception e) {
        return "An exception occurred in ESDrive (UUID:" + uuid + ", Exception: " + e.getMessage() + ")";
    }

    public ItemStack getDriveItem() {
        try {
            ItemStack drive = ItemConstructor.createDrive(size, getFilledSpace(), getFilledTypes());
            NBTItem driveNBT = new NBTItem(drive);

            JSONArray itemsJson = new JSONArray();
            for (Map.Entry<ItemStack, Long> entry : items.entrySet()) {
                try {
                    String object = "{\"itemYAML\":\"" + StringEscapeUtils.escapeJson(ItemSerialization.serializeItem(entry.getKey(), entry.getValue())) + "\"}";
                    JSONObject itemJSON = (JSONObject) new JSONParser().parse(object);

                    itemsJson.add(itemJSON);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            driveNBT.setString("ES_DriveItems", itemsJson.toJSONString());
            drive = driveNBT.getItem();

            return drive;
        } catch (Exception e) {
            System.out.println(exceptionMessage(e));
        }

        return null;
    }

    // @TODO: Implement (has not been tested)
    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap();
        result.put("uuid", uuid);
        result.put("size", size);

        if (!items.isEmpty()) {
            List<Object> itemsSerialized = new ArrayList<>();
            for (Map.Entry<ItemStack, Long> entry : items.entrySet()) {
                Map<String, Object> itemSerialized = new LinkedHashMap<>();
                itemSerialized.put("amount", entry.getValue());
                itemSerialized.put("item", entry.getKey().serialize());
                itemsSerialized.add(itemSerialized);
            }
            result.put("items", itemsSerialized);
        }

        return result;
    }

    // @TODO: Implement (has not been tested)
    @NotNull
    public static ESDrive deserialize(@NotNull Map<String, Object> args) {
        UUID uuid = (UUID) args.get("uuid");
        Long size = ((Number)args.get("size")).longValue();
        Map<ItemStack, Long> items = new HashMap<>();

        if (args.containsKey("items")) {
            Object raw = args.get("items");
            if (raw instanceof Map) {
                Map<?, ?> map = (Map)raw;

                items.put(ItemStack.deserialize((Map<String, Object>) map.get("item")), ((Number)map.get("amount")).longValue());
            }
        }

        return new ESDrive(uuid, size, items);
    }
}
