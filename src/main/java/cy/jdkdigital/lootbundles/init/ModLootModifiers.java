package cy.jdkdigital.lootbundles.init;

import com.mojang.serialization.Codec;
import cy.jdkdigital.lootbundles.LootBundles;
import cy.jdkdigital.lootbundles.loot.LootBundleModifier;
import cy.jdkdigital.lootbundles.loot.LootFragmentModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModLootModifiers
{
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, LootBundles.MODID);

    public static final RegistryObject<Codec<LootFragmentModifier>> LOOT_FRAGMENT = LOOT_SERIALIZERS.register("loot_fragment", LootFragmentModifier.CODEC);
    public static final RegistryObject<Codec<LootBundleModifier>> LOOT_BUNDLE = LOOT_SERIALIZERS.register("loot_bundle", LootBundleModifier.CODEC);
}
