package com.trims.missingtrims.trim;

import com.trims.missingtrims.MissingTrims;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;

public class ModTrimPatterns {
    private static final Logger LOGGER = MissingTrims.LOGGER;

    public static final ResourceKey<TrimPattern> SNOWFLAKE = ResourceKey.create(
            Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "snowflake")
    );
    public static final ResourceKey<TrimPattern> CRAWLER = ResourceKey.create(
            Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "crawler")
    );
    public static final ResourceKey<TrimPattern> CRACK = ResourceKey.create(
            Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "crack")
    );
    public static final ResourceKey<TrimPattern> MYSTIC = ResourceKey.create(
            Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "mystic")
    );
    public static final ResourceKey<TrimPattern> ETHEREAL = ResourceKey.create(
            Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "ethereal")
    );

    public static void bootstrap(BootstrapContext<TrimPattern> context) {
        register(context, MissingTrims.SNOWFLAKE_ARMOR_TRIM, SNOWFLAKE);
        register(context, MissingTrims.CRAWLER_ARMOR_TRIM, CRAWLER);
        register(context, MissingTrims.CRACK_ARMOR_TRIM, CRACK);
        register(context, MissingTrims.MYSTIC_ARMOR_TRIM, MYSTIC);
        register(context, MissingTrims.ETHEREAL_ARMOR_TRIM, ETHEREAL);
    }

    private static void register(BootstrapContext<TrimPattern> context, DeferredHolder<Item, Item> item, ResourceKey<TrimPattern> key) {
        LOGGER.debug("[MissingTrims] Registering trim pattern: {}", key.location());

        // Get the registry holder for the template item from the context's item lookup.
        Holder<Item> templateHolder = context.lookup(Registries.ITEM).getOrThrow(item.getKey());

        // Create the TrimPattern INCLUDING the template holder (this is the 1.21.1-style registration)
        TrimPattern trimPattern = new TrimPattern(
                key.location(),
                templateHolder,
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())),
                false // decal flag; set true if the pattern is a decal
        );

        context.register(key, trimPattern);
    }
}