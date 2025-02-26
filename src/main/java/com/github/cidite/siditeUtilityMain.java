package com.github.cidite;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class siditeUtilityMain implements ModInitializer {
	public static final String MOD_ID = "sidite_utility";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
	public void onInitialize() {
		LOGGER.info("Sidite Utility Initialize.");
	}
}