package com.trims.missingtrims.datagen;

import com.trims.missingtrims.MissingTrims;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MissingTrims.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Datapack entries (trim patterns)
        generator.addProvider(event.includeServer(), new ModDatapackProvider(packOutput, lookupProvider));

        // Recipe provider -> generates recipe jsons (including trim smithing recipes)
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));

        // Item tags provider -> generates item tag JSONs such as minecraft:trim_templates
        // The constructor expects a CompletableFuture<TagLookup<Block>> for block tags; provide an "empty" lookup.
        CompletableFuture<TagsProvider.TagLookup<net.minecraft.world.level.block.Block>> emptyBlockTagLookup =
                CompletableFuture.completedFuture(TagsProvider.TagLookup.empty());

        generator.addProvider(event.includeServer(),
                new MinecraftTrimTagProvider(packOutput, lookupProvider, emptyBlockTagLookup, existingFileHelper));
    }
}