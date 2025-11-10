package com.trims.missingtrims;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.Random;

public class WanderingTraderTrades {

    private static final Random RAND = new Random();

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        // Only run on server side
        if (event.getLevel().isClientSide()) return;

        Entity entity = event.getEntity();
        if (!(entity instanceof WanderingTrader trader)) return;

        // Costs
        ItemCost costFiveEmeralds = new ItemCost(Items.EMERALD, 5);
        ItemCost costTwentyEmeralds = new ItemCost(Items.EMERALD, 20);

        // Avoid adding duplicates: check whether trader already offers our trims
        boolean hasMystic = false;
        boolean hasEthereal = false;
        for (MerchantOffer offer : trader.getOffers()) {
            ItemStack res = offer.getResult();
            if (res != null) {
                if (res.is(MissingTrims.MYSTIC_ARMOR_TRIM.get())) hasMystic = true;
                if (res.is(MissingTrims.ETHEREAL_ARMOR_TRIM.get())) hasEthereal = true;
            }
        }

        // 33% chance to add Mystic trim (additional trade)
        if (!hasMystic && RAND.nextFloat() < 0.33f) {
            ItemStack mystic = MissingTrims.MYSTIC_ARMOR_TRIM.get().getDefaultInstance();
            trader.getOffers().add(new MerchantOffer(costFiveEmeralds, mystic, 12, 5, 0.05f));
        }

        // 10% chance to add Ethereal trim (additional trade)
        if (!hasEthereal && RAND.nextFloat() < 0.1f) {
            ItemStack ethereal = MissingTrims.ETHEREAL_ARMOR_TRIM.get().getDefaultInstance();
            trader.getOffers().add(new MerchantOffer(costTwentyEmeralds, ethereal, 1, 10, 0.1f));
        }
    }
}
