![Plugin wallpaper with the logo](https://cdn.modrinth.com/data/cached_images/715642709c0be7bba1055bb7b0065f4bb7040314.png)

# <p align="center">Procedural mazes, generated in-game</p>

<p align="center">
  <img src="https://cdn.modrinth.com/data/cached_images/8e35039b35a42d274846973aaea56c5961f06988.png" />
  <img src="https://cdn.modrinth.com/data/cached_images/75e924b033ff9a67e9f434438e90d3024d6d6c0c.png" />
  <img src="https://cdn.modrinth.com/data/cached_images/17137b6227fb093226cf99e53045868d51205511.png" />
</p>

## ⚙️ Features

- **Maze Generator**: Multiple generation algorithms  
- **Customizable Size**: From 20×20 up to 200×200 by default (fully configurable)  
- **Center Hole**: Optionally leave an open center area for custom structures  
- **Custom Blocks**: Use any Minecraft block for walls and floors  
- **GUI Controlled**: Configure and generate mazes entirely through an intuitive GUI  
- **Events**: Create and manage maze-based events  
- **Integrations**: Built-in support for PlaceholderAPI  

## 🤖 Commands & Permissions

_Detailed information about all available commands and permissions can be found in the official wiki!_


<details>
<summary><strong>Commands</strong></summary>

- `/maze gui` — Open the GUI to configure and generate a maze  
- `/maze reload` — Reload all plugin configurations  
- `/maze event create` — Set up an event in the world where the maze was generated  
- `/maze join <event>` — Join a specific maze event  
- `/maze event start` — Start the event and teleport all joined players  
- `/maze help` — Display the command help menu  

</details>

<details>
<summary><strong>Permissions</strong></summary>

### General Commands
- `maze.commands.gui` — Access the maze GUI  
- `maze.commands.reload` — Reload the plugin configuration  
- `maze.commands.event.create` — Create a maze event  
- `maze.commands.event.join` — Join a maze event  
- `maze.commands.event.start` — Start a maze event  
- `maze.commands.help` — Access the help menu  
- `maze.commands.admin` — Bypass all permission checks  
- `maze.commands` — Base permission (if denied, `/maze` is completely unavailable)

### GUI Permissions
- `maze.gui.size` — Change maze size and center hole size  
- `maze.gui.hole` — Enable or disable the center hole  
- `maze.gui.algorithm.*` — Access all generation algorithms  
- `maze.gui.algorithm.dfs` — Use the DFS algorithm  
- `maze.gui.algorithm.kruskal` — Use the Kruskal algorithm  
- `maze.gui.algorithm.prim` — Use the Prim algorithm  
- `maze.gui.walls` — Change wall block type  
- `maze.gui.floor` — Change floor block type  

</details>



## 🌟 Why InfiniteMaze?

- Fully GUI-based and easy to use  
- Procedural and replayable: every maze is unique  
- High level of customization for layout, blocks, and gameplay  
- Event-ready with a built-in minigame system that tracks the first three players to finish  

## 📥 Installation

1. Place the plugin JAR into your `plugins/` folder  
2. Restart your server  
3. Run `/maze` to open the configuration GUI  

## 🔗 Useful Links

- Wiki: [Infinite Maze Wiki](https://github.com/0tia0/InfiniteMaze/wiki) *(Work in progress)*  
- Bug Reports: [Issue Tracker](https://github.com/0tia0/InfiniteMaze/issues)

## 🔒 Transparency Notice 
_This plugin is partially obfuscated by design: only internal algorithms are compiled to protect development effort in a free project; it uses only standard Java libraries and does not perform any malicious, network, or unsafe operations._
