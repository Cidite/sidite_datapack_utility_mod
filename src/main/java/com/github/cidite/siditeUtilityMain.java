package com.github.cidite;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import com.github.cidite.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class siditeUtilityMain implements ModInitializer {
	public static final String MOD_ID = "sidite_utility";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, commandRegistryAccess, ignored1) -> {
			PosCommand.register(dispatcher);
			RotCommand.register(dispatcher);
			GetCommand.register(dispatcher);
		});
	}
}