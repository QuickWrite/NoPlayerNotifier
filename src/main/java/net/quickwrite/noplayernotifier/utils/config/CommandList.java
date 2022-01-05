package net.quickwrite.noplayernotifier.utils.config;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * A storage for all of the commands
 *
 * @author QuickWrite
 */
public final class CommandList {
    /**
     * The HashMap where all of the different commands and their
     * messages that can be accessed to check if the command
     * should be included and what message should be send.
     */
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Adds a single command to the {@code commands}
     * HashMap with the return message and the permission
     *
     * @param command The command itself
     * @param message The message that should be send to the player
     * @param permission The permission that should be checked
     */
    public void addCommand(final String command, final TextComponent message, final String permission) {
        commands.put(command, new Command(message, permission));
    }

    /**
     * Returns the message of the command
     *
     * If there is no command the return will be {@code null}
     *
     * @param command The command
     * @return The message that should be send
     */
    public Command getMessage(final String command) {
        return commands.get(command);
    }

    public void clear() {
        commands.clear();
    }

    /**
     * @author QuickWrite
     */
    public static final class Command {
        private final TextComponent message;
        private final String permission;

        /**
         * Stores the message and the permission
         *
         * @param message The message of the command
         * @param permission The permission of the command
         */
        protected Command(final TextComponent message, final String permission) {
            this.message = message;
            this.permission = permission;
        }

        /**
         * Gets the return message if the player is on the server
         * without any other player that has the permission.
         *
         * @return The message
         */
        public TextComponent getMessage() {
            return this.message;
        }

        /**
         * Gets the permission that is needed for a player to have
         * so that the message does not get send.
         *
         * @return The permission
         */
        public String getPermission() {
            return this.permission;
        }

        /**
         * Returns true if the player has the permission that
         * is required with the specific command.
         *
         * If the command does not have the permission it returns true
         *
         * @param player The player that is checked
         * @return If the player has the permission
         */
        public boolean hasPermission(final ProxiedPlayer player) {
            if (this.permission == null)
                return true;

            return player.hasPermission(this.permission);
        }
    }
}
