package net.quickwrite.noplayernotifier.utils.config;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.quickwrite.noplayernotifier.utils.config.holders.CommandList;
import net.quickwrite.noplayernotifier.utils.config.holders.PlayerMessages;
import net.quickwrite.noplayernotifier.utils.format.MessageCombiner;
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

    private static final PlayerMessages playerMessages;
    private static final CommandList commandList;

    static {
        playerMessages = new PlayerMessages();
        commandList = new CommandList();
    }

    /**
     * The class that is used as a intermediate step to store
     * the config
     */
    private Config() { }

    /**
     * Sets the prefix that the messages
     * should be checked against.
     *
     * When the prefix is {@code ""}
     * no prefix will be checked and all of
     * the messages will return a
     * NoPlayerNotifier message.
     *
     * @param prefix The prefix
     */
    private void setPrefix(final String prefix) {
        if(!prefix.equals(""))
            this.prefix = prefix;
        else
            this.prefix = null;
    }

    /**
     * Returns the prefix stored in the config
     *
     * @return The prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Stores all of the commands that are
     * set in the Configuration.
     *
     * @param commands The Configuration object that holds the commands
     */
    private void setCommandList(final Configuration commands) {
        final Collection<String> collection = commands.getKeys();

        commandList.clear();

        for (String command : collection) {
            TextComponent message = MessageCombiner.createTextComponent(commands.getStringList(command + ".message"));

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
     * Returns the message for a command and {@code null} if it does not exist.
     *
     * @param command The command itself ({@code /} not included)
     * @return A TextComponent that has the message that should be send to the player
     */
    public CommandList.Command getCommand(String command) {
        return commandList.getMessage(command);
    }

    /**
     * Returns the {@link PlayerMessages} object
     * that has all of the messages for the player.
     *
     * @return The {@link PlayerMessages} object.
     */
    public PlayerMessages getMsg() {
        return playerMessages;
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

    /**
     * Stores a Configuration object into this object
     * so that it is easier to use later.
     *
     * @param configuration The configuration that should be saved.
     */
    public void storeConfiguration(Configuration configuration) {
        setPrefix(configuration.getString("prefix"));
        MessageCombiner.setMsgPrefix(configuration.getString("msg_prefix"));
        playerMessages.setMessageServer(configuration.getStringList("msg_nobody_online_server"));
        playerMessages.setMessageBungee(configuration.getStringList("msg_nobody_online_bungee"));
        setCommandList(configuration.getSection("commands"));

        playerMessages.setPermissionError(configuration.getString("npnreload.permission_error"));
        playerMessages.setReloadSuccess(configuration.getString("npnreload.reload_successful"));
    }
}
