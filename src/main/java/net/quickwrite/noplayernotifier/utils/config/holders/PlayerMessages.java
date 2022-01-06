package net.quickwrite.noplayernotifier.utils.config.holders;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.quickwrite.noplayernotifier.NoPlayerNotifier;
import net.quickwrite.noplayernotifier.utils.format.MessageCombiner;
import net.quickwrite.noplayernotifier.utils.format.MessageFormatter;

import java.util.List;

public class PlayerMessages {
    private TextComponent messageServer;
    private TextComponent messageBungee;

    private TextComponent permissionError;
    private TextComponent reloadSuccess;

    private Configuration defaultConfiguration;

    public void setMessageServer(final List<String> messageServer) {
        this.messageServer = MessageCombiner.createTextComponent(messageServer);
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

    public void setMessageBungee(final List<String> messageBungee) {
        this.messageBungee = MessageCombiner.createTextComponent(messageBungee);
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

    public void setPermissionError(String permissionError) {
        if(permissionError.equals("")) {
            permissionError = getConfig().getString("npnreload.permission_error");
        }

        this.permissionError = MessageFormatter.format(permissionError)[0];
    }

    public TextComponent getPermissionError() {
        return this.permissionError;
    }

    public void setReloadSuccess(String reloadSuccess) {
        if(reloadSuccess.equals("")) {
            reloadSuccess = getConfig().getString("npnreload.reload_successful");
        }

        this.reloadSuccess = MessageCombiner.createTextComponent(reloadSuccess);
    }

    public TextComponent getReloadSuccess() {
        return this.reloadSuccess;
    }

    private Configuration getConfig() {
        if(defaultConfiguration == null)
            this.defaultConfiguration = ConfigurationProvider
                    .getProvider(YamlConfiguration.class)
                    .load(NoPlayerNotifier.getInstance().getResourceAsStream("config.yml"));

        return this.defaultConfiguration;
    }
}
