package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

// /get_rotation <X|Y> [스케일] [대상]
// 개체의 회전 X/Y를 출력하는 명령어.

public class GetrotationCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder)
                CommandManager.literal("get_rotation")
                        .requires(source -> source.hasPermissionLevel(2)))
                .then(
                        CommandManager.literal("x").then(CommandManager
                                        .argument("scale", DoubleArgumentType.doubleArg())
                                        .then(CommandManager
                                                .argument("target", EntityArgumentType.entity())
                                                .executes((context) -> {
                                                    return executeScale(
                                                                    context.getSource(),
                                                            EntityArgumentType.getEntity(context, "target"),
                                                            EntityArgumentType.getEntity(context, "target").getYaw(),
                                                            DoubleArgumentType.getDouble(context, "scale"));
                                                })
                                        )
                                        .executes((context) -> {
                                            return executeScale(
                                                            context.getSource(),
                                                    (context.getSource()).getEntity(),
                                                    (context.getSource()).getEntity().getYaw(),
                                                    DoubleArgumentType.getDouble(context, "scale"));
                                        })
                                )
                                .executes((context) -> {
                                    return execute(
                                                    context.getSource(),
                                            (context.getSource()).getEntity(),
                                            (context.getSource()).getEntity().getYaw());
                                })
                ).then(
                        CommandManager.literal("y").then(CommandManager
                                        .argument("scale", DoubleArgumentType.doubleArg())
                                        .then(CommandManager
                                                .argument("target", EntityArgumentType.entity())
                                                .executes((context) -> {
                                                    return executeScale(
                                                                    context.getSource(),
                                                            EntityArgumentType.getEntity(context, "target"),
                                                            EntityArgumentType.getEntity(context, "target").getYaw(),
                                                            DoubleArgumentType.getDouble(context, "scale"));
                                                })
                                        )
                                        .executes((context) -> {
                                            return executeScale(
                                                            context.getSource(),
                                                    (context.getSource()).getEntity(),
                                                    (context.getSource()).getEntity().getPitch(),
                                                    DoubleArgumentType.getDouble(context, "scale"));
                                        })
                                )
                                .executes((context) -> {
                                    return execute(
                                                    context.getSource(),
                                            (context.getSource()).getEntity(),
                                            (context.getSource()).getEntity().getPitch());
                                })
                )
        );
    }

    //실행시 값을 확인함.
    private static int execute(ServerCommandSource source, Entity entity, double value) {
        source.sendFeedback(() -> {
            return Text.translatable("commands.data.entity.query", entity.getDisplayName(), value);
        }, true);
        return (int) value;
    }
    //값 확인 +스케일 곱셈
    private static int executeScale(ServerCommandSource source, Entity entity, double value, double scale) {
        value *= scale;
        double i = value;
        source.sendFeedback(() -> {
            return Text.translatable("commands.data.entity.query", entity.getDisplayName(), i);
        }, true);
        return (int) i;
    }

}