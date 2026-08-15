# SimpleShips

<p align="center">
	<img src="screenshots/VikingLongShip.png" width="500">
</p>



> Smooth, survival-friendly sailing ships for modern Minecraft servers.

SimpleShips is a lightweight Paper plugin that allows players to build and sail their own ships using ordinary Minecraft blocks.

No client mods. No resource packs. No physics simulation.

Build a ship, place a helm, gather your crew, and set sail.

---

## Features

* Smooth ship movement using modern Display Entity APIs
* No client mods required
* Multiplayer friendly
* Survival-friendly recipes
* Passenger seats for other players
* Entity pads for animals and villagers
* Dedicated parrot perches
* Moving respawn points for shipboard beds
* Collision detection while sailing
* Reverse movement to help maneuver out of tight spaces
* Ship persistence across chunk loads and server restarts
* Lightweight implementation using supported Paper APIs only


### Supported Decorative Features

SimpleShips preserves many decorative elements while ships are materialized:

* Banners and their patterns
* Hanging banners
* Signs
* Hanging signs
* Player heads and skulls
* Item frames and glow item frames
* Armor stands
* Chests and double chests
* Barrels
* Furnaces
* Beds
* Campfires
* Light sources
* Shelves
* Chiseled Bookshelves

Inventories and most block state information are restored automatically when ships disassemble.

### Death Chest Compatibility

When a ship rematerializes after the pilot dies, SimpleShips will not overwrite a container found at the helm/root block location.

This is intended to improve compatibility with death chest and grave-style plugins or datapacks, which often place a recovery container at the player's death location.

In this situation, the recovery container is preserved and the ship may be restored with one missing block near the helm.

---

## Living Aboard Your Ship

SimpleShips is designed to support long-term exploration and life at sea.  Beds, storage, companions, and many decorative elements can travel with your ship, allowing vessels to become true mobile homes.

### Moving Respawn Points

If a player sets their respawn point using a bed aboard a ship, SimpleShips will automatically move that respawn point when the ship rematerializes.

This allows captains and passengers to treat their vessel as a true mobile home. If disaster strikes while exploring distant oceans, players can respawn aboard their ship rather than returning to a distant land-based base.

### Ship Companions

SimpleShips supports bringing companions along for the voyage.

* Passenger Seats allow other players to travel aboard your vessel.
* Entity Pads allow animals and villagers to travel safely while underway.
* Parrot Perches provide a dedicated place for parrots to ride aboard ships.

Whether you travel alone, with friends, or with a loyal crew of pets and companions, your ship can become a living part of your world.

---

## Philosophy

SimpleShips is intentionally *not* a physics simulation.

There are already fantastic projects in the Minecraft community focused on advanced engineering and physics-driven gameplay. SimpleShips takes a different approach:

* No buoyancy calculations
* No weight systems
* No power systems
* No rigid body physics
* No complicated setup

The goal is simple:

> Build a ship and sail it through your world.

SimpleShips focuses on atmosphere, exploration, creativity, and multiplayer adventures while remaining lightweight and easy to use.

---

## Requirements

* PaperMC 1.21.11+
* Java 21+

### Tested Versions

### SimpleShips-1.+
* Paper 1.21.11
* Paper 26.2.1
* Java 21

### SimpleShipsPlugin-26.2.*
* Paper 26.2
* Java 25

SimpleShips uses supported Paper APIs only and does not rely on NMS or internal server classes.

---

## Installation

1. Download the latest release
2. Drop the jar into your server's `plugins/` directory
3. Start or restart the server
4. Build a ship, place a helm and begin sailing

---

## Configuration

Currently the configuration file is limited to setting boundaries for assembled ships.

