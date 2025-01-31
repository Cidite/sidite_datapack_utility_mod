package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

// /chance <chance> [roll]
// 확률 테스트 명령어 1 = 100%, 0.5 = 50%
public class ChanceCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("chance")
                        .requires(source -> source.hasPermissionLevel(0))
                .then(CommandManager
                        .argument("chance", DoubleArgumentType.doubleArg(0, 1))
                        .then(CommandManager
                                .argument("roll", BoolArgumentType.bool())
                                .executes(context -> execute(
                                        context.getSource(),
                                        DoubleArgumentType.getDouble(context, "chance"),
                                        BoolArgumentType.getBool(context, "roll"))
                                )
                        )
                        .executes(context -> execute(
                                context.getSource(),
                                DoubleArgumentType.getDouble(context, "chance"),
                                false)
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
            source.sendFeedback(() -> Text.translatable("commands.random.sample.success", result), false);
        }
        return result;
    }
}
