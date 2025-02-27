package com.github.cidite.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.Locale;

public class PosCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("pos")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager
                                .argument("target", EntityArgumentType.entity())
                                .then(CommandManager
                                        .argument("pos", Vec3ArgumentType.vec3())
                                        .executes(context -> execute(
                                                context.getSource(),
                                                EntityArgumentType.getEntity(context, "target"),
                                                Vec3ArgumentType.getPosArgument(context, "pos"))
                                        )
                                )
                        )
                        .then(CommandManager
                                .argument("pos",Vec3ArgumentType.vec3())
                                .executes(context -> execute(
                                        context.getSource(),
                                        (context.getSource()).getEntity(),
                                        Vec3ArgumentType.getPosArgument(context, "pos"))
                                )
                        )
                        .executes(context -> executeDefault(
                                context.getSource(),
                                (context.getSource()).getEntity())
                        )
        );
    }

    private static int execute(ServerCommandSource source, Entity entity, PosArgument location) {
        Vec3d vec3d = location.getPos(source);
        ServerPlayerEntity player = source.getPlayer();
        entity.updatePosition(vec3d.x, vec3d.y, vec3d.z);
        //실행자가 플레이어일 경우 tp 패킷 날리기. 디싱크 방지.
        if (player != null) {
            player.requestTeleport(vec3d.x, vec3d.y, vec3d.z);
        }
        source.sendFeedback(() -> Text.translatableWithFallback("commands.pos.success", "Moved %s to %s, %s, %s", entity.getDisplayName(), formatFloat(vec3d.x), formatFloat(vec3d.y), formatFloat(vec3d.z)), true);
        return 1;
    }

    private static int executeDefault(ServerCommandSource source, Entity entity) {
        Vec3d vec3d = source.getPosition();
        ServerPlayerEntity player = source.getPlayer();
        entity.updatePosition(vec3d.x, vec3d.y, vec3d.z);
        if (player != null) {
            player.requestTeleport(vec3d.x, vec3d.y, vec3d.z);
        }
        source.sendFeedback(() -> Text.translatableWithFallback("commands.pos.success", "Moved %s to %s, %s, %s", entity.getDisplayName(), formatFloat(vec3d.x), formatFloat(vec3d.y), formatFloat(vec3d.z)), true);
        return 1;
    }

    private static String formatFloat(double d) {
        return String.format(Locale.ROOT, "%f", d);
    }
// /tp <대상을> <대상에게> 와 비슷한 코드.
//
//    private static int executeEntity(ServerCommandSource source, Entity entity, Entity destination) {
//        Vec3d vec3d = destination.getPos();
//        ServerPlayerEntity player = source.getPlayer();
//        //플레이어일 경우 패킷 날리기
//        if (player != null) {
//            player.requestTeleport(vec3d.x, vec3d.y, vec3d.z);
//        }
//        source.sendFeedback(() -> {
//            return Text.translatable("commands.pos.success.target", entity.getDisplayName(), destination.getDisplayName());
//        }, true);
//        return 1;
//    }
}
