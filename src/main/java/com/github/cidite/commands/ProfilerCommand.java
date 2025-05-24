package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;

public class ProfilerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("profiler")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("push")
                                .then(CommandManager
                                        .argument("name", StringArgumentType.string())
                                        .executes(context -> executePush(
                                                StringArgumentType.getString(context,"name"))
                                        )
                                )
                        )
                        .then(CommandManager.literal("pop")
                                .executes(context -> executePop())
                        )
        );
    }

    private static int executePush(String string) {
        Profiler profiler = Profilers.get();
        profiler.push(string);
        return 1;
    }
    private static int executePop() {
        Profiler profiler = Profilers.get();
        profiler.pop();
        return 1;
    }

}
