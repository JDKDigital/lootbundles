package cy.jdkdigital.lootbundles;

import com.google.common.collect.Lists;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class LootBundleConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CONFIG;
    public static final General COMMON = new General(BUILDER);

    static {
        CONFIG = BUILDER.build();
    }

    public static class General
    {
        public final ModConfigSpec.BooleanValue whitelist;
        public final ModConfigSpec.BooleanValue inventoryInsert;
        public final ModConfigSpec.IntValue maxStackSize;
        public final ModConfigSpec.IntValue minLootAmount;
        public final ModConfigSpec.IntValue maxLootAmount;
        public final ModConfigSpec.ConfigValue<List<? extends String>> disallowedItemNames;
        public final ModConfigSpec.BooleanValue allowFakePlayerDrops;

        public General(ModConfigSpec.Builder builder) {
            builder.push("General");

            whitelist = builder
                    .comment("Use whitelist only")
                    .define("whitelist", false);

            inventoryInsert = builder
                    .comment("Insert into players inventory when possible instead of spewing all the items on the ground")
                    .define("inventoryInsert", false);

            maxStackSize = builder
                    .comment("Stack size of dropped loot will be a random number between 0 and maxStackSize")
                    .defineInRange("maxStackSize", 4, 0, 64);

            minLootAmount = builder
                    .comment("Number of dropped stacks per bundle will be between minLootAmount and maxLootAmount")
                    .defineInRange("minLootAmount", 1, 0, Integer.MAX_VALUE);

            maxLootAmount = builder
                    .comment("Number of dropped stacks per bundle will be between minLootAmount and maxLootAmount")
                    .defineInRange("maxLootAmount", 4, 0, Integer.MAX_VALUE);

            disallowedItemNames = builder
                    .comment("List of strings that an item name must not contain in order for it to be considered a valid loot item. Item name can be partial and can contain the mod id as well to disallow an entire mods items.")
                    .define("disallowedItemNames", Lists.newArrayList("creative", "spawn_egg"));

            allowFakePlayerDrops = builder
                    .comment("Allow drops from fake players")
                    .define("allowFakePlayerDrops", false);

            builder.pop();
        }
    }
}