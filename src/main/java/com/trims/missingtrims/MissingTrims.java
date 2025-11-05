package com.trims.missingtrims;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootModifierManager;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(MissingTrims.MODID)
public class MissingTrims {
    public static final String MODID = "missingtrims";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MissingTrims(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
    }

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<net.minecraft.world.item.Item> SNOWFLAKE_ARMOR_TRIM = ITEMS.registerSimpleItem("snowflake_armor_trim");
    public static final DeferredItem<net.minecraft.world.item.Item> CRAWLER_ARMOR_TRIM = ITEMS.registerSimpleItem("crawler_armor_trim");
    public static final DeferredItem<net.minecraft.world.item.Item> CRACK_ARMOR_TRIM = ITEMS.registerSimpleItem("crack_armor_trim");
    public static final DeferredItem<net.minecraft.world.item.Item> MYSTIC_ARMOR_TRIM = ITEMS.registerSimpleItem("mystic_armor_trim");
    public static final DeferredItem<net.minecraft.world.item.Item> ETHEREAL_ARMOR_TRIM = ITEMS.registerSimpleItem("ethereal_armor_trim");

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            // Add each trim individually after the bolt armor trim
            event.insertAfter(net.minecraft.world.item.Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE.getDefaultInstance(),
                    SNOWFLAKE_ARMOR_TRIM.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(SNOWFLAKE_ARMOR_TRIM.get().getDefaultInstance(),
                    CRAWLER_ARMOR_TRIM.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(CRAWLER_ARMOR_TRIM.get().getDefaultInstance(),
                    CRACK_ARMOR_TRIM.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(CRACK_ARMOR_TRIM.get().getDefaultInstance(),
                    MYSTIC_ARMOR_TRIM.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(MYSTIC_ARMOR_TRIM.get().getDefaultInstance(),
                    ETHEREAL_ARMOR_TRIM.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}