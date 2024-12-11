package com.github.cidite.mixin;


import com.github.cidite.commands.*;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
	@Shadow
	@Final
	private CommandDispatcher<ServerCommandSource> dispatcher;

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"), method = "<init>(Lnet/minecraft/server/command/CommandManager$RegistrationEnvironment;Lnet/minecraft/command/CommandRegistryAccess;)V")
    private void init(CommandManager.RegistrationEnvironment environment, CommandRegistryAccess commandRegistryAccess, CallbackInfo info) {
			ChanceCommand.register(this.dispatcher);
			GetposCommand.register(this.dispatcher);
			GetrotationCommand.register(this.dispatcher);
			GettickCommand.register(this.dispatcher);
			PosCommand.register(this.dispatcher);
			RotCommand.register(this.dispatcher);
    }

}
