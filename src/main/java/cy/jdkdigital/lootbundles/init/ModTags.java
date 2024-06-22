package cy.jdkdigital.lootbundles.init;

import cy.jdkdigital.lootbundles.LootBundles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ModTags
{
    public static Map<ResourceLocation, TagKey<Item>> itemTagCache = new HashMap<>();

    public static final TagKey<Item> WHITELIST = getItemTag(ResourceLocation.fromNamespaceAndPath(LootBundles.MODID, "whitelist"));
    public static final TagKey<Item> BLACKLIST = getItemTag(ResourceLocation.fromNamespaceAndPath(LootBundles.MODID, "blacklist"));

    public static TagKey<Item> getItemTag(ResourceLocation resourceLocation) {
        if (!itemTagCache.containsKey(resourceLocation)) {
            itemTagCache.put(resourceLocation, ItemTags.create(resourceLocation));
        }
        return itemTagCache.get(resourceLocation);
    }
}
