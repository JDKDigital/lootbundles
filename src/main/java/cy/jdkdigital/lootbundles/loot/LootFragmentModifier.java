package cy.jdkdigital.lootbundles.loot;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cy.jdkdigital.lootbundles.LootBundles;
import cy.jdkdigital.lootbundles.init.ModItems;
import cy.jdkdigital.lootbundles.init.ModLootModifiers;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class LootFragmentModifier extends LootModifier
{
    public static final Supplier<Codec<LootFragmentModifier>> CODEC = () ->
        RecordCodecBuilder.create(inst ->
                inst.group(
                        LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(lm -> lm.conditions),
                        Codec.FLOAT.fieldOf("probability").orElse(0f).forGetter((configuration) -> configuration.probability),
                        Codec.INT.fieldOf("maxCount").orElse(1).forGetter((configuration) -> configuration.maxCount)
                )
                .apply(inst, LootFragmentModifier::new));

    private final float probability;
    private final Integer maxCount;

    protected LootFragmentModifier(LootItemCondition[] conditionsIn, float probability, int maxCount) {
        super(conditionsIn);
        this.probability = probability;
        this.maxCount = maxCount;
    }

    @Nonnull
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < probability) {
            generatedLoot.add(new ItemStack(ModItems.FRAGMENT.get(), context.getRandom().nextInt(1, maxCount + 1)));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
