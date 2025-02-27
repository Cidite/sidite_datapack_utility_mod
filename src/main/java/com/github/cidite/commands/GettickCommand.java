package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.server.ServerTickManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class GettickCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("get_tick")
                        .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("target_tps")
                        .then(CommandManager
                                .argument("scale", FloatArgumentType.floatArg())
                                .executes(context -> executeTargetTPS(
                                        context.getSource(),
                                        FloatArgumentType.getFloat(context, "scale"))
                                )
                        )
                        .executes(context -> executeTargetTPS(
                                context.getSource(),
                                1.0f)
                        )
                )
                .then(CommandManager.literal("tps")
                        .then(CommandManager
                                .argument("scale", FloatArgumentType.floatArg())
                                .executes(context -> executeTPS(
                                        context.getSource(),
                                        FloatArgumentType.getFloat(context, "scale"))
                                )
                        )
                        .executes(context -> executeTPS(
                                context.getSource(),
                                1.0f)
                        )
                )
                .then(CommandManager.literal("mspt")
                        .then(CommandManager
                                .argument("scale", FloatArgumentType.floatArg())
                                .executes(context -> executeMspt(
                                        context.getSource(),
                                        FloatArgumentType.getFloat(context, "scale")
                                ))
                        )
                        .executes(context -> executeMspt(
                                context.getSource(),
                                1.0f)
                        )
                )
                .then(CommandManager.literal("nspt")
                        .then(CommandManager
                                .argument("scale", FloatArgumentType.floatArg())
                                .executes(context -> executeNspt(
                                        context.getSource(),
                                        FloatArgumentType.getFloat(context, "scale"))
                                )
                        )
                        .executes(context -> executeNspt(
                                context.getSource(),
                                1.0f)
                        )
                )
        );
    }

    private static int executeTargetTPS(ServerCommandSource source, float scale) {
        //목표 tps 표시.
        //근데 이건 /tick query로 불러올수 있어서 쓰지 않는게 좋음.
        ServerTickManager serverTickManager = source.getServer().getTickManager();
        float f = serverTickManager.getTickRate();
        source.sendFeedback(() -> Text.translatableWithFallback("commands.get_tick.target_tps", "target tps: %s", f), true);

        return (int) (f * scale);
    }

    private static int executeTPS(ServerCommandSource source, float scale) {
        //현재 tps(Tick per seconds) 표시.
        ServerTickManager serverTickManager = source.getServer().getTickManager();
        long mspt = source.getServer().getAverageNanosPerTick();
        float target_tps = serverTickManager.getTickRate();

        //만약 tps 저하가 일어난다면...
        if (serverTickManager.getNanosPerTick() < mspt) {
            target_tps = (float) 1000000000 / mspt;
        }

        float finalTarget_tps = target_tps;
        source.sendFeedback(() -> Text.translatableWithFallback("commands.get_tick.tps", "tps: %s", (finalTarget_tps)), true);
        return (int) (target_tps * scale);
    }

    private static int executeMspt(ServerCommandSource source, float scale) {
        //현재 mspt(Milli seconds per tic) 표시. 1틱을 연산하는데 걸리는 시간(ms)
        float mspt = (float) source.getServer().getAverageNanosPerTick() / 1000000 ;
        source.sendFeedback(() -> Text.translatableWithFallback("commands.get_tick.mspt", "mspt: %s", (mspt)), true);
        return (int) (mspt * scale);
    }

    private static int executeNspt(ServerCommandSource source, float scale) {
        //현재 nspt(Nano seconds per tick) 표시. 나노초!
        long nspt = source.getServer().getAverageNanosPerTick();
        source.sendFeedback(() -> Text.translatableWithFallback("commands.get_tick.nspt", "nspt: %s", (nspt)), true);
        return (int) (nspt * scale);
    }
}
