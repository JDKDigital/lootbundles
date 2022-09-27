package cy.jdkdigital.lootbundles.init;

import cy.jdkdigital.lootbundles.LootBundles;
import cy.jdkdigital.lootbundles.item.LootBundle;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LootBundles.MODID);

    public static final RegistryObject<Item> FRAGMENT = ITEMS.register("fragment", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> LOOT_BUNDLE = ITEMS.register("loot_bundle", () -> new LootBundle(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
