package com.github.cidite.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.network.packet.s2c.play.DebugSampleS2CPacket;
import net.minecraft.server.SampleSubscriptionTracker;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.log.DebugSampleType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Mixin(SampleSubscriptionTracker.class)
public class SampleSubscriptionTrackerMixin {
    @Inject(at = @At("HEAD"), method = "addPlayer")
    private void init(ServerPlayerEntity player, DebugSampleType type, CallbackInfo info) {
        //ServerCommandSource source = player.getCommandSource();
        //player.getServer().getCommandManager().executeWithPrefix(source, "function #sidite:f3_2");
        //^^ 이거 그대로 명령어 실행하는것보다 더 나은 방법 있는데 어캐할지 몰라서 땜빵된 코드
    }
}