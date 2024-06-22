package cy.jdkdigital.lootbundles.item;

import cy.jdkdigital.lootbundles.LootBundleConfig;
import cy.jdkdigital.lootbundles.LootBundles;
import cy.jdkdigital.lootbundles.init.ModTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class LootBundle extends Item
{
    private static final List<Item> possibleItems = new ArrayList<>();

    public LootBundle(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            ItemStack itemStack = player.getItemInHand(hand);

            int usedBundles = 1;
            if (player.isShiftKeyDown()) {
                usedBundles = itemStack.getCount();
            }

            boolean hasUsedBundle = false;
            for (int k = 0; k < usedBundles; k++) {
                List<ItemStack> items = new ArrayList<>();

                // Generate loot and throw on the ground
                int min = LootBundleConfig.COMMON.minLootAmount.get();
                int max = LootBundleConfig.COMMON.maxLootAmount.get();
                int count = min <= max ? level.random.nextInt(min, max + 1) : level.random.nextInt(max + 1);
                int i = 0;
                int u = 0;
                while (i < count && u < 200) {
                    ItemStack randomStack = getRandomItem(level.random);
                    if (!randomStack.isEmpty()) {
                        items.add(randomStack);
                        i++;
                    }
                    u++;
                }
                if (dropContents(items, player)) {
                    itemStack.shrink(1);
                    this.playDropContentsSound(player);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    hasUsedBundle = true;
                }
            }
            if (hasUsedBundle) {
                return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
            }
        }
        return super.use(level, player, hand);
    }

    private static ItemStack getRandomItem(RandomSource rand) {
        if (possibleItems.isEmpty()) {
            if (LootBundleConfig.COMMON.whitelist.get()) {
                BuiltInRegistries.ITEM.getTagOrEmpty(ModTags.WHITELIST).forEach(itemHolder -> {
                    Item item = itemHolder.value();
                    if (isItemAllowed(item)) {
                        possibleItems.add(item);
                    }
                });
            } else {
                BuiltInRegistries.ITEM.forEach(item -> {
                    if (isItemAllowed(item)) {
                        possibleItems.add(item);
                    }
                });
            }
        }
        if (possibleItems.size() > 0) {
            Item item = possibleItems.get(rand.nextInt(possibleItems.size()));

            var stack = new ItemStack(item);
            stack.setCount(Math.min(item.getMaxStackSize(stack), rand.nextInt(1, LootBundleConfig.COMMON.maxStackSize.get() + 1)));
            return stack;
        }
        return ItemStack.EMPTY;
    }

    private static boolean dropContents(List<ItemStack> stacks, Player player) {
        if (player instanceof ServerPlayer) {
            for(ItemStack stack: stacks) {
                if (LootBundleConfig.COMMON.inventoryInsert.get() && player.getInventory().getFreeSlot() > 0) {
                    player.addItem(stack);
                } else {
                    player.drop(stack, true);
                }
            }
        }

        return true;
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static boolean isItemAllowed(Item item) {
        if (!item.builtInRegistryHolder().is(ModTags.BLACKLIST)) {
            if (!item.builtInRegistryHolder().is(ModTags.WHITELIST) && !LootBundleConfig.COMMON.disallowedItemNames.get().isEmpty()) {
                for (String s: LootBundleConfig.COMMON.disallowedItemNames.get()) {
                    if (BuiltInRegistries.ITEM.getKey(item).toString().contains(s)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
