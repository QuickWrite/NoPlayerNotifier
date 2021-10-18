# NoPlayerNotifier
 A simple BungeeCord/Waterfall plugin that allows the server to notify a player when they are talking to themselves.

## How does it work?
When a player sends a message the plugin checks if only one player is on the bungee or the server and sends a message if one of these is true. These two messages are different in the config with the tags
- `msg_nobody_online_server` (When the player is alone on the server, but not the bungee):
  
  ![no-player-server](https://user-images.githubusercontent.com/54590845/137713823-2cf9b0bb-9d1e-40ef-9f50-5835a04531eb.png)
- `msg_nobody_online_bungee` (When the player is alone on the server and the bungee):
  
  ![no-player-bungee](https://user-images.githubusercontent.com/54590845/137713820-41e37855-3e3c-4c17-8ac1-01e7a30a5300.png)

When a message starts with the `prefix` no message will be send.

When you found out that you made a mistake in the `config.yml` you can just use the `/npnreload` and the plugin reloads the config. The player needs to have the `noplayernotifier.reload` permission on the Bungee.

## Why is it useful?
It can be used to explain the Chatsystem the server uses as not everyone understands the idea that every server has a different chat. This can be a common problem on BungeeCord/ Waterfall servers as many servers need to be connected and the chat should be as clean as possible.

## Contribute
If you find a bug or you have an idea for the Plugin just create an Issue or if you know how to implement it, create a Pull Request.

## License
The Plugin is licensed under the permissive [Apache 2.0](LICENSE) License.
