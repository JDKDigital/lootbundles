package cy.jdkdigital.lootbundles;

import com.mojang.logging.LogUtils;
import cy.jdkdigital.lootbundles.init.ModItems;
import cy.jdkdigital.lootbundles.init.ModLootModifiers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LootBundles.MODID)
public class LootBundles
{
    public static final String MODID = "lootbundles";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LootBundles()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::tabs);

        ModItems.ITEMS.register(modEventBus);
        ModLootModifiers.LOOT_SERIALIZERS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, LootBundleConfig.CONFIG);
    }

    private void tabs(final CreativeModeTabEvent.BuildContents event)
    {
        if (event.getTab().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(ModItems.FRAGMENT.get());
            event.accept(ModItems.LOOT_BUNDLE.get());
        }
    }
}
