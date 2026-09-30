package simpleships;
/*
 * SimpleShips
 * Copyright (c) 2026, Jere McDevitt
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class DisplayEntityHandle {
	Display displayEntity;
	Vector3f localOffset;
	float localYaw;
	boolean originalGravity;
	Vector3i blockOffset;
	Vector3f intraBlockOffset;
	float shipYawAtAssemble;
	int interpolationDelay;
	int interpolationDuration;
	int teleportDuration;
	

	public DisplayEntityHandle(Display displayEntity, Location anchor, float shipYawAtAssemble) {
		this.displayEntity = displayEntity;
		this.interpolationDelay = displayEntity.getInterpolationDelay();
		this.interpolationDuration = displayEntity.getInterpolationDuration();
		this.teleportDuration = displayEntity.getTeleportDuration();

		//to ensure smooth movements
		displayEntity.setInterpolationDuration(SimpleShipsPlugin.configuration.lerpDuration);
		displayEntity.setInterpolationDelay(-1);
		displayEntity.setTeleportDuration(SimpleShipsPlugin.configuration.teleportDuration);
		
		
		this.shipYawAtAssemble = shipYawAtAssemble;
		Location displayEntityLoc = displayEntity.getLocation();

		//this is needed to ensure the armor displayEntitys restore at the correct offset
		//on the block to match where they started otherwise they drift
		int sx = displayEntityLoc.getBlockX();
		int sy = displayEntityLoc.getBlockY();
		int sz = displayEntityLoc.getBlockZ();
		this.blockOffset = new Vector3i(sx - anchor.getBlockX(), sy - anchor.getBlockY(), sz - anchor.getBlockZ());
		this.intraBlockOffset = new Vector3f((float)(displayEntityLoc.getX() - sx),
																				 (float)(displayEntityLoc.getY() - sy),
																				 (float)(displayEntityLoc.getZ() - sz));
			

			
		Vector3f worldOffset = new Vector3f((float)(displayEntityLoc.getX() - anchor.getX()),
																				(float)(displayEntityLoc.getY() - anchor.getY()),
																				(float)(displayEntityLoc.getZ() - anchor.getZ()));
		Quaternionf inverseShipRotation = new Quaternionf().rotateY((float)Math.toRadians(shipYawAtAssemble));
		this.localOffset = new Vector3f(worldOffset);
		inverseShipRotation.transform(this.localOffset);
			
		this.localYaw = UtilFuncs.wrapDegrees(displayEntity.getLocation().getYaw() - shipYawAtAssemble);
		this.originalGravity = displayEntity.hasGravity();
		displayEntity.setGravity(false);
	}

	void move(Location anchor, float yaw) {
		Quaternionf rotation = new Quaternionf().rotateY((float)Math.toRadians(-yaw));
		Vector3f worldOffset = new Vector3f(localOffset);
		rotation.transform(worldOffset);
		Location loc = anchor.clone().add(worldOffset.x, worldOffset.y, worldOffset.z);

		loc.setYaw(UtilFuncs.wrapDegrees(yaw + localYaw));
		displayEntity.teleport(loc);
	}

	void restore(Location anchor, float finalYaw) {
		Vector3i rotated = UtilFuncs.rotateOffsetCardinalInt(this.blockOffset, this.shipYawAtAssemble, finalYaw);
		Location blockLoc = anchor.clone().add(rotated.x, rotated.y, rotated.z);
		Block block = blockLoc.getBlock();
		Location loc = block.getLocation().clone().add(this.intraBlockOffset.x, this.intraBlockOffset.y, this.intraBlockOffset.z);
		loc.setYaw(UtilFuncs.wrapDegrees(finalYaw + localYaw));
		displayEntity.teleport(loc);
		displayEntity.setGravity(originalGravity);
		
		//to ensure smooth movements
		displayEntity.setInterpolationDuration(interpolationDuration);
		displayEntity.setInterpolationDelay(interpolationDelay);
		displayEntity.setTeleportDuration(teleportDuration);
	}
		
}
