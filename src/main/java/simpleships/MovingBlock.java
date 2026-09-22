package simpleships;
/*
 * SimpleShips
 * Copyright (c) 2026, Jere McDevitt
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.joml.Vector3f;

import static simpleships.SimpleShipsPlugin.LOG;

/**
 * This allows for regular blocks, such as {@link:org.bukkit.block.data.type.Light} blocks
 * (or any other direct blocks) to be part of the ship and moved.  Unlike the {@link:org.bukkit.entity.BlockDisplay}
 * block entities, a normal block can not move in partial block increments, they must
 * go on block boundaries.
 *
 * The initial use of this was for the hidden light blocks that are created associated
 * with any light source on the ships.
 */
public class MovingBlock {
	private Location location = null;
	private final Vector3f offset;
	private final BlockData data;
	private final Material blockType;
	private final float yawAtAssemble;

	public MovingBlock(Location helm, Vector3f offset, BlockData data, Material blockType, float yawAtAssemble) {
		location = helm.clone();  
		this.offset = new Vector3f(offset);
		this.data = data.clone();
		this.blockType = blockType;
		this.yawAtAssemble = yawAtAssemble;
		moveTo(helm, yawAtAssemble);
	}
	void moveTo(Location l, float shipYaw) {
		float deltaYaw = UtilFuncs.wrapDegrees(shipYaw - yawAtAssemble);
		
		float yaw = (float)Math.toRadians(deltaYaw);
		float x = offset.x * (float)Math.cos(yaw) - offset.z * (float)Math.sin(yaw);
		float z = offset.x * (float)Math.sin(yaw) + offset.z * (float)Math.cos(yaw);

		location = l.clone().add(x, offset.y, z);
		// LOG(10,"MovingBlock: %s Offset(%f,%f,%f), Loc(%f,%f,%f), L(%f,%f,%f), shipYaw: %f, yaw: %f, x: %f, z: %f",
		// 		blockType.toString(),
		// 		offset.x, offset.y, offset.z, 
		// 		location.getX(), location.getY(), location.getZ(),
		// 		l.getX(), l.getY(), l.getZ(),
		// 		shipYaw, yaw, x, z);
		
	}
	void render() {
		location.getBlock().setBlockData(data);
	}

	void remove() {
		Block block = location.getBlock();
		if(block.getType() == this.blockType ) 
			location.getBlock().setType(Material.AIR);
	}

}
