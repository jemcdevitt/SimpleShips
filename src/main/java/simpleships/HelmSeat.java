package simpleships;
/*
 * SimpleShips
 * Copyright (c) 2026, Jere McDevitt
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * A HelmSeat is the visual display for the helm, consisting
 * of a spruce fence as the post, and a spruce trapdoor as the
 * seat.
 */
public class HelmSeat {
	BlockDisplay seat;
	BlockDisplay post;

	private HelmSeat(BlockDisplay seat, BlockDisplay post) {
		this.seat = seat;
		this.post = post;
	}
	
	public HelmSeat(Location blockLocation, String helmId) {
		World world = blockLocation.getWorld();
		BlockData seatBlockData = Bukkit.createBlockData(Material.SPRUCE_TRAPDOOR);

		Matrix4f transform = new Matrix4f().identity().translate(-0.5f, 0.90f, -0.25f).scale(1, 1, 0.5f);

		
		seat = world.spawn(blockLocation.clone(), BlockDisplay.class, entity -> {
				entity.setBlock(seatBlockData);
				entity.setPersistent(true);
				entity.setTransformationMatrix(new Matrix4f(transform));
				entity.setGravity(false);
				entity.setInterpolationDuration(SimpleShipsPlugin.configuration.lerpDuration);
				entity.setInterpolationDelay(-1);
				entity.setTeleportDuration(0);
			});
		seat.teleport(blockLocation);
		seat.getPersistentDataContainer().set(Constants.ITEM_TYPE_KEY, PersistentDataType.STRING, Constants.SHIP_HELM_SEAT_TYPE);
		seat.getPersistentDataContainer().set(Constants.SHIP_HELM_ID_KEY, PersistentDataType.STRING, helmId);
		Constants.markShipComponent(seat);

		
		BlockData postBlockData = Bukkit.createBlockData(Material.SPRUCE_FENCE);
		transform.identity().translate(-0.25f, 0.40f, -0.25f).scale(0.5f, 0.5f, 0.5f);

		post = world.spawn(blockLocation.clone(), BlockDisplay.class, entity -> {
				entity.setBlock(postBlockData);
				entity.setPersistent(true);
				entity.setTransformationMatrix(new Matrix4f(transform));
				entity.setGravity(false);
				entity.setInterpolationDuration(SimpleShipsPlugin.configuration.lerpDuration);
				entity.setInterpolationDelay(-1);
				entity.setTeleportDuration(0);
			});
		post.teleport(blockLocation);
		Constants.markShipComponent(post);

		post.getPersistentDataContainer().set(Constants.ITEM_TYPE_KEY, PersistentDataType.STRING, Constants.SHIP_HELM_POST_TYPE);
		post.getPersistentDataContainer().set(Constants.SHIP_HELM_ID_KEY, PersistentDataType.STRING, helmId);
		
		seat.setTeleportDuration(SimpleShipsPlugin.configuration.teleportDuration);
		post.setTeleportDuration(SimpleShipsPlugin.configuration.teleportDuration);

	}

	public void remove() {
		seat.remove();
		post.remove();
	}

	public void teleport(Location location) {
		seat.teleport(location);
		post.teleport(location);
	}

	public static HelmSeat fromExisting(BlockDisplay seat, BlockDisplay post) {
		if(seat == null || post == null ) {
			return null;
		}
		
		seat.setTeleportDuration(SimpleShipsPlugin.configuration.teleportDuration);
		post.setTeleportDuration(SimpleShipsPlugin.configuration.teleportDuration);

		return new HelmSeat(seat, post);
	}

	public static ItemStack createShipHelmItemStack() {
		ItemStack item = ItemStack.of(Material.ARMOR_STAND);
		ItemMeta meta = item.getItemMeta();

		if( meta == null ) {
			return null;
		}

		meta.displayName(Component.text(Constants.getStringFor(Constants.SHIP_HELM_ITEM_TYPE)));
		meta.getPersistentDataContainer().set(Constants.ITEM_TYPE_KEY, PersistentDataType.STRING, Constants.SHIP_HELM_ITEM_TYPE);
		item.setItemMeta(meta);
		return item;
	}
	
	

	public static HelmSeat findForHelm(Location helmLocation, String helmId) {
		BlockDisplay foundSeat = null;
		BlockDisplay foundPost = null;

		for(BlockDisplay display : helmLocation.getWorld().getEntitiesByClass(BlockDisplay.class)) {
			String type = display.getPersistentDataContainer().get(Constants.ITEM_TYPE_KEY, PersistentDataType.STRING);
			String displayHelmId = display.getPersistentDataContainer().get(Constants.SHIP_HELM_ID_KEY, PersistentDataType.STRING);
			if(!helmId.equals(displayHelmId))
				continue;

			if(Constants.SHIP_HELM_SEAT_TYPE.equals(type)) {
				foundSeat = display;
			} else if( Constants.SHIP_HELM_POST_TYPE.equals(type)) {
				foundPost = display;
			}
			if( foundSeat != null && foundPost != null )
				break;
		}
		return fromExisting(foundSeat, foundPost);
	}

	public static boolean isHelmSeat(ArmorStand stand) {
		return Constants.SHIP_HELM_ITEM_TYPE.equals(stand.getPersistentDataContainer().get(Constants.ITEM_TYPE_KEY, PersistentDataType.STRING));
	}
	public static boolean isHelmSeat(String type) {
		return Constants.SHIP_HELM_ITEM_TYPE.equals(type);
	}
}
