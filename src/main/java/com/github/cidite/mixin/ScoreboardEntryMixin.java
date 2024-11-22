package com.github.cidite.mixin;

import net.minecraft.scoreboard.ScoreboardEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

//스코어보드 변수가 '#'로 시작하면 숨기는 것 대신 '믕'으로 시작하면 숨김.

@Mixin(ScoreboardEntry.class)
public class ScoreboardEntryMixin {
    @ModifyConstant(method = "hidden()Z",constant = @Constant(stringValue = "#")
    )
    public String hidden(String constant) {
        return "믕";
    }

}