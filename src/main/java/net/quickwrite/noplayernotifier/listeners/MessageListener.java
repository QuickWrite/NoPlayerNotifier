package net.quickwrite.noplayernotifier.listeners;

import dev.aura.bungeechat.api.account.AccountManager;
import dev.aura.bungeechat.api.enums.ChannelType;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.quickwrite.noplayernotifier.utils.CommandList;
import net.quickwrite.noplayernotifier.utils.Config;
import net.quickwrite.noplayernotifier.utils.Permission;

import java.util.Objects;

/**
 * @author QuickWrite
 */
public class MessageListener implements Listener {
    private Config config;

    /**
     * Checks every message if a player has
     * written it and if another player
     * is on the server and sending a
     * message.
     *
     * @param config The messages that are saved in the config
     */
    public MessageListener(Config config) {
        this.config = config;
    }

    /**
     * When the player sends a message
     *
     * @param event The message event itself
     */
    @EventHandler
    public void onPlayerChat(ChatEvent event) {
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
        if(player.getServer().getInfo().getPlayers().size() == 1 && !config.hasPrefix(event.getMessage()) && getChannelType(player) == ChannelType.LOCAL) {
            player.sendMessage(config.getMessageServer());
        }
    }

    /**
     * Sets the config attribute
     *
     * @param config The config class
     */
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * Returns the channel type of the player
     *
     * @param player The player object
     * @return The channel type
     */
    private ChannelType getChannelType(ProxiedPlayer player) {
        return Objects.requireNonNull(AccountManager.getAccount(player.getUniqueId()).orElse(null)).getChannelType();
    }
}
