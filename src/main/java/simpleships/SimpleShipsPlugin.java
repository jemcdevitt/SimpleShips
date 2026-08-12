package simpleships;
/*
 * SimpleShips
 * Copyright (c) 2026, Jere McDevitt
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * The primary plugin.  Currently there are no permission
 * checks in place and all of the commands are available to
 * any player.
 */
public class SimpleShipsPlugin extends JavaPlugin {
	private static Logger logger;
	public static Configuration configuration;
	private HelmListener helmListener;
	private EntityManager entityManager;
	private EntityPadManager entityPadManager;
	private PassengerSeatManager passengerSeatManager;
	private ParrotPerchManager parrotPerchManager;
	

	@Override
	public void onEnable() {
		logger = getLogger();

		saveDefaultConfig();
		loadConfiguration();

		registerRecipes();
		helmListener = new HelmListener(this);
		entityManager = new EntityManager(this, helmListener);

		entityPadManager = new EntityPadManager(this);
		getServer().getPluginManager().registerEvents(entityPadManager, this);

		passengerSeatManager = new PassengerSeatManager(this);
		getServer().getPluginManager().registerEvents(passengerSeatManager, this);

		parrotPerchManager = new ParrotPerchManager(this);
		getServer().getPluginManager().registerEvents(parrotPerchManager, this);
		
		getServer().getPluginManager().registerEvents(helmListener, this);
		getServer().getPluginManager().registerEvents(entityManager, this);
		helmListener.rehydrateHelms();
		entityManager.rehydrateEntities();

		new BukkitRunnable() {
			@Override
			public void run() {
				SimpleShipsPlugin.this.updateShips();
			}
		}.runTaskTimer(this, 0, Constants.UPDATE_TICKS);
		
		LOG(0,"Simple Ships  plugin startup");
	}

	@Override
	public void onDisable() {
		if( helmListener != null )
			helmListener.onDisable();
		LOG(0,"Simple Ships plugin shutdown");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String commandName = command.getName().toLowerCase();
		
    if(!(sender instanceof Player player)) {
			sender.sendMessage("This command can only be run by a player");
			return true;
		}

		if(commandName.equalsIgnoreCase("cleanup")) {
			helmListener.flushAll();
			entityManager.flushAll(player.getWorld());
			entityManager.removeAllComponents();
		}

		if(commandName.equalsIgnoreCase("helm")) {
			SimpleShipsPlugin.giveHelmToPlayer(player);
		}

		if( commandName.equalsIgnoreCase("flush") ) {
			helmListener.flushAll();
			entityManager.flushAll(player.getWorld());
		}
	
		if( commandName.equalsIgnoreCase("pad") ) {
			EntityPadManager.givePadToPlayer(player);
		}

		if( commandName.equalsIgnoreCase("seat")) {
			PassengerSeatManager.giveSeatToPlayer(player);
		}
	
		if( commandName.equalsIgnoreCase("perch")) {
			ParrotPerchManager.givePerchToPlayer(player);
		}
	
		return true;
	}

	@Override
	public String namespace() {
		return Constants.NAME_SPACE;
	}

	public void updateShips() {
		if(helmListener != null )
			helmListener.doUpdate();
	}

