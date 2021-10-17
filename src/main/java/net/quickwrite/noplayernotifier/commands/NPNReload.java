package net.quickwrite.noplayernotifier.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.quickwrite.noplayernotifier.NoPlayerNotifier;
import net.quickwrite.noplayernotifier.listeners.MessageListener;

import java.util.Collections;

public class NPNReload extends Command implements TabExecutor {
    private final MessageListener messageListener;
    private final NoPlayerNotifier noPlayerNotifier;

    public NPNReload(NoPlayerNotifier noPlayerNotifier, MessageListener messageListener) {
        super("npnreload");

        this.noPlayerNotifier = noPlayerNotifier;
        this.messageListener = messageListener;

        System.out.println("Test 3");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!hasPermission(sender)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is a mistake."));
            return;
        }

        messageListener.setConfig(noPlayerNotifier.getConfig(noPlayerNotifier.getConfiguration()));

        sender.sendMessage(new TextComponent(ChatColor.GREEN + "Reloaded"));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!hasPermission(sender)) return null;

        return Collections.emptyList();
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("noplayernotifier.reload");
    }
}
