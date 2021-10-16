package net.quickwrite.noplayernotifier.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.quickwrite.noplayernotifier.data.Messages;

/**
 * @author QuickWrite
 */
public class MessageListener implements Listener {
    private final Messages messages;

    public MessageListener(Messages messages) {
        this.messages = messages;
    }

    @EventHandler
    public void onPlayerChat(ChatEvent event) {
        if(event.isCommand() || event.isCancelled() || !(event.getSender() instanceof ProxiedPlayer) || messageStartsWith(event.toString())) return;

        ProxiedPlayer player = (ProxiedPlayer)event.getSender();

        if(ProxyServer.getInstance().getPlayers().size() == 1) {
            player.sendMessage(new TextComponent(format(messages.getMessageBungee())));
            return;
        }

        if(player.getServer().getInfo().getPlayers().size() == 1) {
            player.sendMessage(new TextComponent(format(messages.getMessageServer())));
            return;
        }
    }

    private boolean messageStartsWith(String message) {
        if(messages.getPrefix() == null) return false;

        return message.startsWith(messages.getPrefix());
    }

    private String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
