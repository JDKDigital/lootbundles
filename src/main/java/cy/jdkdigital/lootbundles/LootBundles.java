package cy.jdkdigital.lootbundles;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cy.jdkdigital.lootbundles.init.ModItems;
import cy.jdkdigital.lootbundles.init.ModLootModifiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import org.slf4j.Logger;

@Mod(LootBundles.MODID)
public class LootBundles
{
    public static final String MODID = "lootbundles";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DataMapType<Item, LootWeightData> LOOT_WEIGHT_DATA = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(MODID, "loot_weights"),
            Registries.ITEM,
            LootWeightData.CODEC
    ).build();

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

        @SubscribeEvent
        public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
            event.register(LOOT_WEIGHT_DATA);
        }
    }

    public record LootWeightData(int weight) {
        public static final Codec<LootWeightData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("weight").forGetter(LootWeightData::weight)
        ).apply(instance, LootWeightData::new));
    }
}
