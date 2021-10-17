package net.quickwrite.noplayernotifier.listeners;

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
    private final Config config;

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

    @EventHandler
    public void onPlayerChat(ChatEvent event) {
        if(
                event.isCommand() ||
                event.isCancelled() ||
                !(event.getSender() instanceof ProxiedPlayer) ||
                config.hasPrefix(event.toString())
        )
            return;

        ProxiedPlayer player = (ProxiedPlayer)event.getSender();

        if(ProxyServer.getInstance().getPlayers().size() == 1) {
            player.sendMessage(config.getMessageBungee());
            return;
        }

        if(player.getServer().getInfo().getPlayers().size() == 1) {
            player.sendMessage(config.getMessageServer());
        }
    }
}
