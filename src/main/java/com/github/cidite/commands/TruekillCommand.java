package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class TruekillCommand {
    public TruekillCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("truekill")
                        .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> execute(
                        context.getSource(),
                        (context.getSource()).getEntity())
                )
        );
    }

    private static int execute(ServerCommandSource source, Entity entity) {
        ServerPlayerEntity player = source.getPlayer();
        if (player != null) {
            player.setHealth(0.0f);
        }
        entity.discard();
        source.sendFeedback(() -> Text.translatable("commands.kill.success.single", entity.getDisplayName()), true);
        return 1;
    }
}
