package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.ServerTickManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.concurrent.TimeUnit;

public class GettickCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder)
                CommandManager.literal("get_tick")
                        .requires(source -> source.hasPermissionLevel(2)))
                .then(
                        CommandManager.literal("target_tps")
                                .then(CommandManager
                                        .argument("scale", FloatArgumentType.floatArg())
                                        .executes((context) -> {
                                            return executeTargetTPS((ServerCommandSource)
                                                    context.getSource(),
                                                    FloatArgumentType.getFloat(context, "scale")
                                            );
                                        })
                                )
                                .executes((context) -> {
                                    return executeTargetTPS((ServerCommandSource)
                                                    context.getSource(),
                                            1.0f
                                    );
                                })
                ).then(
                        CommandManager.literal("tps")
                                .then(CommandManager
                                        .argument("scale", FloatArgumentType.floatArg())
                                        .executes((context) -> {
                                            return executeTPS((ServerCommandSource)
                                                            context.getSource(),
                                                    FloatArgumentType.getFloat(context, "scale")
                                            );
                                        })
                                )
                                .executes((context) -> {
                                    return executeTPS((ServerCommandSource)
                                                    context.getSource(),
                                            1.0f
                                    );
                                })
                ).then(
                        CommandManager.literal("mspt")
                                .then(CommandManager
                                        .argument("scale", FloatArgumentType.floatArg())
                                        .executes((context) -> {
                                            return executeMspt((ServerCommandSource)
                                                            context.getSource(),
                                                    FloatArgumentType.getFloat(context, "scale")
                                            );
                                        })
                                )
                                .executes((context) -> {
                                    return executeMspt((ServerCommandSource)
                                                    context.getSource(),
                                            1.0f
                                    );
                                })
                ).then(
                        CommandManager.literal("nspt")
                                .then(CommandManager
                                        .argument("scale", FloatArgumentType.floatArg())
                                        .executes((context) -> {
                                            return executeNspt((ServerCommandSource)
                                                            context.getSource(),
                                                    FloatArgumentType.getFloat(context, "scale")
                                            );
                                        })
                                )
                                .executes((context) -> {
                                    return executeNspt((ServerCommandSource)
                                                    context.getSource(),
                                            1.0f
                                    );
                                })
                )
        );
    }

    private static int executeTargetTPS(ServerCommandSource source, float value) {
        //목표 tps 표시.
        ServerTickManager serverTickManager = source.getServer().getTickManager();
        float f = serverTickManager.getTickRate() * value;
        source.sendFeedback(() -> {
            return Text.literal("target tps: " + (f/value));
        }, true);
        return (int) f;
    }

    private static int executeTPS(ServerCommandSource source, float value) {
        //현재 tps 표시.
        ServerTickManager serverTickManager = source.getServer().getTickManager();
        long mspt = source.getServer().getAverageNanosPerTick();
        float target_tps = serverTickManager.getTickRate();

        //만약 tps 저하가 일어난다면
        if (serverTickManager.getNanosPerTick() < mspt) {
            target_tps = (float) TimeUnit.SECONDS.toNanos(1L) / mspt;
        }

        float finalTarget_tps = target_tps;
        source.sendFeedback(() -> {
            return Text.literal("tps: " + (finalTarget_tps));
        }, true);
        return (int) (target_tps * value);
    }

    private static int executeMspt(ServerCommandSource source, float value) {
        //현재 mspt 표시. 1틱을 연산하는데 걸리는 시간(ms)
        float mspt = (value / 1000000) * source.getServer().getAverageNanosPerTick();
        source.sendFeedback(() -> {
            return Text.literal("mspt: " + (mspt/value));
        }, true);
        return (int) mspt;
    }

    private static int executeNspt(ServerCommandSource source, float value) {
        //현재 nspt 표시. 나노초!
        long nspt = source.getServer().getAverageNanosPerTick();
        source.sendFeedback(() -> {
            return Text.literal("nspt: " + (nspt));
        }, true);
        return (int) (nspt * value);
    }
}
