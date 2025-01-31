package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.RotationArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;

public class RotCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("rot")
                        .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager
                        .argument("target", EntityArgumentType.entity())
                        .then(CommandManager
                                .argument("rotation", RotationArgumentType.rotation())
                                .executes(context -> execute(
                                        context.getSource(),
                                        EntityArgumentType.getEntity(context, "target"),
                                        RotationArgumentType.getRotation(context,"rotation"))
                                )
                        )
                )
                .then(CommandManager
                        .argument("rotation", RotationArgumentType.rotation())
                        .executes(context -> execute(
                                context.getSource(),
                                (context.getSource()).getEntity(),
                                RotationArgumentType.getRotation(context, "rotation"))
                        )
                )
                .executes(context -> executeDefault(
                        context.getSource(),
                        (context.getSource()).getEntity())
                )
        );
    }

    private static int execute(ServerCommandSource source, Entity entity, PosArgument rotation) {
        Vec2f vec2f = rotation.getRotation(source);
        entity.rotate(vec2f.y, vec2f.x);
        source.sendFeedback(() -> Text.translatable("commands.rotate.success", entity.getDisplayName()), true);
        return 1;
    }

    private static int executeDefault(ServerCommandSource source, Entity entity) {
        Vec2f vec2f = source.getRotation();
        entity.rotate(vec2f.y, vec2f.x);
        source.sendFeedback(() -> Text.translatable("commands.rotate.success", entity.getDisplayName()), true);
        return 1;
    }

}
