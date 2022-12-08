package cy.jdkdigital.lootbundles.item;

import cy.jdkdigital.lootbundles.LootBundleConfig;
import cy.jdkdigital.lootbundles.init.ModTags;
import net.minecraft.core.Registry;
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
import net.minecraftforge.registries.ForgeRegistries;

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
                return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
            }
        }
        return super.use(level, player, hand);
    }

    private static ItemStack getRandomItem(RandomSource rand) {
        if (possibleItems.isEmpty()) {
            if (LootBundleConfig.COMMON.whitelist.get()) {
                Registry.ITEM.getTagOrEmpty(ModTags.WHITELIST).forEach(itemHolder -> {
                    Item item = itemHolder.value();
                    if (ForgeRegistries.ITEMS.getKey(item) != null) {
                        if (isItemAllowed(item)) {
                            possibleItems.add(item);
                        }
                    }
                });
            } else {
                ForgeRegistries.ITEMS.getValues().forEach(item -> {
                    if (isItemAllowed(item)) {
                        possibleItems.add(item);
                    }
                });
            }
        }
        if (possibleItems.size() > 0) {
            Item item = possibleItems.get(rand.nextInt(possibleItems.size()));

            return new ItemStack(item, Math.min(item.getMaxStackSize(), rand.nextInt(1, LootBundleConfig.COMMON.maxStackSize.get() + 1)));
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
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private static boolean isItemAllowed(Item item) {
        if (!item.builtInRegistryHolder().is(ModTags.BLACKLIST) && !item.getCreativeTabs().isEmpty()) {
            if (!item.builtInRegistryHolder().is(ModTags.WHITELIST) && !LootBundleConfig.COMMON.disallowedItemNames.get().isEmpty()) {
                for (String s: LootBundleConfig.COMMON.disallowedItemNames.get()) {
                    if (ForgeRegistries.ITEMS.getKey(item).toString().contains(s)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
