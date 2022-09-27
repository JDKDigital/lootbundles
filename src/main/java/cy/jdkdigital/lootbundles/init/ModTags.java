package cy.jdkdigital.lootbundles.init;

import cy.jdkdigital.lootbundles.LootBundles;
import cy.jdkdigital.lootbundles.item.LootBundle;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;
import java.util.Map;

public class ModTags
{
    public static Map<ResourceLocation, TagKey<Block>> blockTagCache = new HashMap<>();
    public static Map<ResourceLocation, TagKey<Item>> itemTagCache = new HashMap<>();
    public static Map<ResourceLocation, TagKey<Fluid>> fluidTagCache = new HashMap<>();
    public static Map<ResourceLocation, TagKey<EntityType<?>>> entityTagCache = new HashMap<>();

    public static final TagKey<Item> WHITELIST = getItemTag(new ResourceLocation(LootBundles.MODID, "whitelist"));
    public static final TagKey<Item> BLACKLIST = getItemTag(new ResourceLocation(LootBundles.MODID, "blacklist"));

    public static TagKey<Block> getBlockTag(ResourceLocation resourceLocation) {
        if (!blockTagCache.containsKey(resourceLocation)) {
            blockTagCache.put(resourceLocation, BlockTags.create(resourceLocation));
        }
        return blockTagCache.get(resourceLocation);
    }

    public static TagKey<Item> getItemTag(ResourceLocation resourceLocation) {
        if (!itemTagCache.containsKey(resourceLocation)) {
            itemTagCache.put(resourceLocation, ItemTags.create(resourceLocation));
        }
        return itemTagCache.get(resourceLocation);
    }

    public static TagKey<Fluid> getFluidTag(ResourceLocation resourceLocation) {
        if (!fluidTagCache.containsKey(resourceLocation)) {
            fluidTagCache.put(resourceLocation, FluidTags.create(resourceLocation));
        }
        return fluidTagCache.get(resourceLocation);
    }

    public static TagKey<EntityType<?>> getEntityTag(ResourceLocation name) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, name);
    }

    public static TagKey<Biome> getBiomeTag(ResourceLocation name) {
        return TagKey.create(Registry.BIOME_REGISTRY, name);
    }
}
