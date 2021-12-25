package net.quickwrite.noplayernotifier.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.quickwrite.noplayernotifier.utils.config.CommandList;
import net.quickwrite.noplayernotifier.utils.config.Config;
import net.quickwrite.noplayernotifier.utils.Permission;
import net.quickwrite.noplayernotifier.utils.channel.ChannelType;

/**
 * @author QuickWrite
 */
public class MessageListener implements Listener {
    private Config config;
    private final ChannelType channelType;

    /**
     * Checks every message if a player has
     * written it and if another player
     * is on the server and sending a
     * message.
     *
     * @param config The messages that are saved in the config
     */
    public MessageListener(final Config config, final ChannelType channelType) {
        this.config = config;
        this.channelType = channelType;
    }

    /**
     * When the player sends a message
     * this event gets called and it is getting
     * checked if a message should be send.
     *
     * @param event The message event itself
     */
    @EventHandler
    public void onPlayerChat(final ChatEvent event) {
        if(!(event.getSender() instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer)event.getSender();

        if(
                event.isCancelled() ||
                player.hasPermission(Permission.BYPASS.toString())
        )
            return;

        if(event.isCommand()) {
            CommandList.Command command = config.getCommand(
                    event.getMessage()
                        .substring(1)
                        .split(" ", 2)[0]
            );

            if(command == null) return;

            boolean playerExists = ProxyServer.getInstance().getPlayers().stream()
                    .anyMatch(command::hasPermission);

            if (!playerExists)
                player.sendMessage(command.getMessage());

            return;
        }

        // When the player is alone on the bungee
        if(ProxyServer.getInstance().getPlayers().size() == 1) {
            player.sendMessage(config.getMessageBungee());
            return;
        }

        // When the player is alone on the server
        if(player.getServer().getInfo().getPlayers().size() == 1 && channelType.isLocal(event, config.getPrefix())) {
            player.sendMessage(config.getMessageServer());
        }
    }

    /**
     * Sets the config attribute in the class
     *
     * @param config The config class
     */
    public void setConfig(final Config config) {
        this.config = config;
    }
}
