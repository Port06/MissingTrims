package com.trims.missingtrims;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LootModifiers {
    // Correct: Use MapCodec for the serializer register [citation:1]
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MissingTrims.MOD_ID);

    // Correct: Register the MapCodec
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AddItemModifier>> ADD_ITEM =
            LOOT_MODIFIERS.register("add_item", () -> AddItemModifier.CODEC);

    public static class AddItemModifier extends LootModifier {
        public static final MapCodec<AddItemModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        LootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter((AddItemModifier m) -> m.conditions),
                        ResourceLocation.CODEC.fieldOf("item").forGetter((AddItemModifier m) -> m.itemId),
                        Codec.FLOAT.fieldOf("chance").forGetter((AddItemModifier m) -> m.chance)
                ).apply(inst, (conditions, itemId, chance) -> new AddItemModifier(conditions, itemId, chance))
        );

        private final ResourceLocation itemId;
        private final Item item;
        private final float chance;

        // Constructor used by codec: resolve the Item from the registry here
        public AddItemModifier(LootItemCondition[] conditions, ResourceLocation itemId, float chance) {
            super(conditions);
            this.itemId = itemId;
            // resolve item from registry; throws if missing (you can change to getOptional + fallback if desired)
            this.item = BuiltInRegistries.ITEM.get(itemId);
            this.chance = chance;
        }

        // Optional getters (used elsewhere)
        public ResourceLocation getItemId() {
            return itemId;
        }

        public Item getItem() {
            return item;
        }

        public float getChance() {
            return chance;
        }

        @Override
        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            if (context.getRandom().nextFloat() < chance) {
                generatedLoot.add(new ItemStack(item));
            }
            return generatedLoot;
        }

        @Override
        public MapCodec<? extends IGlobalLootModifier> codec() {
            return CODEC;
        }
    }
}