```yaml
debug: false

ship-size:
  max-ship-blocks:  500
  max-ship-x-width: 32
  max-ship-height:  16
  max-ship-z-width: 32
  
allowed-blocks:
  - "#ANVIL"
  - "#ALL_SIGNS"
  - "#BAMBOO_BLOCKS"
  - "#BANNERS"
  - "#BARS"
  - "#BEDS"
  - "#BUTTONS"
  - "#CANDLES"
  - "#CHAINS"
  - "#COPPER"
  - "#DOORS"
  - "#FENCES"
  - "#FENCE_GATES"
  - "#FLOWER_POTS"
  - "#LANTERNS"
  - "#LIGHTNING_RODS"
  - "#LOGS"
  - "#PLANKS"
  - "#WOODEN_STAIRS"
  - "#TERRACOTTA"
  - "#WOODEN_SHELVES"
  - "#WOODEN_SLABS"
  - "#WOODEN_TRAPDOORS"
  - "#WOOL"
  - "#WOOL_CARPETS"
  - BARRIER
  - RED_SANDSTONE_STAIRS
  - ARMOR_STAND
  - BARREL
  - BELL
  - BOOKSHELF
  - CAMPFIRE
  - CARTOGRAPHY_TABLE
  
```

The `debug` parameter will cause extra messages to be generated in the server logs.

This shows an example of how to limit the ship to just 500 blocks total, and max blocks by dimension.  The default uses -1 for all of these so there is no limit.

The `allowed-blocks` defines what blocks are permitted on the ship.  This list may use a tag reference such as `#PLANKS` which would include all wood plank variants by default or list each block separately. Make sure to wrap a tag reference in double quotes so the leading # does not get interpreted as a comment marker.

---

## Ship Components

There are various components available for use with the ship, the primary being the **Helm**.
To carry non-player living entities, there is the **Entity Pad**. To allow other players to travel aboard your vessel, there is the **Passenger Seat** and for avian companions, there is the **Parrot Perch**.

When placing the **Helm**, it is necessary to be facing in the direction considered to be the front of the
ship as the direction the player is facing at the time of placement determines what direction is `forward`.

All components can be picked up with a `sneak-left click`.

To use the **Helm** or **Passenger Seat**, simply right-click and the player will mount. The moment a player mounts the
**Helm** the ship is assembled into Display entities and any entities or players standing on the ship will
fall through the ship, so make sure your companions and pets are seated before the captain takes the helm.

The **Entity Pad** is used by simply right-clicking. It will scan the area around the pad for a nearby living entity (excluding players, armor stands, and water mobs) and mount that entity onto the pad.

The **Parrot Perch** is a decorative ship component designed specifically for parrots. Right-click the perch while near a parrot 
and it will take up residence aboard your vessel. Parrots remain attached while the ship is underway and travel with the vessel.


Unmounting a **Helm** or **Passenger Seat** is the standard unmount option.

To detach the entity from the **Entity Pad** or **Parrot Perch**, `sneak-right click` on the entity or the pad.

---

## Crafting Recipes


### Helm

Used to pilot ships.  When placing the helm, the player needs to be facing
in the direction considered the front of the ship as that will be considered
the direction of movement.

```text
[][ Any Wooden Trapdoor ][]
[][   Any Wooden Fence  ][]
[][      Iron Ingot     ][]
```

### Passenger Seat

Allows other players to ride aboard ships.

```text
[][    Any Carpet    ][]
[][ Any Wooden Stair ][]
[][    Iron Ingot    ][]
```

### Entity Pad

Allows animals and villagers to travel aboard ships.

```text
[][      Hay Bale    ][]
[][ Any Wooden Fence ][]
[][    Iron Ingot    ][]
```

### Parrot Perch

Allows parrots to travel in style aboard ships.

```text
[Stick][Stick][Stick]
[     ][Stick][     ]
[     ][Stick][     ]
```

---

## Controls

| Key    | Action                           |
| ------ | -------------------------------- |
| W      | Move forward                     |
| S      | Move backward                    |
| A / D  | Turn ship                        |
| Space  | Toggle auto-sail                 |
| Sprint | Align ship to cardinal direction |
| Sneak  | Dismount                         |


When auto-sail is active, the ship will move forward at a steady pace and can be turned.  To halt the ship,
simply press W/S or Space again.

---

## Building Ships

To create a ship:

