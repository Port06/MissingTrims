package com.trims.missingtrims;

import com.mojang.logging.LogUtils;
import com.trims.missingtrims.trim.ModTrimPatterns;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import net.minecraft.core.registries.Registries;

@Mod(MissingTrims.MOD_ID)
public class MissingTrims {
    public static final String MOD_ID = "missingtrims";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MissingTrims(IEventBus modEventBus) {
        LOGGER.info("MissingTrims constructor started, registering items and events");
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        LootModifiers.LOOT_MODIFIERS.register(modEventBus);
        NeoForge.EVENT_BUS.register(WanderingTraderTrades.class);
        LOGGER.info("MissingTrims registration complete");
    }

    // Use DeferredRegister for Items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);

    // Use DeferredHolder instead of RegistryObject
    public static final DeferredHolder<Item, Item> SNOWFLAKE_ARMOR_TRIM = ITEMS.register("snowflake_armor_trim",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "snowflake")));

    public static final DeferredHolder<Item, Item> CRAWLER_ARMOR_TRIM = ITEMS.register("crawler_armor_trim",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "crawler")));

    public static final DeferredHolder<Item, Item> CRACK_ARMOR_TRIM = ITEMS.register("crack_armor_trim",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "crack")));

    public static final DeferredHolder<Item, Item> MYSTIC_ARMOR_TRIM = ITEMS.register("mystic_armor_trim",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "mystic")));

    public static final DeferredHolder<Item, Item> ETHEREAL_ARMOR_TRIM = ITEMS.register("ethereal_armor_trim",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "ethereal")));

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            LOGGER.debug("Adding armor trims to Ingredients tab");
            // Insert them into the Ingredients creative tab near vanilla templates
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