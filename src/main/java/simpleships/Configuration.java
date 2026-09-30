package simpleships;
/*
 * SimpleShips
 * Copyright (c) 2026, Jere McDevitt
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

import java.util.logging.Logger;

public class Configuration {
	static public final boolean DEBUG_ON_DEFAULT = false;
	
	static public final int MAX_SHIP_BLOCKS_DEFAULT = 500;
	static public final int MAX_SHIP_X_WIDTH_DEFAULT = 32;
	static public final int MAX_SHIP_Z_WIDTH_DEFAULT = 32;
	static public final int MAX_SHIP_HEIGHT_DEFAULT  = 16;

	static public final float SHIP_SPEED_DEFAULT = 0.25f;
	static public final float SHIP_REVERSE_SPEED_DEFAULT = 0.125f;
	static public final int UPDATE_TICKS_DEFAULT = 1;
	static public final int TELEPORT_DURATION_DEFAULT = 3;
	static public final int LERP_DURATION_DEFAULT = 0;

	boolean debugOn = DEBUG_ON_DEFAULT;
	int maxBlocks = MAX_SHIP_BLOCKS_DEFAULT;
	int maxXWidth = MAX_SHIP_X_WIDTH_DEFAULT;
	int maxZWidth = MAX_SHIP_Z_WIDTH_DEFAULT;
	int maxHeight = MAX_SHIP_HEIGHT_DEFAULT;

	float shipSpeed = SHIP_SPEED_DEFAULT;
	float shipReverseSpeed = SHIP_REVERSE_SPEED_DEFAULT;
	int updateTicks = UPDATE_TICKS_DEFAULT;
	int teleportDuration = TELEPORT_DURATION_DEFAULT;
	int lerpDuration = LERP_DURATION_DEFAULT;

	Configuration() {
	}

	void showInfo(Logger logger) {
		logger.info("Debug is " + (debugOn?"ON":"OFF"));
		logger.info("Ship Speed Settings");
		logger.info("      Forward Speed: " + shipSpeed);
		logger.info("      Reverse Speed: " + shipReverseSpeed);
		logger.info("       Update Ticks: " + updateTicks);
		logger.info("  Teleport Duration: " + teleportDuration);
		logger.info("      Lerp Duration: " + lerpDuration);
		
		logger.info("Ship Constraints");
		logger.info("         Max blocks: " + maxBlocks);
		logger.info("         X/Z       : " + maxXWidth + "/" + maxZWidth);
		logger.info("         Height    : " + maxHeight);
	}
}