	static public void giveHelmToPlayer(Player player) {
		ItemStack itemStack = HelmSeat.createShipHelmItemStack();
		if( itemStack != null )
			player.getInventory().addItem(itemStack);
	}
	
	
	public void registerRecipes() {
		registerShipHelmRecipe();
		
	}
	private void registerShipHelmRecipe() {
		Bukkit.removeRecipe(Constants.SHIP_HELM_RECIPE_KEY);
		Bukkit.removeRecipe(Constants.ENTITY_PAD_RECIPE_KEY);

		ItemStack helmStack = HelmSeat.createShipHelmItemStack();
		if( helmStack == null ) {
			logger.severe("Failed to create the helm recipe");
		} else {
			ShapedRecipe helmRecipe = new ShapedRecipe(Constants.SHIP_HELM_RECIPE_KEY, helmStack);
			helmRecipe.shape(".S.",
											 ".F.",
											 ".I."
											 );
			helmRecipe.setIngredient('S', new RecipeChoice.MaterialChoice(Tag.WOODEN_TRAPDOORS));
			helmRecipe.setIngredient('F', new RecipeChoice.MaterialChoice(Tag.WOODEN_FENCES));
			helmRecipe.setIngredient('I', Material.IRON_INGOT);
			Bukkit.addRecipe(helmRecipe);
		}

		ItemStack entityPad = EntityPadManager.createEntityPadItemStack();
		if( entityPad == null ) {
			logger.severe("Failed to create the entity pad recipe");
		} else {
			ShapedRecipe entityPadRecipe = new ShapedRecipe(Constants.ENTITY_PAD_RECIPE_KEY, entityPad);
			entityPadRecipe.shape(".H.",
														".F.",
														".I.");
			entityPadRecipe.setIngredient('H', Material.HAY_BLOCK);
			entityPadRecipe.setIngredient('F', new RecipeChoice.MaterialChoice(Tag.WOODEN_FENCES));
			entityPadRecipe.setIngredient('I', Material.IRON_INGOT);
			Bukkit.addRecipe(entityPadRecipe);
		}
		
		ItemStack passengerSeat = PassengerSeatManager.createPassengerSeatItemStack();
		if( passengerSeat == null ) {
			logger.severe("Failed to create the passenger seat recipe");
		} else {
			ShapedRecipe passengerSeatRecipe = new ShapedRecipe(Constants.PASSENGER_SEAT_RECIPE_KEY, passengerSeat);
			passengerSeatRecipe.shape(".Y.",
														".S.",
														".I.");
			passengerSeatRecipe.setIngredient('Y', new RecipeChoice.MaterialChoice(Tag.WOOL_CARPETS));
			passengerSeatRecipe.setIngredient('S', new RecipeChoice.MaterialChoice(Tag.WOODEN_STAIRS));
			passengerSeatRecipe.setIngredient('I', Material.IRON_INGOT);
			Bukkit.addRecipe(passengerSeatRecipe);
		}

		ItemStack parrotPerch = ParrotPerchManager.createParrotPerchItemStack();
		if( parrotPerch == null) {
			logger.severe("Failed to create the parrot perch recipe");
		} else {
			ShapedRecipe parrotPerchRecipe = new ShapedRecipe(Constants.PARROT_PERCH_RECIPE_KEY, parrotPerch);
			parrotPerchRecipe.shape("SSS",
															".S.",
															".S.");
			parrotPerchRecipe.setIngredient('S',Material.STICK);
			Bukkit.addRecipe(parrotPerchRecipe);
		}
	}

	static public void LOG(int level, String msg, Object... args) {
		try {
			if( level == 0 || level == 10) {
				if( configuration.debugOn || level == 10)
					logger.info(String.format(msg, args));
			}	else {
				logger.warning(String.format(msg, args));
			}
		} catch(Exception ex) {
			logger.log(Level.SEVERE, "Exception generating log message", ex);
		}
	}
	
	static public void LOG(int level, Player player, String msg, Object... args) {
		try {
			String toSend = String.format(msg, args);
			if( level == 0 || level == 10 ) {
				if( configuration.debugOn || level == 10) {
					logger.info(toSend);
					player.sendMessage(toSend);
				}
			} else {
				logger.warning(toSend);
				player.sendMessage(toSend);
			}

		} catch(Exception ex) {
			logger.log(Level.SEVERE, "Exception generating log message", ex);
			player.sendMessage("Exception writing log to player: " + ex.getMessage());

		}
	}

	private void loadConfiguration() {
		FileConfiguration cfg = getConfig();
		configuration = new Configuration();
		
		if( cfg == null ) {
			logger.severe("Configuration should not be null");
		} else {

			configuration.debugOn = cfg.getBoolean("debug", false);
		
			configuration.maxBlocks = cfg.getConfigurationSection("ship-size").getInt("max-ship-blocks", Configuration.MAX_SHIP_BLOCKS_DEFAULT);
			configuration.maxXWidth = cfg.getConfigurationSection("ship-size").getInt("max-ship-x-width", Configuration.MAX_SHIP_X_WIDTH_DEFAULT);
			configuration.maxZWidth = cfg.getConfigurationSection("ship-size").getInt("max-ship-z-width", Configuration.MAX_SHIP_Z_WIDTH_DEFAULT);
			configuration.maxHeight = cfg.getConfigurationSection("ship-size").getInt("max-ship-height", Configuration.MAX_SHIP_HEIGHT_DEFAULT);
			BlockSupport.initAllowedBlocksFromConfig(cfg);
		}	
		configuration.showInfo(logger);
	}

}
