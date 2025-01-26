package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

public class ChanceCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder)
                CommandManager.literal("chance")
                        .requires(source -> source.hasPermissionLevel(0)))
                .then(
                        CommandManager.literal("value").then(CommandManager
                                        .argument("chance", DoubleArgumentType.doubleArg(0, 1))
                                        .executes((context) -> {
                                            return execute(
                                                    context.getSource(),
                                                    DoubleArgumentType.getDouble(context, "chance"),
                                                    false);
                                        })
                                )
                ).then(
                        CommandManager.literal("roll").then(CommandManager
                                        .argument("chance", DoubleArgumentType.doubleArg(0, 1))
                                        .executes((context) -> {
                                            return execute(
                                                    context.getSource(),
                                                    DoubleArgumentType.getDouble(context, "chance"),
                                                    true);
                                        })
                                )
                )
        );
    }

    private static int execute(ServerCommandSource source, Double chance, boolean roll) {
        Random random = source.getWorld().getRandom();
        double randomValue = random.nextDouble();
        int result = (randomValue <= chance) ? 1 : 0;

        if (roll) {
            source.getServer().getPlayerManager().broadcast(Text.translatable("commands.random.roll", source.getDisplayName(), result, chance, chance), false);
        } else {
            source.sendFeedback(() -> {
                return Text.translatable("commands.random.sample.success", result);
            }, false);
        }
        return result;
    }
}
