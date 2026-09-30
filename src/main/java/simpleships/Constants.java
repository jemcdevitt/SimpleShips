package simpleships;
/*
 * SimpleShips
 * Copyright (c) 2026, Jere McDevitt
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class Constants {
	static public final float ONE_64 = 1.0f/64.0f;
//	static public final int BD_TELEPORT_DURATION  = 3;
//	static public final int BD_LERP_DURATION      = 0;
//	static public final int UPDATE_TICKS          = 1;
//	static public final float SHIP_SPEED          = 0.25f;
//	static public final float SHIP_REVERSE_SPEED  = 0.125f;
	
	static public final String NAME_SPACE = "simpleships";
	

	static public final NamespacedKey ITEM_TYPE_KEY = new NamespacedKey(NAME_SPACE,"item_type");
	static public final NamespacedKey SHIP_COMPONENT_KEY = new NamespacedKey(NAME_SPACE,"simple_ships_component_key");

	@Deprecated 
	static public final String SHIP_COMPONENT_ITEM_TYPE = "simple_ships_component";
	
	static public final NamespacedKey SHIP_HELM_ID_KEY = new NamespacedKey(NAME_SPACE, "ship_helm_id_key");
	static public final NamespacedKey SHIP_HELM_RECIPE_KEY = new NamespacedKey(NAME_SPACE, "ship_helm_recipe");
	static public final String SHIP_HELM_ITEM_TYPE = "ship_helm";
	static public final String SHIP_HELM_SEAT_TYPE = "ship_helm_seat";
	static public final String SHIP_HELM_POST_TYPE = "ship_helm_post";

	
	static public final String ENTITY_PAD_ITEM_TYPE = "entity_pad_item";
	static public final NamespacedKey ENTITY_PAD_ID_KEY = new NamespacedKey(NAME_SPACE, "entity_pad_item_key");
	static public final NamespacedKey ENTITY_PAD_RECIPE_KEY = new NamespacedKey(NAME_SPACE, "entity_pad_recipe");
	static public final String ENTITY_PAD_ID = "entity_pad_id";

	static public final String PARROT_PERCH_ITEM_TYPE = "parrot_perch_item";
	static public final String PARROT_PERCH_POST_ITEM_TYPE = "parrot_perch_post_item";
	static public final String PARROT_PERCH_CROSSBAR_ITEM_TYPE = "parrot_perch_crossbar_item";
	static public final NamespacedKey PARROT_PERCH_ID_KEY = new NamespacedKey(NAME_SPACE, "parrot_perch_item_key");
	static public final NamespacedKey PARROT_PERCH_RECIPE_KEY = new NamespacedKey(NAME_SPACE, "parrot_perch_recipe");
	static public final String PARROT_PERCH_ID = "parrot_perch_id";
	

	static public final String PASSENGER_SEAT_ITEM_TYPE = "passenger_seat_item";
	static public final String PASSENGER_SEAT_CUSHION_ITEM_TYPE = "passenger_seat_cushion_item";
	static public final NamespacedKey PASSENGER_SEAT_ID_KEY = new NamespacedKey(NAME_SPACE, "passenger_seat_item_key");
	static public final NamespacedKey PASSENGER_SEAT_RECIPE_KEY = new NamespacedKey(NAME_SPACE, "passenger_seat_recipe");



	///reworking to fit the new model
	static public final NamespacedKey SIMPLE_SHIPS_COMPONENT = new NamespacedKey(NAME_SPACE, "simple_ships_component");
	static public final NamespacedKey SS_CD_IS_ACTIVE_KEY  = new NamespacedKey(NAME_SPACE, "simple_ships_cd_is_active_key");
	static public final NamespacedKey SS_CD_ID_KEY         = new NamespacedKey(NAME_SPACE, "simple_ships_cd_id_key");
	static public final NamespacedKey SS_CD_TYPE_KEY       = new NamespacedKey(NAME_SPACE, "simple_ships_cd_type_key");
	static public final NamespacedKey SS_CD_INTERACTION_ID = new NamespacedKey(NAME_SPACE, "simple_ships_cd_interaction_id");
	static public final NamespacedKey SS_CD_YAW_VALUE      = new NamespacedKey(NAME_SPACE, "simple_ships_cd_yaw_value");
	static public final NamespacedKey SS_CD_PITCH_VALUE    = new NamespacedKey(NAME_SPACE, "simple_ships_cd_pitch_value");

	


	static final public boolean isShipComponent(Object obj) {
		if( obj instanceof PersistentDataHolder pdh) {
			PersistentDataContainer pdc = pdh.getPersistentDataContainer();
			Boolean boolVal = pdc.get(Constants.SIMPLE_SHIPS_COMPONENT, PersistentDataType.BOOLEAN);
			if( boolVal != null )
				return boolVal;
					
			if( SHIP_COMPONENT_ITEM_TYPE.equals(pdc.get(Constants.SHIP_COMPONENT_KEY, PersistentDataType.STRING)))
				return true;
		}
		return false;
	}

	static final public void markShipComponent(Object obj) {
		if( obj instanceof PersistentDataHolder pdh) {	
			PersistentDataContainer pdc = pdh.getPersistentDataContainer();
			pdc.set(Constants.SHIP_COMPONENT_KEY, PersistentDataType.STRING, Constants.SHIP_COMPONENT_ITEM_TYPE);
			pdc.set(Constants.SIMPLE_SHIPS_COMPONENT, PersistentDataType.BOOLEAN, true);
		}
	}
	
	static public String getStringFor(final String key) {
	 	switch(key) {
	 		case SHIP_HELM_ITEM_TYPE:
	 			return "Ship Helm";
			case SHIP_HELM_SEAT_TYPE:
				return "Ship Helm Seat";
			case SHIP_HELM_POST_TYPE:
				return "Ship Helm Post";
			case ENTITY_PAD_ITEM_TYPE:
				return "Entity Pad";
			case PASSENGER_SEAT_ITEM_TYPE:
				return "Passenger Seat";
			case SHIP_COMPONENT_ITEM_TYPE:
				return "Ship Component";
	 		default:
	 			return key;
	 	}
	}
}
