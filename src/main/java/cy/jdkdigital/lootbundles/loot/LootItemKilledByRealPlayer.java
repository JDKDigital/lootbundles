package cy.jdkdigital.lootbundles.loot;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import cy.jdkdigital.lootbundles.LootBundleConfig;
import cy.jdkdigital.lootbundles.init.ModLootModifiers;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Set;

public class LootItemKilledByRealPlayer implements LootItemCondition
{
    static final LootItemKilledByRealPlayer INSTANCE = new LootItemKilledByRealPlayer();

    private LootItemKilledByRealPlayer() {
    }

    public LootItemConditionType getType() {
        return ModLootModifiers.KILLED_BY_REAL_PLAYER.get();
    }

    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.LAST_DAMAGE_PLAYER);
    }

    public boolean test(LootContext context) {
        return context.hasParam(LootContextParams.LAST_DAMAGE_PLAYER) &&
                (
                    LootBundleConfig.COMMON.allowFakePlayerDrops.get() ||
                    !(context.getParam(LootContextParams.LAST_DAMAGE_PLAYER) instanceof FakePlayer)
                );
    }

    public static LootItemCondition.Builder killedByPlayer() {
        return () -> {
            return INSTANCE;
        };
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemKilledByRealPlayer> {
        public void serialize(JsonObject json, LootItemKilledByRealPlayer condition, JsonSerializationContext context) {
        }

        public LootItemKilledByRealPlayer deserialize(JsonObject json, JsonDeserializationContext context) {
            return LootItemKilledByRealPlayer.INSTANCE;
        }
    }
}