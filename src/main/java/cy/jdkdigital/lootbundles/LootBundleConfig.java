package cy.jdkdigital.lootbundles;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class LootBundleConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG;
    public static final General COMMON = new General(BUILDER);

    static {
        CONFIG = BUILDER.build();
    }

    public static class General
    {
        public final ForgeConfigSpec.BooleanValue whitelist;
        public final ForgeConfigSpec.BooleanValue inventoryInsert;
        public final ForgeConfigSpec.IntValue maxStackSize;
        public final ForgeConfigSpec.IntValue minLootAmount;
        public final ForgeConfigSpec.IntValue maxLootAmount;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> disallowedItemNames;
        public final ForgeConfigSpec.BooleanValue allowFakePlayerDrops;

        public General(ForgeConfigSpec.Builder builder) {
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
                    .define("whitelist", false);

            builder.pop();
        }
    }
}