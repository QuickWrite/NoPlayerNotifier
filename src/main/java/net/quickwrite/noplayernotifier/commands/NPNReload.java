package net.quickwrite.noplayernotifier.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.quickwrite.noplayernotifier.NoPlayerNotifier;
import net.quickwrite.noplayernotifier.utils.config.Config;
import net.quickwrite.noplayernotifier.utils.Permission;

import java.util.Collections;

/**
 * @author QuickWrite
 */
public final class NPNReload extends Command implements TabExecutor {
    private final NoPlayerNotifier noPlayerNotifier;

    /**
     * The {@code NPNReload} command that allows
     * the sender to reload the config so that
     * the Bungee does not reload itself.
     *
     * @param noPlayerNotifier The instance of the main class
     */
    public NPNReload(final NoPlayerNotifier noPlayerNotifier) {
        super("npnreload");

        this.noPlayerNotifier = noPlayerNotifier;
    }

    /**
     * Executes when a player sends the command
     * {@code /npnreload} in chat or the
     * console is sending {@code npnreload}
     *
     * @param sender The sender of the command
     * @param args The arguments the command has
     */
    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if(!hasPermission(sender)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is a mistake."));
            return;
        }

        Config.getConfig().storeConfiguration(noPlayerNotifier.getConfiguration());

        sender.sendMessage(new TextComponent(ChatColor.GREEN + "Reloaded"));
    }

    /**
     * Returns an Iterable if the sender has
     * permissions so that the command gets autocompleted
     *
     * @param sender The sender of the command
     * @param args The arguments the command has
     * @return An empty list when sender has permissions
     */
    @Override
    public final Iterable<String> onTabComplete(final CommandSender sender, final String[] args) {
        if(!hasPermission(sender)) return null;

        return Collections.emptyList();
    }

    /**
     * Checks if the sender has the
     * {@code noplayernotifier.reload} permission
     *
     * @param sender The sender of the command
     * @return If the sender has the permission
     */
    public boolean hasPermission(final CommandSender sender) {
        return sender.hasPermission(Permission.RELOAD.toString());
    }
}
