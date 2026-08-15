package simpleships;
/*
 * SimpleShips
 * Copyright (c) 2026, Jere McDevitt
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import static simpleships.SimpleShipsPlugin.LOG;

public class BlockSupport {
	private static Set<Material> allowedBlocks = null;


	//limited set of blocks on which a helm can be placed
	private static Set<Material> helmSupportBlocks = new HashSet<>()
																							 {{
																									 addAll(Tag.BAMBOO_BLOCKS.getValues());
																									 addAll(Tag.LOGS.getValues());
																									 addAll(Tag.PLANKS.getValues());
																									 addAll(Tag.WOODEN_STAIRS.getValues());
																									 addAll(Tag.TERRACOTTA.getValues());
																									 addAll(Tag.WOODEN_SLABS.getValues());  //only the top slab
																									 addAll(Tag.WOODEN_TRAPDOORS.getValues());
																									 addAll(Tag.WOOL.getValues());
																											 
																								 }};


	
	private static Set<Material> glassPanes = new HashSet<>()
																						{{
																								add(Material.BLACK_STAINED_GLASS_PANE);
																								add(Material.BLUE_STAINED_GLASS_PANE);
																								add(Material.BROWN_STAINED_GLASS_PANE);
																								add(Material.CYAN_STAINED_GLASS_PANE);
																								add(Material.GRAY_STAINED_GLASS_PANE);
																								add(Material.GREEN_STAINED_GLASS_PANE);
																								add(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
																								add(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
																								add(Material.LIME_STAINED_GLASS_PANE);
																								add(Material.MAGENTA_STAINED_GLASS_PANE);
																								add(Material.ORANGE_STAINED_GLASS_PANE);
																								add(Material.PINK_STAINED_GLASS_PANE);
																								add(Material.PURPLE_STAINED_GLASS_PANE);
																								add(Material.RED_STAINED_GLASS_PANE);
																								add(Material.WHITE_STAINED_GLASS_PANE);
																								add(Material.YELLOW_STAINED_GLASS_PANE);
																							}};

	private static Set<Material> glass = new HashSet<>()
																			 {{
																					 add(Material.BLACK_STAINED_GLASS);
																					 add(Material.BLUE_STAINED_GLASS);
																					 add(Material.BROWN_STAINED_GLASS);
																					 add(Material.CYAN_STAINED_GLASS);
																					 add(Material.GRAY_STAINED_GLASS);
																					 add(Material.GREEN_STAINED_GLASS);
																					 add(Material.LIGHT_BLUE_STAINED_GLASS);
																					 add(Material.LIGHT_GRAY_STAINED_GLASS);
																					 add(Material.LIME_STAINED_GLASS);
																					 add(Material.MAGENTA_STAINED_GLASS);
																					 add(Material.ORANGE_STAINED_GLASS);
																					 add(Material.PINK_STAINED_GLASS);
																					 add(Material.PURPLE_STAINED_GLASS);
																					 add(Material.RED_STAINED_GLASS);
																					 add(Material.WHITE_STAINED_GLASS);
																					 add(Material.YELLOW_STAINED_GLASS);
																				 }};
	private static Set<Material> concrete = new HashSet<>()
																					{{
																					 add(Material.BLACK_CONCRETE);
																					 add(Material.BLUE_CONCRETE);
																					 add(Material.BROWN_CONCRETE);
																					 add(Material.CYAN_CONCRETE);
																					 add(Material.GRAY_CONCRETE);
																					 add(Material.GREEN_CONCRETE);
																					 add(Material.LIGHT_BLUE_CONCRETE);
																					 add(Material.LIGHT_GRAY_CONCRETE);
																					 add(Material.LIME_CONCRETE);
																					 add(Material.MAGENTA_CONCRETE);
																					 add(Material.ORANGE_CONCRETE);
																					 add(Material.PINK_CONCRETE);
																					 add(Material.PURPLE_CONCRETE);
																					 add(Material.RED_CONCRETE);
																					 add(Material.WHITE_CONCRETE);
																					 add(Material.YELLOW_CONCRETE);
																						}};

	private static Set<Material> pottedPlants = new HashSet<>()
																							{{
																									add(Material.POTTED_ACACIA_SAPLING);
																									add(Material.POTTED_ALLIUM);
																									add(Material.POTTED_AZALEA_BUSH);
																									add(Material.POTTED_AZURE_BLUET);
																									add(Material.POTTED_BAMBOO);
																									add(Material.POTTED_BIRCH_SAPLING);
																									add(Material.POTTED_BLUE_ORCHID);
																									add(Material.POTTED_BROWN_MUSHROOM);
																									add(Material.POTTED_CACTUS);
																									add(Material.POTTED_CHERRY_SAPLING);
																									add(Material.POTTED_CLOSED_EYEBLOSSOM);
																									add(Material.POTTED_CORNFLOWER);
																									add(Material.POTTED_CRIMSON_FUNGUS);
																									add(Material.POTTED_CRIMSON_ROOTS);
																									add(Material.POTTED_DANDELION);
																									add(Material.POTTED_DARK_OAK_SAPLING);
																									add(Material.POTTED_DEAD_BUSH);
																									add(Material.POTTED_FERN);
																									add(Material.POTTED_FLOWERING_AZALEA_BUSH);
																									add(Material.POTTED_GOLDEN_DANDELION);  
																									add(Material.POTTED_JUNGLE_SAPLING);
																									add(Material.POTTED_LILY_OF_THE_VALLEY);
																									add(Material.POTTED_MANGROVE_PROPAGULE);
																									add(Material.POTTED_OAK_SAPLING);
																									add(Material.POTTED_OPEN_EYEBLOSSOM);
																									add(Material.POTTED_ORANGE_TULIP);
																									add(Material.POTTED_OXEYE_DAISY);
																									add(Material.POTTED_PALE_OAK_SAPLING);
																									add(Material.POTTED_PINK_TULIP);
																									add(Material.POTTED_POPPY);
																									add(Material.POTTED_RED_MUSHROOM);
																									add(Material.POTTED_RED_TULIP);
																									add(Material.POTTED_SPRUCE_SAPLING);
																									add(Material.POTTED_TORCHFLOWER);
																									add(Material.POTTED_WARPED_FUNGUS);
																									add(Material.POTTED_WARPED_ROOTS);
																									add(Material.POTTED_WHITE_TULIP);
																									add(Material.POTTED_WITHER_ROSE);
																								}};


	public static final void initAllowedBlocksFromConfig(FileConfiguration cfg) {
		if( allowedBlocks != null ) {
			LOG(0,"Block list already initiatlized");
			return;
		}
		allowedBlocks = new HashSet<>();
		List<String> materialNames = cfg.getStringList("allowed-blocks");

		if(!materialNames.isEmpty()) {
			for(String name : materialNames) {
				if( name.startsWith("#") ) {
					LOG(0,"Loading tag %s", name);
					Set<Material> tagMaterials = getTagMaterials(name);
					if( tagMaterials == null) {
						LOG(1,"Didn't find the tag for %s", name);
						continue;
					}
					for(Material tagMat : tagMaterials ) {
						if( !tagMat.isBlock() || tagMat.isAir()) {
							continue;
						}
						LOG(0,"Adding %s to allowed blocks list", tagMat);
						allowedBlocks.add(tagMat);
					}
				} else {
					Material mat = Material.matchMaterial(name);
					if( mat == null ) {
						LOG(1,"Didn't find material for %s", name);
					} else {
						LOG(0,"Adding %s to allowed blocks list", mat);
						allowedBlocks.add(mat);
					}
				}
			}
		}
		LOG(10, "Loaded %d allowed blocks", allowedBlocks.size());
	}

	public static final boolean isBlockAllowedForHelm(Material mat) {
		if( mat == null )
			return false;
		if(!isBlockAllowed(mat))
			return false;

		return helmSupportBlocks.contains(mat) ||
			glass.contains(mat) ||
			concrete.contains(mat);
	}
	public static final boolean isBlockAllowed(Block block) {
		if( block == null )
			return false;
		return isBlockAllowed(block.getType());
	}
	public static final boolean isBlockAllowed(Material mat) {
		return
			allowedBlocks.contains(mat);
	}

	//apparently there isn't a Tag.WALL_BANNERS
	public static final boolean isWallBanner(Material mat) {
		return
			mat == Material.WHITE_WALL_BANNER ||
			mat == Material.ORANGE_WALL_BANNER ||
			mat == Material.MAGENTA_WALL_BANNER ||
			mat == Material.LIGHT_BLUE_WALL_BANNER ||
			mat == Material.YELLOW_WALL_BANNER ||
			mat == Material.LIME_WALL_BANNER ||
			mat == Material.PINK_WALL_BANNER ||
			mat == Material.GRAY_WALL_BANNER ||
			mat == Material.LIGHT_GRAY_WALL_BANNER ||
			mat == Material.CYAN_WALL_BANNER ||
			mat == Material.PURPLE_WALL_BANNER ||
			mat == Material.BLUE_WALL_BANNER ||
			mat == Material.BROWN_WALL_BANNER ||
			mat == Material.GREEN_WALL_BANNER ||
			mat == Material.RED_WALL_BANNER ||
			mat == Material.BLACK_WALL_BANNER;
	}

	public static final boolean isHead(Material mat) {
		return
			isWallHead(mat) ||
			mat == Material.SKELETON_SKULL ||
			mat == Material.ZOMBIE_HEAD ||
			mat == Material.WITHER_SKELETON_SKULL ||
			mat == Material.PLAYER_HEAD ||
			mat == Material.CREEPER_HEAD ||
			mat == Material.DRAGON_HEAD ||
			mat == Material.PIGLIN_HEAD;
	}
	public static final boolean isWallHead(Material mat) {
		return
			mat == Material.SKELETON_WALL_SKULL ||
			mat == Material.ZOMBIE_WALL_HEAD ||
			mat == Material.WITHER_SKELETON_WALL_SKULL ||
			mat == Material.PLAYER_WALL_HEAD ||
			mat == Material.CREEPER_WALL_HEAD ||
			mat == Material.DRAGON_WALL_HEAD ||
			mat == Material.PIGLIN_WALL_HEAD;
	}
	public static final boolean isWallSign(Material mat) {
		return
			mat == Material.OAK_WALL_SIGN ||
			mat == Material.SPRUCE_WALL_SIGN ||
			mat == Material.JUNGLE_WALL_SIGN ||
			mat == Material.BIRCH_WALL_SIGN ||
			mat == Material.ACACIA_WALL_SIGN ||
			mat == Material.CHERRY_WALL_SIGN ||
			mat == Material.DARK_OAK_WALL_SIGN ||
			mat == Material.PALE_OAK_WALL_SIGN ||
			mat == Material.MANGROVE_WALL_SIGN ||
			mat == Material.BAMBOO_WALL_SIGN ||
			mat == Material.CRIMSON_WALL_SIGN ||
			mat == Material.WARPED_WALL_SIGN;
	}
	public static final boolean isShelf(Material mat) {
		return Tag.WOODEN_SHELVES.isTagged(mat);
	}

	public static final Material getPlankForSign(Material sign) {
		return switch(sign) {
			case OAK_WALL_SIGN -> Material.OAK_PLANKS;
			case SPRUCE_WALL_SIGN -> Material.SPRUCE_PLANKS;
			case JUNGLE_WALL_SIGN -> Material.JUNGLE_PLANKS;
			case BIRCH_WALL_SIGN -> Material.BIRCH_PLANKS;
			case ACACIA_WALL_SIGN -> Material.ACACIA_PLANKS;
			case CHERRY_WALL_SIGN -> Material.CHERRY_PLANKS;
			case DARK_OAK_WALL_SIGN -> Material.DARK_OAK_PLANKS;
			case PALE_OAK_WALL_SIGN -> Material.PALE_OAK_PLANKS;
			case MANGROVE_WALL_SIGN -> Material.MANGROVE_PLANKS;
			case BAMBOO_WALL_SIGN -> Material.BAMBOO_PLANKS;
			case CRIMSON_WALL_SIGN -> Material.CRIMSON_PLANKS;
			case WARPED_WALL_SIGN -> Material.WARPED_PLANKS;
			default -> sign;
		};
	}

	public static final ItemStack[] cloneContents(ItemStack[] contents) {
		ItemStack[] copy = new ItemStack[contents.length];
		for(int i = 0; i < contents.length; i++) {
			copy[i] = contents[i] == null ? null : contents[i].clone();
		}
		return copy;
	}

	public static final boolean canPassThru(Block block ) {
		Material type = block.getType();

		if(type.isAir() ||
			 type == Material.WATER ||
			 type == Material.BUBBLE_COLUMN ||
			 type == Material.LILY_PAD ||
			 type == Material.KELP_PLANT ||
			 block.isPassable())
			return true;
		return false;
	}
			
	static public final boolean isLowerSlab(Block block) {
		if( block.getBlockData() instanceof Slab slab) {
			if( slab.getType() == Slab.Type.BOTTOM)
				return true;
		}
		return false;
	}


	static private Set<Material> getTagMaterials(String arg) {
		String tagName = arg.substring(1).toLowerCase(Locale.ROOT);
		Tag<Material> tag = Bukkit.getTag(Tag.REGISTRY_BLOCKS, NamespacedKey.minecraft(tagName), Material.class);
		if( tag == null ) {
			LOG(1, "Unknown material tag: %s", tag);
			return null;
		}
		return tag.getValues();
	}
}
