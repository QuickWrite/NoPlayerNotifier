package net.quickwrite.noplayernotifier.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;

import java.util.List;

/**
 * Storage for the configuration file.
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
    public Config(String prefix,
                  String msgPrefix,
                  List<String> messageServer,
                  List<String> messageBungee) {
        if(!prefix.equals(""))
            this.prefix = prefix;
        else
            this.prefix = null;

        this.messageServer = createTextComponent(msgPrefix, messageServer);
        this.messageBungee = createTextComponent(msgPrefix, messageBungee);
    }

    private TextComponent createTextComponent(String prefix, List<String> text) {
        return new TextComponent(format(concatenateStrings(prefix, text)));
    }

    /**
     * Concatinates the strings in the list with
     * \n so that these can be used as one
     * simple string itself.
     *
     * @param messages The List of strings
     * @return The concatenated string
     */
    private String concatenateStrings(String prefix, List<String> messages) {
        return prefix + String.join("\n", messages);
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

    /**
     * Returns if the message starts with the
     * prefix that is specified in the config
     *
     * @param message The message that is checked
     * @return If the message starts with the prefix
     */
    public boolean hasPrefix(String message) {
        if(this.prefix == null) return false;

        return message.startsWith(this.prefix);
    }

    /**
     * Returns the message for the
     * NoPlayerServer event as a
     * TextComponent
     *
     * @return The message
     */
    public TextComponent getMessageServer() {
        return this.messageServer;
    }

    /**
     * Returns the message for the
     * NoPlayerBungee event as a
     * TextComponent
     *
     * @return The message
     */
    public TextComponent getMessageBungee() {
        return this.messageBungee;
    }

    /**
     * Returns a Config-Object instantiated.
     *
     * @param configuration The configuration Object
     * @return The Config Object
     */
    public static Config getConfig(Configuration configuration) {
        return new Config(
                configuration.getString("prefix"),
                configuration.getString("msg_prefix"),
                configuration.getStringList("msg_nobody_online_server"),
                configuration.getStringList("msg_nobody_online_bungee")
        );
    }
}
