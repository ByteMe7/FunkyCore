package net.funkystudios.funkycore.mixin;

import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.utils.FCTags;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1))
    private void onInit(CallbackInfo ci) {
        Registries.ITEM.getIds()
                .stream()
                .filter(key -> key.getNamespace().equals(FunkyCore.MOD_ID))
                .map(Registries.ITEM::getOrEmpty)
                .map(Optional::orElseThrow)
                .filter(item -> item.getDefaultStack().isIn(FCTags.Items.HAMMER))
                .forEach(item -> this.loadItemModel(ModelIdentifier.ofInventoryVariant(FunkyCore.id(
                        Registries.ITEM.getId(item).getPath() + "_3d"
                ))));
    }
}
