# VillagerViewer

VillagerViewer is a simple Minecraft Fabric mod that allows players to view and interact with a villager's inventory by sneaking and right-clicking on them. A stick can be configured as an extra step to access the villager's inventory.

![](https://i.imgur.com/uo3oTD2.png)

## Features

* Open a villager's inventory by sneaking and right-clicking.
* Optional requirement to hold a stick when interacting.
* Configurable behavior through a properties file.

## Configuration

The mod automatically creates a configuration file at:

```
config/villagerviewer.properties
```

### Options

* `requireStick` (default: `true`) - When enabled, players must hold a stick to access a villager's inventory.

## Usage

1. Sneak and right-click on a villager while holding a stick (if `requireStick=true`).
2. The villager inventory screen will open for inspection or interaction.

## Build & Installation

1. Clone this repository.
2. Build with a compatible modding toolchain (e.g. Fabric Loom).
3. Place the compiled `.jar` file in your Minecraft `mods` folder.

## Requirements

* Minecraft version compatible with the APIs used
* Fabric API
* Java 17+

## License

This mod is open-source. See `LICENSE` for details.