1. Build using normal Minecraft blocks
2. Ensure at least part of the ship is touching water
3. Place a helm somewhere on the vessel
4. Right-click the helm to assemble and pilot the ship
5. Upon dismount, the ship will align to the nearest cardinal direction and rematerialize.

When assembled, the ship materializes into moving Display Entities while preserving the appearance of the original build.

---

## Collision Handling

SimpleShips performs collision detection while moving forward to help prevent ships from passing through terrain or structures.

Reverse movement intentionally ignores collision checks.

This behavior is a deliberate design choice intended to help players recover ships that become wedged in tight canals, docks, harbors, or other difficult navigation areas.

While it is technically possible to misuse reverse movement to force ships into invalid locations, SimpleShips prioritizes gameplay recovery and ease of use over strict movement enforcement.

Note that when the pilot dismounts, the ship rematerializes back into world blocks. Any intersecting blocks occupying the same space may be replaced during this process.

---
## Commands

| Command | Description |
|---|---|
| `/simpleshipsplugin:cleanup` | This will remove all ship component parts (helm/seat/pad) as an aid to cleaning up issues |
| `/simpleshipsplugin:helm` | Gives the player a ship helm |
| `/simpleshipsplugin:seat` | Gives the player a passenger seat |
| `/simpleshipsplugin:pad` | Gives the player an entity pad |
| `/simpleshipsplugin:perch` | Gives the player a parrot perch |
| `/simpleshipsplugin:flush` | Forces all active ships to rematerialize |

### Permissions
```
simpleships.cleanup.use:
  description: Allow use of the cleanup command
  default: op
  
simpleships.flush.use:
  description: Allow use of the flush command
  default: op
  
simpleships.helm.use:
  description: Allow use of the helm spawn command
  default: op
  
simpleships.seat.use:
  description: Allow use of the seat spawn command
  default: op
  
simpleships.pad.use:
  description: Allow use of the pad spawn command
  default: op
  
simpleships.perch.use:
  description: Allow use of the perch spawn command
  default: op
```


---
## Notes

* Larger ships move more slowly than smaller ships
* Very large ships may occasionally show minor visual interpolation gaps during movement
* Reverse movement intentionally ignores collision checks to help ships escape tight spaces
* Ships currently operate on water only


---
### Waterline Note

SimpleShips is designed for surface vessels.

Ships with enclosed spaces below the waterline may leave or carry water in unexpected ways when materialized and restored. If you build submarine-style vessels or sealed underwater cabins, those spaces may flood.

For best results, build ships as surface vessels with no enclosed dry rooms below the waterline.

---

## Current Status

SimpleShips has reached a 1.0 release, but the project is still actively developed and used in real survival gameplay.

The plugin is fully playable and actively used in survival gameplay, but there may still be occasional edge cases or issues during chunk loading, server restart recovery, or unusual builds.

Feedback, bug reports, and suggestions are welcome.

---

## Screenshots and Videos

<p align="center">
	<img src="screenshots/VikingLongShip.png" width="700">
</p>


<p align="center">
	<video src="https://github.com/user-attachments/assets/6aa976c3-8467-417c-b3c7-8de4ae4a63af" controls width="800"></video>
</p>	

<p align="center">
	<em>
		Viking Longship design by Minecraft builder ThorHammerhand
	</em>
</p>	


---

## Inspiration

SimpleShips would not exist without the amazing work done over the years by the Minecraft modding and plugin communities.

Special appreciation goes to projects such as:

* Archimedes' Ships
* Davinci's Vessels
* Movecraft
* Valkyrien Skies
* Create
* Create Aeronautics

These projects helped inspire the idea that player-built ships could become living parts of a Minecraft world.

---

## License

SimpleShips is licensed under the MIT License.

See the [LICENSE](LICENSE) file for full details.

---

## Final Thoughts

SimpleShips began as a personal experiment exploring what modern Minecraft Display Entities could do.

It turned into something far more fun than expected.

If you build something cool with it, sail into adventure with friends, or simply enjoy watching a ship glide across the water at sunset, then the project has succeeded.

Fair winds and following seas.

