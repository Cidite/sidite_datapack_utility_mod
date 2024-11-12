package com.github.cidite.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.PlayerManager;
import net.minecraft.network.packet.s2c.play.DebugSampleS2CPacket;
import net.minecraft.server.SampleSubscriptionTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.profiler.log.DebugSampleType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.Queue;

@Mixin(SampleSubscriptionTracker.class)
public class SampleSubscriptionTrackerMixin {
    //private final Queue<SampleSubscriptionTracker.PlayerSubscriptionData> pendingQueue = new LinkedList();

    @Inject(at = @At("HEAD"), method = "addPlayer")
    private void init(ServerPlayerEntity player, DebugSampleType type, CallbackInfo info) {
        //System.out.println("F3+2!");
        //this.pendingQueue.add(new SampleSubscriptionTracker.PlayerSubscriptionData(player, type));

    }

}