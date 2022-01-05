package net.quickwrite.noplayernotifier.utils.config;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.quickwrite.noplayernotifier.utils.format.MessageFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Storage for the configuration file.
 *
 * @author QuickWrite
 */
public final class Config {
    private static Config instance = null;

    private String prefix;
    private String msgPrefix;
    private TextComponent messageServer;
    private TextComponent messageBungee;
    private final CommandList commandList = new CommandList();

    /**
     * The class that is used as a intermediate step to store
     * the config
     */
    private Config() { }

    private void setPrefix(final String prefix) {
        if(!prefix.equals(""))
            this.prefix = prefix;
        else
            this.prefix = null;
    }

    private void setMsgPrefix(final String msgPrefix) {
        this.msgPrefix = msgPrefix;
    }

    private void setCommandList(final Configuration commands) {
        final Collection<String> collection = commands.getKeys();

        commandList.clear();

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
        return new TextComponent(MessageFormatter.format(concatenateStrings(msgPrefix, text)));
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

    private void setMessageServer(final List<String> messageServer) {
        this.messageServer = createTextComponent(messageServer);
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

    private void setMessageBungee(final List<String> messageBungee) {
        this.messageBungee = createTextComponent(messageBungee);
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
     * Returns the message for a command and {@code null} if it does not exist.
     *
     * @param command The command itself ({@code /} not included)
     * @return A TextComponent that has the message that should be send to the player
     */
    public CommandList.Command getCommand(String command) {
        return commandList.getMessage(command);
    }

    /**
     * Returns a Config-Object instantiated.
     *
     * @return The Config Object
     */
    public static Config getConfig() {
        if (instance == null)
            instance = new Config();

        return instance;
    }

    public void storeConfiguration(Configuration configuration) {
        setPrefix(configuration.getString("prefix"));
        setMsgPrefix(configuration.getString("msg_prefix"));
        setMessageServer(configuration.getStringList("msg_nobody_online_server"));
        setMessageBungee(configuration.getStringList("msg_nobody_online_bungee"));
        setCommandList(configuration.getSection("commands"));
    }

    /**
     * Returns the prefix stored in the config
     *
     * @return The prefix
     */
    public String getPrefix() {
        return prefix;
    }
}
