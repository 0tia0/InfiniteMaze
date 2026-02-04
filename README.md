![Plugin wallpaper with the logo](https://cdn.modrinth.com/data/cached_images/715642709c0be7bba1055bb7b0065f4bb7040314.png)

# <p align="center">Procedural mazes, generated in-game</p>

<p align="center">
  <img src="https://cdn.modrinth.com/data/cached_images/8e35039b35a42d274846973aaea56c5961f06988.png" , alt="spigot logo"/>
  <img src="https://cdn.modrinth.com/data/cached_images/75e924b033ff9a67e9f434438e90d3024d6d6c0c.png" , alt="paper logo"/>
  <img src="https://cdn.modrinth.com/data/cached_images/17137b6227fb093226cf99e53045868d51205511.png" 
, alt="spigot logo"/>
</p>

## ⚙️ Features

- **Maze Generator**: Multiple generation algorithms
- **Customizable Size**: From 20×20 up to 200×200 by default (fully configurable)
- **Center Hole**: Optionally leave an open center area for custom structures
- **Custom Blocks**: Use any Minecraft block for walls and floors
- **GUI Controlled**: Configure and generate mazes entirely through an intuitive GUI
- **Events**: Create and manage maze-based events **(WIP)**
- **Integrations**: Built-in support for PlaceholderAPI

![gui-showcase](https://cdn.modrinth.com/data/8wuj6ddG/images/7825ae9d02c9987e16c2f94b5c09532186ef2e01.gif)

## 🖥️ What I'm working on _(upcoming releases)_

- **Properly designed entrance and exit platforms for the maze**
- **Savable maze configurations**
- **Event system**
- **Renameable maze worlds**

## 📝 Configuration

<details>
<summary><strong>config.yml</strong></summary>

```
################################################################################################################################
#     __  .__   __.  _______  __  .__   __.  __  .___________. _______          .___  ___.      ___      ________   _______    #
#    |  | |  \ |  | |   ____||  | |  \ |  | |  | |           ||   ____|         |   \/   |     /   \    |       /  |   ____|   #
#    |  | |   \|  | |  |__   |  | |   \|  | |  | `---|  |----`|  |__            |  \  /  |    /  ^  \   `---/  /   |  |__      #
#    |  | |  . `  | |   __|  |  | |  . `  | |  |     |  |     |   __|           |  |\/|  |   /  /_\  \     /  /    |   __|     #
#    |  | |  |\   | |  |     |  | |  |\   | |  |     |  |     |  |____          |  |  |  |  /  _____  \   /  /----.|  |____    #
#    |__| |__| \__| |__|     |__| |__| \__| |__|     |__|     |_______|         |__|  |__| /__/     \__\ /________||_______|   #
#                                                                                                                              #
################################################################################################################################
#                     ______   ______   .__   __.  _______  __    _______  ____    ____ .___  ___.  __                         #
#                    /      | /  __  \  |  \ |  | |   ____||  |  /  _____| \   \  /   / |   \/   | |  |                        #
#                   |  ,----'|  |  |  | |   \|  | |  |__   |  | |  |  __    \   \/   /  |  \  /  | |  |                        #
#                   |  |     |  |  |  | |  . `  | |   __|  |  | |  | |_ |    \_    _/   |  |\/|  | |  |                        #
#                   |  `----.|  `--'  | |  |\   | |  |     |  | |  |__| |  __  |  |     |  |  |  | |  `----.                   #
#                    \______| \______/  |__| \__| |__|     |__|  \______| (__) |__|     |__|  |__| |_______|                   #
#                                                                                                                              #
################################################################################################################################

maze_generation:
  # Maximum maze size selectable via GUI
  max_size: 200

  # Minimum allowed maze size to prevent very small generations
  # WE HIGHLY RECOMMEND keeping this value above 20 to avoid poor generation results
  min_size: 20 #READ ABOVE BEFORE CHANGING

  # Thickness of the maze walls (in blocks)
  wall_width: 2

  # Height of the maze walls (in blocks)
  wall_height: 4

anti_lag_limitation:
  # To prevent lag, the plugin generates the maze gradually over time
  # This is the maximum number of blocks placed per server tick
  # Smaller values reduce lag but increase generation time
  # The default value is balanced between speed and performance
  block_per_tick: 500

  # If true, FAWE will be used for faster and more efficient block placement when available
  use_FAWE_if_available: true
```

</details>

<details>
<summary><strong>messages.yml</strong></summary>

```
################################################################################################################################
#     __  .__   __.  _______  __  .__   __.  __  .___________. _______          .___  ___.      ___      ________   _______    #
#    |  | |  \ |  | |   ____||  | |  \ |  | |  | |           ||   ____|         |   \/   |     /   \    |       /  |   ____|   #
#    |  | |   \|  | |  |__   |  | |   \|  | |  | `---|  |----`|  |__            |  \  /  |    /  ^  \   `---/  /   |  |__      #
#    |  | |  . `  | |   __|  |  | |  . `  | |  |     |  |     |   __|           |  |\/|  |   /  /_\  \     /  /    |   __|     #
#    |  | |  |\   | |  |     |  | |  |\   | |  |     |  |     |  |____          |  |  |  |  /  _____  \   /  /----.|  |____    #
#    |__| |__| \__| |__|     |__| |__| \__| |__|     |__|     |_______|         |__|  |__| /__/     \__\ /________||_______|   #
#                                                                                                                              #
################################################################################################################################
#  .___  ___.  _______     _______.     _______.     ___       _______  _______     _______. ____    ____ .___  ___.  __       #
#  |   \/   | |   ____|   /       |    /       |    /   \     /  _____||   ____|   /       | \   \  /   / |   \/   | |  |      #
#  |  \  /  | |  |__     |   (----`   |   (----`   /  ^  \   |  |  __  |  |__     |   (----`  \   \/   /  |  \  /  | |  |      #
#  |  |\/|  | |   __|     \   \        \   \      /  /_\  \  |  | |_ | |   __|     \   \       \_    _/   |  |\/|  | |  |      #
#  |  |  |  | |  |____.----)   |   .----)   |    /  _____  \ |  |__| | |  |____.----)   |    __  |  |     |  |  |  | |  `----. #
#  |__|  |__| |_______|_______/    |_______/    /__/     \__\ \______| |_______|_______/    (__) |__|     |__|  |__| |_______| #
#                                                                                                                              #
################################################################################################################################

#
# These configurable messages support some formatting features:
# - chat links with the following format [visible text](url)
# - PAPI placeholders with the following format %your_placeholders%
#   (except for %prefix%, which is reserved for the plugin name)
#

prefix: "&6InfiniteMaze &8&l»&r"

gui:
  maze:
    title: "&6Maze &8: &2Configurator"
  chat:
    maze_generation_in_progress: "%prefix% &7We are generating your maze, &6please wait&7. You will be teleported when it’s ready. Generation time depends on maze size. Speed it up via &6config.yml &7or by installing [&6&nFAWE](https://0tia0.gitbook.io/infinite-maze)."
```


</details>


## 🤖 Commands & Permissions

<details>
<summary><strong>Commands</strong></summary>

- `/maze help` — Display the command help menu
- `/maze gui` — Open the GUI to configure and generate a maze
- `/maze tp <maze_world_name>` — Teleport you, or the selected player, into the maze world
- `/maze reload` — Reload all plugin configurations
- `/maze event create` — Set up an event in the world where the maze was generated **(WIP)**
- `/maze join <event>` — Join a specific maze event **(WIP)**
- `/maze event start` — Start the event and teleport all joined players **(WIP)**

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

</details>

_Detailed information about all available commands and permissions can be found in the official wiki!_

## 💻 Installation

- In order to make this plugin work, follow this [guide](https://0tia0.gitbook.io/infinite-maze).

## 🌟 Why InfiniteMaze?

- Fully GUI-based and easy to use
- Procedural: every maze is unique
- High level of customization for layout, blocks, and gameplay
- Event-ready with a built-in minigame system that tracks the first three players to finish **(WIP)**

## 🔗 Useful Links

- Wiki: [Infinite Maze Wiki](https://0tia0.gitbook.io/infinite-maze)
- Bug Reports: [Issue Tracker](https://github.com/0tia0/InfiniteMaze/issues) _(before opening an issue make sure to follow [this rules](https://0tia0.gitbook.io/infinite-maze/faq/troubleshooting-and-support))_
