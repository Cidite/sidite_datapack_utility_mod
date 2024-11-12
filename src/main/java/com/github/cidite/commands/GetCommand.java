package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

// /get <pos|rot> <X|Y|Z> [스케일] [대상]
// 개체의 좌표/회전 X/Y/Z를 출력하는 명령어.
// /data get으로 뽑는건 느리고 NBT 데이터가 많으면 더 걸림.

public class GetCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder)
                CommandManager.literal("get")
                        .requires(source -> source.hasPermissionLevel(2)))
                .then(
                        (CommandManager.literal("pos")
                                        .then(
                                                CommandManager.literal("x").then(CommandManager
                                                        .argument("scale", DoubleArgumentType.doubleArg())
                                                        .then(CommandManager
                                                                .argument("target", EntityArgumentType.entity())
                                                                .executes((context) -> {
                                                                    return executePosScale((ServerCommandSource)
                                                                            context.getSource(),
                                                                            EntityArgumentType.getEntity(context, "target"),
                                                                            EntityArgumentType.getEntity(context, "target").getX(),
                                                                            DoubleArgumentType.getDouble(context, "scale"));
                                                                })
                                                        )
                                                        .executes((context) -> {
                                                            return executePosScale((ServerCommandSource)
                                                                    context.getSource(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity().getPos().getX(),
                                                                    DoubleArgumentType.getDouble(context, "scale"));
                                                        })
                                                )
                                                .executes((context) -> {
                                                    return executePos((ServerCommandSource)
                                                            context.getSource(),
                                                            ((ServerCommandSource)context.getSource()).getEntity(),
                                                            ((ServerCommandSource)context.getSource()).getEntity().getPos().getX());
                                                })
                                        ).then(
                                        CommandManager.literal("y").then(CommandManager
                                                        .argument("scale", DoubleArgumentType.doubleArg())
                                                        .then(CommandManager
                                                                .argument("target", EntityArgumentType.entity())
                                                                .executes((context) -> {
                                                                    return executePosScale((ServerCommandSource)
                                                                            context.getSource(),
                                                                            EntityArgumentType.getEntity(context, "target"),
                                                                            EntityArgumentType.getEntity(context, "target").getY(),
                                                                            DoubleArgumentType.getDouble(context, "scale"));
                                                                })
                                                        )
                                                        .executes((context) -> {
                                                            return executePosScale((ServerCommandSource)
                                                                    context.getSource(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity().getPos().getY(),
                                                                    DoubleArgumentType.getDouble(context, "scale"));
                                                        })
                                                )
                                                .executes((context) -> {
                                                    return executePos((ServerCommandSource)
                                                            context.getSource(),
                                                            ((ServerCommandSource)context.getSource()).getEntity(),
                                                            ((ServerCommandSource)context.getSource()).getEntity().getPos().getY());
                                                })
                                        ).then(
                                        CommandManager.literal("z").then(CommandManager
                                                        .argument("scale", DoubleArgumentType.doubleArg())
                                                        .then(CommandManager
                                                                .argument("target", EntityArgumentType.entity())
                                                                .executes((context) -> {
                                                                    return executePosScale((ServerCommandSource)
                                                                            context.getSource(),
                                                                            EntityArgumentType.getEntity(context, "target"),
                                                                            EntityArgumentType.getEntity(context, "target").getZ(),
                                                                            DoubleArgumentType.getDouble(context, "scale"));
                                                                })
                                                        )
                                                        .executes((context) -> {
                                                            return executePosScale((ServerCommandSource)
                                                                    context.getSource(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity().getPos().getZ(),
                                                                    DoubleArgumentType.getDouble(context, "scale"));
                                                        })
                                                )
                                                .executes((context) -> {
                                                    return executePos((ServerCommandSource)
                                                            context.getSource(),
                                                            ((ServerCommandSource)context.getSource()).getEntity(),
                                                            ((ServerCommandSource)context.getSource()).getEntity().getPos().getZ());
                                                })
                                )
                        )
                )
                .then(
                        (CommandManager.literal("rot")
                                .then(
                                        CommandManager.literal("x").then(CommandManager
                                                        .argument("scale", DoubleArgumentType.doubleArg())
                                                        .then(CommandManager
                                                                .argument("target", EntityArgumentType.entity())
                                                                .executes((context) -> {
                                                                    return executeRotationScale((ServerCommandSource)
                                                                                    context.getSource(),
                                                                            EntityArgumentType.getEntity(context, "target"),
                                                                            EntityArgumentType.getEntity(context, "target").getYaw(),
                                                                            DoubleArgumentType.getDouble(context, "scale"));
                                                                })
                                                        )
                                                        .executes((context) -> {
                                                            return executeRotationScale((ServerCommandSource)
                                                                            context.getSource(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity().getYaw(),
                                                                    DoubleArgumentType.getDouble(context, "scale"));
                                                        })
                                                )
                                                .executes((context) -> {
                                                    return executeRotation((ServerCommandSource)
                                                                    context.getSource(),
                                                            ((ServerCommandSource)context.getSource()).getEntity(),
                                                            ((ServerCommandSource)context.getSource()).getEntity().getYaw());
                                                })
                                ).then(
                                        CommandManager.literal("y").then(CommandManager
                                                        .argument("scale", DoubleArgumentType.doubleArg())
                                                        .then(CommandManager
                                                                .argument("target", EntityArgumentType.entity())
                                                                .executes((context) -> {
                                                                    return executeRotationScale((ServerCommandSource)
                                                                                    context.getSource(),
                                                                            EntityArgumentType.getEntity(context, "target"),
                                                                            EntityArgumentType.getEntity(context, "target").getYaw(),
                                                                            DoubleArgumentType.getDouble(context, "scale"));
                                                                })
                                                        )
                                                        .executes((context) -> {
                                                            return executeRotationScale((ServerCommandSource)
                                                                            context.getSource(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity(),
                                                                    ((ServerCommandSource)context.getSource()).getEntity().getPitch(),
                                                                    DoubleArgumentType.getDouble(context, "scale"));
                                                        })
                                                )
                                                .executes((context) -> {
                                                    return executeRotation((ServerCommandSource)
                                                                    context.getSource(),
                                                            ((ServerCommandSource)context.getSource()).getEntity(),
                                                            ((ServerCommandSource)context.getSource()).getEntity().getPitch());
                                                })
                                )
                        )
                )
        );
    }

    //좌표를 확인함.
    private static int executePos(ServerCommandSource source, Entity entity, double pos) {
        int i = (int) pos;
        source.sendFeedback(() -> {
            return Text.translatable("commands.data.entity.query", entity.getDisplayName(), i);
        }, true);
        return i;
    }
    //좌표를 확인함.+스케일이 있으면 곱셈
    private static int executePosScale(ServerCommandSource source, Entity entity, double pos, double scale) {
        pos = pos * scale;
        int i = (int) pos;
        source.sendFeedback(() -> {
            return Text.translatable("commands.data.entity.query", entity.getDisplayName(), i);
        }, true);
        return i;
    }
    //위랑 같지만 좌표가 아닌 방향임.
    private static int executeRotation(ServerCommandSource source, Entity entity, double rotation) {
        int i = (int) rotation;
        source.sendFeedback(() -> {
            return Text.translatable("commands.data.entity.query", entity.getDisplayName(), i);
        }, true);
        return i;
    }

    private static int executeRotationScale(ServerCommandSource source, Entity entity, double rotation, double scale) {
        rotation = rotation * scale;
        int i = (int) rotation;
        source.sendFeedback(() -> {
            return Text.translatable("commands.data.entity.query", entity.getDisplayName(), i);
        }, true);
        return i;
    }

}