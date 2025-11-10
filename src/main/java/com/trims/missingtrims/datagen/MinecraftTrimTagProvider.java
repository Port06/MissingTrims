package com.trims.missingtrims.datagen;

import com.trims.missingtrims.MissingTrims;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinecraftTrimTagProvider extends ItemTagsProvider {
    public MinecraftTrimTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                    CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        // Important: pass "minecraft" here so this provider writes into the minecraft namespace
        super(output, lookupProvider, blockTags, "minecraft", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add your 5 custom trims to the vanilla trim_templates tag
        this.tag(ItemTags.TRIM_TEMPLATES)
                .add(
                        MissingTrims.SNOWFLAKE_ARMOR_TRIM.get(),
                        MissingTrims.CRAWLER_ARMOR_TRIM.get(),
                        MissingTrims.CRACK_ARMOR_TRIM.get(),
                        MissingTrims.MYSTIC_ARMOR_TRIM.get(),
                        MissingTrims.ETHEREAL_ARMOR_TRIM.get()
                )
                .replace(false); // Merge with vanilla
    }
}