package net.funkystudios.funkycore.recipe.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.funkystudios.funkycore.init.FCRecipeSerializerInit;
import net.funkystudios.funkycore.init.FCRecipeTypeInit;
import net.funkystudios.funkycore.recipe.input.FoundryRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class FoundryRecipe implements Recipe<FoundryRecipeInput> {

    private static final int INPUT_SLOT = 0;
    private static final int CARBON_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    final ItemStack output;
    final Ingredient input;
    final Ingredient addition;
    final int time;

    public FoundryRecipe(Ingredient input, Ingredient addition, ItemStack output, int time){
        this.input = input;
        this.addition = addition;
        this.output = output;
        this.time = time;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getTime() {
        return time;
    }

    @Override
    public boolean matches(FoundryRecipeInput inventory, World world) {
        if(world.isClient) return false;
        if(inventory.getSize() < 2) return false;
        return this.input.test(inventory.getStackInSlot(0)) && this.addition.test(inventory.getStackInSlot(1));
    }

    @Override
    public ItemStack craft(FoundryRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return getResult(lookup).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FCRecipeSerializerInit.FOUNDRY_RECIPE;
    }



    @Override
    public RecipeType<?> getType() {
        return FCRecipeTypeInit.FOUNDRY;
    }

    public static class Serializer implements RecipeSerializer<FoundryRecipe> {
        public final MapCodec<FoundryRecipe> CODEC;

        public Serializer(int time){
            this.CODEC = RecordCodecBuilder.mapCodec(
                    in -> in.group(
                                    Ingredient.ALLOW_EMPTY_CODEC.fieldOf("input").forGetter(recipe -> recipe.input),
                                    Ingredient.ALLOW_EMPTY_CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
                                    ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                                    Codec.INT.fieldOf("time").orElse(time).forGetter(recipe -> recipe.time)
                            )
                            .apply(in, FoundryRecipe::new)
            );
        }

        public static final PacketCodec<RegistryByteBuf, FoundryRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                FoundryRecipe.Serializer::write, FoundryRecipe.Serializer::read
        );
        @Override
        public MapCodec<FoundryRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FoundryRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static FoundryRecipe read(RegistryByteBuf buf){
            Ingredient input = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient addition = Ingredient.PACKET_CODEC.decode(buf);
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            int time = buf.readVarInt();
            return new FoundryRecipe(input, addition, output, time);
        }

        private static void write(RegistryByteBuf buf, FoundryRecipe recipe){
            Ingredient.PACKET_CODEC.encode(buf, recipe.input);
            Ingredient.PACKET_CODEC.encode(buf, recipe.addition);
            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
            buf.writeVarInt(recipe.time);
        }
    }



}
