package net.quickwrite.noplayernotifier.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.quickwrite.noplayernotifier.NoPlayerNotifier;
import net.quickwrite.noplayernotifier.data.Config;
import net.quickwrite.noplayernotifier.listeners.MessageListener;

import java.util.Collections;

/**
 * @author QuickWrite
 */
public class NPNReload extends Command implements TabExecutor {
    private final MessageListener messageListener;
    private final NoPlayerNotifier noPlayerNotifier;

    /**
     * The <code>NPNReload</code> command that allows
     * the sender to reload the config so that
     * the Bungee does not reload itself.
     *
     * @param noPlayerNotifier The instance of the main class
     * @param messageListener The message listener that gets all of the messages
     */
    public NPNReload(NoPlayerNotifier noPlayerNotifier, MessageListener messageListener) {
        super("npnreload");

        this.noPlayerNotifier = noPlayerNotifier;
        this.messageListener = messageListener;
    }

    /**
     * Executes the command <code>/npnreload</code>
     *
     * @param sender The sender of the command
     * @param args The arguments the command has
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!hasPermission(sender)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is a mistake."));
            return;
        }

        messageListener.setConfig(Config.getConfig(noPlayerNotifier.getConfiguration()));

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
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!hasPermission(sender)) return null;

        return Collections.emptyList();
    }

    /**
     * Checks if the sender has the
     * <code>noplayernotifier.reload</code> permission
     *
     * @param sender The sender of the command
     * @return If the sender has the permission
     */
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("noplayernotifier.reload");
    }
}
