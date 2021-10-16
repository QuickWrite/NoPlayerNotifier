package net.quickwrite.noplayernotifier.data;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

/**
 *
 * @author QuickWrite
 */
public class Config {
    private final String prefix;
    private final TextComponent messageServer;
    private final TextComponent messageBungee;

    /**
     * The class that is used as a intermediate step to store
     * the config
     *
     * @param prefix The prefix that global messages have
     * @param messageServer The message when nobody is on the same server
     * @param messageBungee The message when nobody is on the same bungee
     */
    public Config(String prefix, String messageServer, String messageBungee) {
        if(prefix.equals(""))
            this.prefix = prefix;
        else
            this.prefix = null;

        this.messageServer = new TextComponent(format(messageServer));
        this.messageBungee = new TextComponent(format(messageBungee));
    }

    /**
     * Formats the message by translating the
     * colorcodes with an '&'
     *
     * @param string The message that should be translated.
     * @return The translated message.
     */
    private String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public TextComponent getMessageServer() {
        return this.messageServer;
    }

    public TextComponent getMessageBungee() {
        return this.messageBungee;
    }
}
