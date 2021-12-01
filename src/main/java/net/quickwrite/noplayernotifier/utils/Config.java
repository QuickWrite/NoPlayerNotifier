package net.quickwrite.noplayernotifier.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Storage for the configuration file.
 *
 * @author QuickWrite
 */
public final class Config {
    private final String prefix;
    private final String msgPrefix;
    private final TextComponent messageServer;
    private final TextComponent messageBungee;
    private final CommandList commandList = new CommandList();

    /**
     * The class that is used as a intermediate step to store
     * the config
     *
     * @param prefix The prefix that global messages have
     * @param msgPrefix The prefix for the returned messages
     * @param messageServer The message when nobody is on the same server
     * @param messageBungee The message when nobody is on the same bungee
     * @param commands The commands that should be included
     */
    public Config(final String prefix,
                  final String msgPrefix,
                  final List<String> messageServer,
                  final List<String> messageBungee,
                  final Configuration commands) {
        if(!prefix.equals(""))
            this.prefix = prefix;
        else
            this.prefix = null;

        this.msgPrefix = msgPrefix;

        this.messageServer = createTextComponent(messageServer);
        this.messageBungee = createTextComponent(messageBungee);

        final Collection<String> collection = commands.getKeys();

        for (String command : collection) {
            TextComponent message = createTextComponent(commands.getStringList(command + ".message"));

            String permission = commands.getString(command + ".permission");

            List<String> commandList = new ArrayList<>();
            commandList.add(command);
            commandList.addAll(commands.getStringList(command + ".aliases"));

            addCommand(commandList, message, permission);
        }
    }

    /**
     * Adds a command with their aliases so that it
     * can be used in the HashMap
     *
     * @param commands The different commands. The aliases are treated the same as the main command
     *                 so that they can be checked as well as the main command
     * @param message The message that should be send to the player
     * @param permission The permission that should be checked
     */
    private void addCommand(List<String> commands, TextComponent message, String permission) {
        for(String command : commands) {
            commandList.addCommand(
                    command,
                    message,
                    permission
            );
        }
    }

    /**
     * Creates a TextComponent from a list of strings with
     * color codes
     *
     * @param text The list of lines of test
     * @return The TextComponent
     */
    private TextComponent createTextComponent(List<String> text) {
        return new TextComponent(format(concatenateStrings(msgPrefix, text)));
    }

    /**
     * Concatenates the strings in the list with
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
     * Returns the message for a command and <code>null</code> if it does not exist.
     *
     * @param command The command itself (<code>/</code> not included)
     * @return A TextComponent that has the message that should be send to the player
     */
    public CommandList.Command getCommand(String command) {
        return commandList.getMessage(command);
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
                configuration.getStringList("msg_nobody_online_bungee"),
                configuration.getSection("commands")
        );
    }
}
