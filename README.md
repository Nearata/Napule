# Napule

> A Minecraft custom plugin for Neapovil private server

## Warning

- This plugin is unfinished and dirty.
- The plugin messages are in `Italian`.

## Features

### Events

- CombatLog. (Customizable | Toggable)
- EnderPearl cooldown. (Customizable | Toggable)
- No drops on death. (Toggable)
- Fix party op damage. (Needs `Parties` | Toggable)
- Custom message on player death. (Toggable)
  - Display health and remaining potions of the killer.
  - Format: `<death_message> (<health_count> \u2764 | <potions_count> pot)`
- Scoreboard (Toggable)
  - TPS
  - Ping (Needs `ProtocolLib`)

### Commands

All the commands should have tab completion.

- `/napule admin` - Open admin modules inventory.
- `/napule ping <username:optional>` - Display the ping.
- `/napule reload` - Reloads the configuration files.
- `/napule user` - Open the user modules inventory.

## License

Licensed under the ISC license. See [LICENSE](LICENSE) for details.
