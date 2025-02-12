package net.funkystudios.funkycore.init;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.network.BlockPosPayload;
import net.funkystudios.funkycore.screen.handler.FoundryScreenHandler;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class FCScreenHandlerTypeInit {
    public static final ScreenHandlerType<FoundryScreenHandler> FOUNDRY_SCREEN_HANDLER =  register("foundry", FoundryScreenHandler::new, BlockPosPayload.PACKET_CODEC);

    public static <T extends ScreenHandler, D extends CustomPayload>ExtendedScreenHandlerType<T,D> register(String name,
                                                ExtendedScreenHandlerType.ExtendedFactory<T,D> factory, PacketCodec<? super RegistryByteBuf, D> codec){
        return Registry.register(Registries.SCREEN_HANDLER, FunkyCore.id(name), new ExtendedScreenHandlerType<>(factory, codec));
    }
    public static void load(){}
}
