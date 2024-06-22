package cy.jdkdigital.lootbundles;

import com.mojang.logging.LogUtils;
import cy.jdkdigital.lootbundles.init.ModItems;
import cy.jdkdigital.lootbundles.init.ModLootModifiers;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

@Mod(LootBundles.MODID)
public class LootBundles
{
    public static final String MODID = "lootbundles";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LootBundles(IEventBus modEventBus, ModContainer modContainer)
    {
        ModItems.ITEMS.register(modEventBus);
        ModLootModifiers.LOOT_SERIALIZERS.register(modEventBus);
        ModLootModifiers.LOOT_CONDITIONS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, LootBundleConfig.CONFIG);
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MODID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void buildContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
                event.accept(ModItems.FRAGMENT.get());
                event.accept(ModItems.LOOT_BUNDLE.get());
            }
        }
    }
}
