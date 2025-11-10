package com.trims.missingtrims.datagen;

import com.trims.missingtrims.MissingTrims;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // --- Your existing smithing-table recipes (kept unchanged) ---
        trimSmithing(recipeOutput, MissingTrims.SNOWFLAKE_ARMOR_TRIM.get(),
                ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "snowflake"));
        trimSmithing(recipeOutput, MissingTrims.CRACK_ARMOR_TRIM.get(),
                ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "crack"));
        trimSmithing(recipeOutput, MissingTrims.CRAWLER_ARMOR_TRIM.get(),
                ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "crawler"));
        trimSmithing(recipeOutput, MissingTrims.MYSTIC_ARMOR_TRIM.get(),
                ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "mystic"));
        trimSmithing(recipeOutput, MissingTrims.ETHEREAL_ARMOR_TRIM.get(),
                ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, "ethereal"));

        // --- New crafting-table duplication recipes (XYX / XZX / XXX) ---
        // X = diamond, Y = template, Z = material
        createCraftingDuplication(recipeOutput,
                MissingTrims.SNOWFLAKE_ARMOR_TRIM.get(),
                Items.PACKED_ICE,
                "snowflake");

        createCraftingDuplication(recipeOutput,
                MissingTrims.CRAWLER_ARMOR_TRIM.get(),
                Items.MOSSY_COBBLESTONE,
                "crawler");

        createCraftingDuplication(recipeOutput,
                MissingTrims.CRACK_ARMOR_TRIM.get(),
                Items.COBBLED_DEEPSLATE,
                "crack");

        createCraftingDuplication(recipeOutput,
                MissingTrims.MYSTIC_ARMOR_TRIM.get(),
                Items.MAGENTA_TERRACOTTA,
                "mystic");

        createCraftingDuplication(recipeOutput,
                MissingTrims.ETHEREAL_ARMOR_TRIM.get(),
                Items.PURPLE_TERRACOTTA,
                "ethereal");
    }

    /**
     * Produces a shaped crafting recipe that duplicates the template:
     * Pattern:
     * XYX
     * XZX
     * XXX
     * where X = diamond, Y = template, Z = material
     *
     * Output: 2 of the template item.
     */
    private void createCraftingDuplication(RecipeOutput recipeOutput, Item templateItem, Item materialItem, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, templateItem, 2)
                .define('X', Items.DIAMOND)
                .define('Y', templateItem)
                .define('Z', materialItem)
                .pattern("XYX")
                .pattern("XZX")
                .pattern("XXX")
                .unlockedBy("has_" + name + "_trim", has(templateItem))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MissingTrims.MOD_ID, name + "_trim_duplication_crafting"));
    }
}