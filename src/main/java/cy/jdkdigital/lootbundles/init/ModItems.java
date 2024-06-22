package cy.jdkdigital.lootbundles.init;

import cy.jdkdigital.lootbundles.LootBundles;
import cy.jdkdigital.lootbundles.item.LootBundle;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, LootBundles.MODID);

    public static final DeferredHolder<Item, Item> FRAGMENT = ITEMS.register("fragment", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> LOOT_BUNDLE = ITEMS.register("loot_bundle", () -> new LootBundle(new Item.Properties()));
}
