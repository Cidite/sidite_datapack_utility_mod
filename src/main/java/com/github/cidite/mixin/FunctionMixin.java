package com.github.cidite.mixin;

import net.minecraft.command.CommandExecutionContext;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandExecutionContext.class)
public class FunctionMixin {
    @Inject(at = @At(value = "HEAD"), method = "run()V")
    private void injected(CallbackInfo ci) {
//        Profiler profiler = Profilers.get();
//        profiler.push("test_profiler");
    }

    @Inject(at = @At(value = "TAIL"), method = "run")
    private void injected2(CallbackInfo ci) {
//        Profiler profiler = Profilers.get();
//        profiler.pop();
    }

}