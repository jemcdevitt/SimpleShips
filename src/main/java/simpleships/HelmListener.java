package simpleships;
/*
 * SimpleShips
 * Copyright (c) 2026, Jere McDevitt
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Input;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInputEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static simpleships.SimpleShipsPlugin.LOG;

/**
 * The HelmListener is responsible for most of the management
 * of a ship's helm and for the player input and events.
 */
public class HelmListener implements Listener {
	static Map<UUID, Ship>     playerToShip = new HashMap<>();
	static Map<String, Player> mountedPlayers = new HashMap<>();
	static Map<String, Ship>   shipById = new HashMap<>();
	final SimpleShipsPlugin plugin;

	public HelmListener(SimpleShipsPlugin plugin) {
		this.plugin = plugin;
	}
	

	public void flushAll() {
		LOG(0,"Flushing all");
		for(Ship ship : shipById.values()) {
			ship.forceUnmount();
		}
		playerToShip.clear();
		mountedPlayers.clear();
		shipById.clear();
	}
		
	public void onDisable() {
		flushAll();
	}

	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt) {
		Player player = evt.getPlayer();
		unmountPlayer(player, true);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent evt) {
		LOG(0,"Player died, restoring ship ");
		Player player = evt.getPlayer();
		if( player == null )
			return;
		
		Ship ship = playerToShip.get(player.getUniqueId());
		if( ship == null )
			return;

		Bukkit.getScheduler().runTaskLater(plugin, () -> {
				ship.forceUnmount();
				LOG(0,"Unmounting player");
			}, 5L);
	}

	static public void onPlayerPlaceEntity(PlayerInteractEvent event, ItemStack itemStack) {
		Player player = event.getPlayer();
		
		Block block = event.getClickedBlock();
		if( block == null )
			return;


		if( !BlockSupport.isBlockAllowedForHelm(block.getType())) {
			LOG(0, player, "Helm must be placed on an allowed block");
			return;
		}
		if( BlockSupport.isLowerSlab(block)) {
			LOG(0, player, "Helm can not be placed on a lower slab");
			return;
		}
			
		
		Ship ship = new Ship(player.getFacing(), block.getLocation());
		shipById.put(ship.getUniqueIdStr(), ship);

		if(player.getGameMode() != GameMode.CREATIVE) {
			itemStack.subtract(1);
		}
	}


	/**
	 * Locate existing armor stands when the world or a chunk is loaded
	 */
	public void rehydrateHelms() {
		for(World world : Bukkit.getWorlds()) {
			for(ArmorStand stand : world.getEntitiesByClass(ArmorStand.class)) {
				checkIfIsHelmStand(stand, "startup");
			}
		}
	}

	static public void onEntityClicked(Player player, ArmorStand stand, PlayerInteractAtEntityEvent event) {

		//sneak right clicking on a helm results in ship materializing but player not
		//actually mounting.  This should prevent that
		if( player.isSneaking()) {
			event.setCancelled(true);
			return;
		}
		
		String shipHelmId = stand.getPersistentDataContainer().get(Constants.SHIP_HELM_ID_KEY, PersistentDataType.STRING);
		if( shipHelmId == null ) {
			return;
		}

		Ship ship = shipById.get(shipHelmId);
		Location standLoc = stand.getLocation().clone();
		if(ship == null ) {
			LOG(1,player,"Problem with helm, no ship associated with it.  Pickup and replace");
			return;
		}
		
		ship.mount(player);
		mountedPlayers.put(ship.getUniqueIdStr(), player);
		playerToShip.put(player.getUniqueId(), ship);
	}

	static public void onEntityHit(Player player, ArmorStand stand, EntityDamageByEntityEvent event) {

		if( stand.getPersistentDataContainer().has(Constants.SHIP_HELM_ID_KEY)) {
			event.setCancelled(true);

			if( player.isSneaking() ) {
				String shipId = stand.getPersistentDataContainer().get(Constants.SHIP_HELM_ID_KEY, PersistentDataType.STRING);
				Ship ship = shipById.get(shipId);
				if( ship != null && ship.isPiloted()) {
					LOG(0, player, "Helm must be dismounted before picking it up.");
					return;
				}
				if( ship != null ) {
					ship.removeHelm();
					shipById.remove(shipId);
					if( player.getGameMode() != GameMode.CREATIVE) {
						SimpleShipsPlugin.giveHelmToPlayer(player);
					}
				} else {
					removeLooseHelm(stand, shipId);
				}
			}
		}
	}
	
	public void onChunkLoad(ArmorStand stand) {
		checkIfIsHelmStand(stand, "chunkLoad");
	}

	private void checkIfIsHelmStand(ArmorStand stand, String fromWhere) {
		String type = stand.getPersistentDataContainer().get(Constants.ITEM_TYPE_KEY, PersistentDataType.STRING);
		if(!Constants.SHIP_HELM_ITEM_TYPE.equals(type))
			return;

		String id = stand.getPersistentDataContainer().get(Constants.SHIP_HELM_ID_KEY, PersistentDataType.STRING);
		if(id == null)
			return;

		// TODO consider using a factory to rehydrate to do additional checks
		Ship ship = new Ship(stand);
		shipById.put(id, ship);
	}
	
	@EventHandler
	public void onPlayerInput(PlayerInputEvent event) {
		Input input = event.getInput();
		Player player = event.getPlayer();
		UUID playerId = player.getUniqueId();
		
		Ship ship = playerToShip.get(playerId);
		if( ship == null )
			return;

		if( !ship.isPlayerPilot(player))
			return;
		
		if(input.isSneak()) {
			ship.unmount(player);
		} else {
			if(input.isForward() ) {
				ship.moveForward(true);
			} else {
				ship.moveForward(false);
			}
			if( input.isBackward() ) {
				ship.moveBackward(true);
			} else {
				ship.moveBackward(false);
			}
			if( input.isLeft() ) {
				ship.turnLeft(true);
			} else {
				ship.turnLeft(false);
			}
			if( input.isRight() ) {
				ship.turnRight(true);
			} else {
				ship.turnRight(false);
			}
			if( input.isSprint() ) {
				ship.align();
			}
			if(input.isJump() ) {
				ship.autoMove();
			}
		}
	}


	public void doUpdate() {
		for(Ship ship : shipById.values()) {
			if( ship.isPiloted()) {
				ship.doUpdate();
			}
		}
	}
	private static void removeLooseHelm(ArmorStand stand, String shipHelmId) {
		Location loc = stand.getLocation().clone().add(-0.5f, 0.5f, -0.5f);
		float yaw = loc.getYaw();
		
		for(BlockDisplay display : loc.getWorld().getEntitiesByClass(BlockDisplay.class)) {
			if(display.getLocation().distanceSquared(loc) >= 4)
				break;
			String seatHelmId = stand.getPersistentDataContainer().get(Constants.SHIP_HELM_ID_KEY, PersistentDataType.STRING);
			if(shipHelmId.equals(seatHelmId)) {
				display.remove();
			}
		}
		
		stand.remove();
	}

	static public boolean isShipHelm(ArmorStand stand) {
		if(stand.getPersistentDataContainer().has(Constants.SHIP_HELM_ID_KEY))
			return true;
		return false;
	}

	private void unmountPlayer(Player player, boolean forceShipRestore) {
		UUID playerId = player.getUniqueId();
		Ship ship = playerToShip.get(playerId);
		if( ship == null ) {
			return;
		}

		if( ship.isPlayerPilot(player) ) {
			if( forceShipRestore ) 
				ship.forceUnmount();
			else
				ship.unmount(player);
		}
	}

}
