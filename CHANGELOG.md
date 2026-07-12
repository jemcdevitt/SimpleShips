# Changelog

## 26.2-1.0

* First release for working with Minecraft 26.2 servers

## 1.1.1

* Fixed issue where shift-right clicking to mount the helm resulted in unexpected behaviour

## 1.1.0

* Reworking PersistentData information used
* Reworked ship components to utilize a new CompositeDisplay manager which eliminates needs to rehydrate and should work consistently now after login/logout and server restarts

## 1.0.1

* Cleaned up extraneous debug messages

## 1.0
* Added permission checks to the available commands

## 0.9.11-beta

* Modified helm recipe to work with any wooden trapdoor and wooden fence post
* Modified seat recipe to work with any wooden stair and carpet
* Modified pad recipe to work with any wooden fence post
* Added a parrot perch

## 0.9.10-beta

### Enhancement

* Add support for moving a player's spawn point bed when it is on a moving vehicle

## 0.9.9-beta

### Fixed

* Shelves now properly show their contents when virtualized

## 0.9.8-beta

### Fixed

* Modifed inventory handling to detect InventoryHolder types and not Containers, this corrects inventory loss from Shelves and BookShelves and should work for all InventoryHolders

### Outstanding

* Shelves currently do not show their contents when materialized but they are restored when ship is restored.

## 0.9.7-beta

### Fixed

* Improved entity scanning logic to better preserve internal SimpleShips control entities while excluding managed ship display components.

### Improved

* Added support for transporting third-party decorative display entities attached to ships.
* Improved compatibility with decoration-style datapacks and plugins that use `Display` and `Interaction` entities.
* Decorative entities that are part of parent/child passenger hierarchies are now preserved correctly during ship movement and restoration.
* Decorations from datapacks such as *Decorations Plus* now move and restore correctly with ships.
* External display entities now temporarily inherit SimpleShips interpolation and teleport settings during movement for smoother visual motion, with original settings restored on disassembly.

### Compatibility

* Improved interoperability with datapacks/plugins that use composite entity structures for custom decorations.
* Prevented orphaned display entities caused by independent movement of passenger-linked decorative entities.


## 0.9.6-beta
Added enchanting tables and bookshelves to supported blocks.

## 0.9.5-beta
Corrected orientation issues with logs and blocks that are Orientable.

## 0.9.4-beta
Added checks for using death chest type plugins or data packs.  Assuming
the chest is placed at the location of the player's death, the re-assembly logic
now looks if there is a container at the location that the root block would be
placed and if that is the case, the root block is not restored and the container
is left untouched. 

## 0.9.3-beta
Implemented immediate restore of the ship on player death to
avoid ship loss.

## 0.9.2-beta
Updated the collision checks to ignore kelp and lily pads

## 0.9.1-beta

Initial public beta release.

### Features
- Sailing ships
- Passenger seats
- Entity pads
- Decorative support
- Item frame support
- Armor stand support
- Collision handling
- Ship persistence

### Notes
- Water-only ships currently supported
