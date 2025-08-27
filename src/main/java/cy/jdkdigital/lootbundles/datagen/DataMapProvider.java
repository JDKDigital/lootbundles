package cy.jdkdigital.lootbundles.datagen;

import cy.jdkdigital.lootbundles.LootBundles;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class DataMapProvider extends net.neoforged.neoforge.common.data.DataMapProvider
{
    protected DataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(LootBundles.LOOT_WEIGHT_DATA).add(Items.STICK.builtInRegistryHolder(), new LootBundles.LootWeightData(1), false);
    }
}
