package cy.jdkdigital.lootbundles;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class LootBundleConfig
{
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_CONFIG;
    public static final Client CLIENT = new Client(CLIENT_BUILDER);
    public static final ForgeConfigSpec SERVER_CONFIG;
    public static final General SERVER = new General(SERVER_BUILDER);

    static {
        CLIENT_CONFIG = CLIENT_BUILDER.build();
        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    public static class Client
    {
        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("Client");

            builder.pop();
        }
    }

    public static class General
    {
        public final ForgeConfigSpec.BooleanValue whitelist;
        public final ForgeConfigSpec.IntValue maxStackSize;
        public final ForgeConfigSpec.IntValue minLootAmount;
        public final ForgeConfigSpec.IntValue maxLootAmount;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> disallowedItemNames;

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("General");

            whitelist = builder
                    .comment("Use whitelist only")
                    .define("whitelist", false);

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

            builder.pop();
        }
    }
}