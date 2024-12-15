package com.github.cidite.mixin;


import com.github.cidite.commands.ChanceCommand;
import com.github.cidite.commands.GetposCommand;
import com.github.cidite.commands.GetrotationCommand;
import com.github.cidite.commands.GettickCommand;
import com.github.cidite.commands.PosCommand;
import com.github.cidite.commands.RotCommand;
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

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"), method = "<init>")
    private void init(CommandManager.RegistrationEnvironment environment, CommandRegistryAccess commandRegistryAccess, CallbackInfo info) {
			ChanceCommand.register(this.dispatcher);
			GetposCommand.register(this.dispatcher);
			GetrotationCommand.register(this.dispatcher);
			GettickCommand.register(this.dispatcher);
			PosCommand.register(this.dispatcher);
			RotCommand.register(this.dispatcher);
	}

}
