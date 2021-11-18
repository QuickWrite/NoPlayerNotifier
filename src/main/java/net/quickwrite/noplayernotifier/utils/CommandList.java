package net.quickwrite.noplayernotifier.utils;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * A storage for all of the commands
 *
 * @author QuickWrite
 */
public class CommandList {
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Adds a single command to the <code>commands</code>
     * HashMap with the return message and the permission
     *
     * @param command The command itself
     * @param message The message that should be send to the player
     * @param permission The permission that should be checked
     */
    public void addCommand(String command, TextComponent message, String permission) {
        commands.put(command, new Command(message, permission));
    }

    /**
     * Returns the message of the command
     *
     * If there is no command the return will be <code>null</code>
     *
     * @param command The command
     * @return The message that should be send
     */
    public Command getMessage(String command) {
        return commands.get(command);
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
        protected Command(TextComponent message, String permission) {
            this.message = message;
            this.permission = permission;
        }

        /**
         * Gets the message
         *
         * @return The message
         */
        public TextComponent getMessage() {
            return this.message;
        }

        /**
         * Gets the permission
         *
         * @return The permission
         */
        public String getPermission() {
            return this.permission;
        }
    }
}
