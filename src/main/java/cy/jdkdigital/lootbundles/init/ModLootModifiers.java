package cy.jdkdigital.lootbundles.init;

import com.mojang.serialization.MapCodec;
import cy.jdkdigital.lootbundles.LootBundles;
import cy.jdkdigital.lootbundles.loot.LootBundleModifier;
import cy.jdkdigital.lootbundles.loot.LootFragmentModifier;
import cy.jdkdigital.lootbundles.loot.LootItemKilledByRealPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModLootModifiers
{
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, LootBundles.MODID);
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, LootBundles.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<LootFragmentModifier>> LOOT_FRAGMENT = LOOT_SERIALIZERS.register("loot_fragment", LootFragmentModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<LootBundleModifier>> LOOT_BUNDLE = LOOT_SERIALIZERS.register("loot_bundle", LootBundleModifier.CODEC);

    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> KILLED_BY_REAL_PLAYER = LOOT_CONDITIONS.register("killed_by_real_player", () -> new LootItemConditionType(LootItemKilledByRealPlayer.CODEC));
}
