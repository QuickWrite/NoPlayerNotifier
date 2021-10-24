package net.quickwrite.noplayernotifier.listeners;

import dev.aura.bungeechat.api.account.AccountManager;
import dev.aura.bungeechat.api.enums.ChannelType;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.quickwrite.noplayernotifier.data.Config;

/**
 * @author QuickWrite
 */
public class MessageListener implements Listener {
    private Config config;
    private final String BYPASS_PERM = "noplayernotifier.bypass";

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
        if(
                event.isCommand() ||
                event.isCancelled() ||
                !(event.getSender() instanceof ProxiedPlayer)
        )
            return;

        ProxiedPlayer player = (ProxiedPlayer)event.getSender();

        if(player.hasPermission(BYPASS_PERM)) return;

        // When the player is alone on the bungee
        if(ProxyServer.getInstance().getPlayers().size() == 1) {
            player.sendMessage(config.getMessageBungee());
            return;
        }

        // When the player is alone on the server
        if(player.getServer().getInfo().getPlayers().size() == 1 && !config.hasPrefix(event.getMessage()) && isLocal(player)) {
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
     * Returns true when the player is in the channel <code>LOCAL</code>.
     *
     * @param player The player that is checked.
     * @return If the player is in the channel <code>LOCAL</code>.
     */
    private boolean isLocal(ProxiedPlayer player) {
        return AccountManager.getAccount(player.getUniqueId()).get().getChannelType() == ChannelType.LOCAL;
    }
}
