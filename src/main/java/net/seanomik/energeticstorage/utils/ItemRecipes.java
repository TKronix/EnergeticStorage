package net.seanomik.energeticstorage.utils;

import net.seanomik.energeticstorage.EnergeticStorage;
import org.bukkit.*;
import org.bukkit.inventory.*;

import static net.seanomik.energeticstorage.utils.ItemConstructor.CreateCustomItem;

public class ItemRecipes {
    public static void registerRecipes() {
        registerCustomRecipes(); //Required for other recipes to work
        registerBlockRecipes();
        registerDriveRecipes();
    }

    private static void registerCustomRecipes()
    {
        try{
            //Silicon Recipe
            FurnaceRecipe silicon = new FurnaceRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "silicon"),
                    CreateCustomItem(Material.CLAY_BALL,ChatColor.GRAY, "Silicon",1, 1000), Material.QUARTZ,
                    1, 100);
            Bukkit.addRecipe(silicon);

            //Basic Processor recipe
            FurnaceRecipe basicprocessor = new FurnaceRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "basic_processor"),
                    CreateCustomItem(Material.FLINT,ChatColor.GRAY, "Basic Processor",1, 1000),
                    new RecipeChoice.ExactChoice(CreateCustomItem(Material.GUNPOWDER,ChatColor.GRAY, "Raw Basic Processor",1, 1000)),
                    1, 100);
            Bukkit.addRecipe(basicprocessor);

            //Improved Processor recipe
            FurnaceRecipe improvedprocessor = new FurnaceRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "improved_processor"),
                    CreateCustomItem(Material.CLOCK,ChatColor.YELLOW, "Improved Processor",1, 1000),
                    new RecipeChoice.ExactChoice(CreateCustomItem(Material.GOLD_NUGGET,ChatColor.YELLOW, "Raw Improved Processor",1, 1000)),
                    1, 100);
            Bukkit.addRecipe(improvedprocessor);

            //Advanced Processor recipe
            FurnaceRecipe advancedprocessor = new FurnaceRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "advanced_processor"),
                    CreateCustomItem(Material.PRISMARINE_SHARD,ChatColor.AQUA, "Advanced Processor",1, 1000),
                    new RecipeChoice.ExactChoice(CreateCustomItem(Material.PRISMARINE_CRYSTALS,ChatColor.AQUA, "Raw Advanced Processor",1, 1000)),
                    1, 100);
            Bukkit.addRecipe(advancedprocessor);

            //Quartz Enriched Iron Recipe
            ShapelessRecipe quartzenrichediron = new ShapelessRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "quartz_enriched_iron"), CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",4, 1000))
                    .addIngredient(3, Material.IRON_INGOT)
                    .addIngredient(1, Material.QUARTZ);
            Bukkit.getServer().addRecipe(quartzenrichediron);

            //Processor Binding Recipe
            ShapelessRecipe processorbinding = new ShapelessRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "processor_binding"), CreateCustomItem(Material.SCUTE,ChatColor.GREEN, "Processor Binding",8, 1000))
                    .addIngredient(2, Material.STRING)
                    .addIngredient(1, Material.SLIME_BALL);
            Bukkit.getServer().addRecipe(processorbinding);

            //Raw Basic Processor Recipe
            ShapelessRecipe rawbasicprocessor = new ShapelessRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "raw_basic_processor"), CreateCustomItem(Material.GUNPOWDER,ChatColor.GRAY, "Raw Basic Processor",1, 1000))
                    .addIngredient(1, Material.CLAY_BALL)
                    .addIngredient(1, Material.REDSTONE)
                    .addIngredient(new RecipeChoice.ExactChoice(CreateCustomItem(Material.SCUTE,ChatColor.GREEN, "Processor Binding",1, 1000)))
                    .addIngredient(new RecipeChoice.ExactChoice(CreateCustomItem(Material.CLAY_BALL, ChatColor.GRAY, "Silicon",1, 1000)));
            Bukkit.getServer().addRecipe(rawbasicprocessor);

            //Raw Improved Processor Recipe
            ShapelessRecipe rawimprovedprocessor = new ShapelessRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "raw_improved_processor"), CreateCustomItem(Material.GOLD_NUGGET,ChatColor.YELLOW, "Raw Improved Processor",1, 1000))
                    .addIngredient(1, Material.GOLD_INGOT)
                    .addIngredient(1, Material.REDSTONE)
                    .addIngredient(new RecipeChoice.ExactChoice(CreateCustomItem(Material.SCUTE,ChatColor.GREEN, "Processor Binding",1, 1000)))
                    .addIngredient(new RecipeChoice.ExactChoice(CreateCustomItem(Material.CLAY_BALL, ChatColor.GRAY, "Silicon",1, 1000)));
            Bukkit.getServer().addRecipe(rawimprovedprocessor);

            //Raw Advanced Processor Recipe
            ShapelessRecipe rawadvancedprocessor = new ShapelessRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "raw_advanced_processor"), CreateCustomItem(Material.PRISMARINE_CRYSTALS,ChatColor.AQUA, "Raw Advanced Processor",1, 1000))
                    .addIngredient(1, Material.DIAMOND)
                    .addIngredient(1, Material.REDSTONE)
                    .addIngredient(new RecipeChoice.ExactChoice(CreateCustomItem(Material.SCUTE,ChatColor.GREEN, "Processor Binding",1, 1000)))
                    .addIngredient(new RecipeChoice.ExactChoice(CreateCustomItem(Material.CLAY_BALL, ChatColor.GRAY, "Silicon", 1,1000)));
            Bukkit.getServer().addRecipe(rawadvancedprocessor);

            //Machine Block
            ShapedRecipe machinecasing = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "machine_casing"), CreateCustomItem(Material.BLACK_GLAZED_TERRACOTTA,ChatColor.GRAY,"Machine Casing",1, 1000))
                    .shape("QQQ",
                            "QSQ",
                            "QQQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('S', Material.STONE);
            Bukkit.getServer().addRecipe(machinecasing);

            //1K Storage Part
            ShapedRecipe onekpart = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "1k_storage_part"), CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"1K Storage Part",1, 1000))
                    .shape("SQS",
                            "GRG",
                            "SGS")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('G', Material.GLASS)
                    .setIngredient('S', new RecipeChoice.ExactChoice(CreateCustomItem(Material.CLAY_BALL,ChatColor.GRAY, "Silicon",1, 1000)));
            Bukkit.getServer().addRecipe(onekpart);

            //4K Storage Part
            ShapedRecipe fourkpart = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "4k_storage_part"), CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"4K Storage Part",1, 1001))
                    .shape("SQS",
                            "GRG",
                            "SGS")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('G', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"1K Storage Part",1, 1000)))
                    .setIngredient('S', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FLINT,ChatColor.GRAY, "Basic Processor",1, 1000)));
            Bukkit.getServer().addRecipe(fourkpart);

            //16K Storage Part
            ShapedRecipe sixteenkpart = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "16k_storage_part"), CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"16K Storage Part",1, 1002))
                    .shape("SQS",
                            "GRG",
                            "SGS")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('G', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"4K Storage Part",1, 1001)))
                    .setIngredient('S', new RecipeChoice.ExactChoice(CreateCustomItem(Material.CLOCK,ChatColor.YELLOW, "Improved Processor",1, 1000)));
            Bukkit.getServer().addRecipe(sixteenkpart);

            //64k Storage Part
            ShapedRecipe sixtyfourkpart = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "64k_storage_part"), CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"64K Storage Part",1, 1003))
                    .shape("SQS",
                            "GRG",
                            "SGS")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('G', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"16K Storage Part",1, 1002)))
                    .setIngredient('S', new RecipeChoice.ExactChoice(CreateCustomItem(Material.PRISMARINE_SHARD,ChatColor.AQUA, "Advanced Processor",1, 1000)));
            Bukkit.getServer().addRecipe(sixtyfourkpart);

            //256k Storage Part
            ShapedRecipe twohundredfiftysixkpart = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "256k_storage_part"), CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"256K Storage Part",1, 1004))
                    .shape("SQS",
                            "GRG",
                            "SGS")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('G', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"64K Storage Part",1, 1003)))
                    .setIngredient('S', new RecipeChoice.ExactChoice(CreateCustomItem(Material.PRISMARINE_SHARD,ChatColor.AQUA, "Advanced Processor",1, 1000)));
            Bukkit.getServer().addRecipe(twohundredfiftysixkpart);

            //1024k Storage Part
            ShapedRecipe onethousandtwentyfourkpart = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "1024k_storage_part"), CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"1024K Storage Part",1, 1005))
                    .shape("SQS",
                            "GRG",
                            "SGS")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('G', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"256K Storage Part",1, 1004)))
                    .setIngredient('S', new RecipeChoice.ExactChoice(CreateCustomItem(Material.PRISMARINE_SHARD,ChatColor.AQUA, "Advanced Processor",1, 1000)));
            Bukkit.getServer().addRecipe(onethousandtwentyfourkpart);

            //4096k Storage Part
            ShapedRecipe fourthousandnintysixkpart = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "4096k_storage_part"), CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"4096K Storage Part",1, 1006))
                    .shape("SQS",
                            "GRG",
                            "SGS")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('G', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"1024K Storage Part",1, 1005)))
                    .setIngredient('S', new RecipeChoice.ExactChoice(CreateCustomItem(Material.PRISMARINE_SHARD,ChatColor.AQUA, "Advanced Processor",1, 1000)));
            Bukkit.getServer().addRecipe(fourthousandnintysixkpart);

            //16384k Storage Part
            ShapedRecipe sixteenthousandthreehundredeightyfourkpart = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "16384k_storage_part"), CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"16384K Storage Part",1, 1007))
                    .shape("SQS",
                            "GRG",
                            "SGS")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('G', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"4096K Storage Part",1, 1006)))
                    .setIngredient('S', new RecipeChoice.ExactChoice(CreateCustomItem(Material.PRISMARINE_SHARD,ChatColor.AQUA, "Advanced Processor",1, 1000)));
            Bukkit.getServer().addRecipe(sixteenthousandthreehundredeightyfourkpart);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static  void registerBlockRecipes() {
        try {
            // Register system block
            ItemStack esSystem = ItemConstructor.createSystemBlock();
            ShapedRecipe systemRecipe = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_system"), esSystem)
                    .shape("QDQ",
                           "SMS",
                           "QSQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('M', new RecipeChoice.ExactChoice(CreateCustomItem(Material.BLACK_GLAZED_TERRACOTTA, ChatColor.GRAY,"Machine Casing",1, 1000)))
                    .setIngredient('S', new RecipeChoice.ExactChoice(CreateCustomItem(Material.CLAY_BALL, ChatColor.GRAY, "Silicon",1, 1000)))
                    .setIngredient('D', Material.DIAMOND);

            Bukkit.getServer().addRecipe(systemRecipe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerDriveRecipes() {
        try { // If the plugin was reloaded, a exception will be thrown.
            // Register Drive 1k
            ItemStack drive1k = ItemConstructor.createDrive(1024L, 0L, 0L);

            ShapedRecipe drv1k = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_1k"), drive1k)
                    .shape("GRG",
                            "RPR",
                            "QQQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('G', Material.GLASS)
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('P', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"1K Storage Part",1, 1000)));
            Bukkit.getServer().addRecipe(drv1k);

            // Register Drive 4k
            ItemStack drive4k = ItemConstructor.createDrive(4096L, 0L, 0L);

            ShapedRecipe drv4k = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_4k"), drive4k)
                    .shape("GRG",
                           "RPR",
                           "QQQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('G', Material.GLASS)
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('P', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"4K Storage Part",1, 1001)));
            Bukkit.getServer().addRecipe(drv4k);

            // Register Drive 16k
            ItemStack drive16k = ItemConstructor.createDrive(16384L, 0L, 0L);

            ShapedRecipe drv16k = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_16k"), drive16k)
                    .shape("GRG",
                            "RPR",
                            "QQQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('G', Material.GLASS)
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('P', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"16K Storage Part",1, 1002)));
            Bukkit.getServer().addRecipe(drv16k);

            // Register Drive 64k
            ItemStack drive64k = ItemConstructor.createDrive(65536L, 0L, 0L);

            ShapedRecipe drv64k = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_64k"), drive64k)
                    .shape("GRG",
                            "RPR",
                            "QQQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('G', Material.GLASS)
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('P', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"64K Storage Part",1, 1003)));
            Bukkit.getServer().addRecipe(drv64k);

            // Register Drive 256k
            ItemStack drive256k = ItemConstructor.createDrive(262144L, 0L, 0L);

            ShapedRecipe drv256k = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_256k"), drive256k)
                    .shape("GRG",
                            "RPR",
                            "QQQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('G', Material.GLASS)
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('P', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"256K Storage Part",1, 1004)));
            Bukkit.getServer().addRecipe(drv256k);

            // Register Drive 1024k
            ItemStack drive1024k = ItemConstructor.createDrive(1048576L, 0L, 0L);

            ShapedRecipe drv1024k = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_1024k"), drive1024k)
                    .shape("GRG",
                            "RPR",
                            "QQQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('G', Material.GLASS)
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('P', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"1024K Storage Part",1, 1005)));
            Bukkit.getServer().addRecipe(drv1024k);

            // Register Drive 4096k
            ItemStack drive4096k = ItemConstructor.createDrive(4194304L, 0L, 0L);

            ShapedRecipe drv4096k = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_4096k"), drive4096k)
                    .shape("GRG",
                            "RPR",
                            "QQQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('G', Material.GLASS)
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('P', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"4096K Storage Part",1, 1006)));
            Bukkit.getServer().addRecipe(drv4096k);

            //Register Drive 16384k
            ItemStack drive16384k = ItemConstructor.createDrive(16777216L, 0L, 0L);

            ShapedRecipe drv16384k = new ShapedRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_16384k"), drive16384k)
                    .shape("GRG",
                            "RPR",
                            "QQQ")
                    .setIngredient('Q', new RecipeChoice.ExactChoice(CreateCustomItem(Material.IRON_INGOT,ChatColor.AQUA, "Quartz Enriched Iron",1, 1000)))
                    .setIngredient('G', Material.GLASS)
                    .setIngredient('R', Material.REDSTONE)
                    .setIngredient('P', new RecipeChoice.ExactChoice(CreateCustomItem(Material.FIREWORK_STAR,ChatColor.AQUA,"16384K Storage Part",1, 1007)));
            Bukkit.getServer().addRecipe(drv16384k);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
