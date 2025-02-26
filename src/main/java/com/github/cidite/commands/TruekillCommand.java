package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
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
        //실행자가 플레이어일 경우 즉시 사망하기. 안그럼 무적됨.
        if (player != null) {
            player.setHealth(0.0f);
        }
        entity.discard();
        source.sendFeedback(() -> Text.translatable("commands.kill.success.single", entity.getDisplayName()), true);
        return 1;
    }
}
