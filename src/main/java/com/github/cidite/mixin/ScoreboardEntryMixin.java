package com.github.cidite.mixin;

import net.minecraft.scoreboard.ScoreboardEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Mixin(ScoreboardEntry.class)
public class ScoreboardEntryMixin {
    @ModifyConstant(method = "hidden()Z",constant = @Constant(stringValue = "#")
    )
    private String hidden(String constant) {
        return "믕";
    }

}