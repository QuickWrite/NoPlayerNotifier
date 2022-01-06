package net.quickwrite.noplayernotifier.utils.config.holders;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.quickwrite.noplayernotifier.NoPlayerNotifier;
import net.quickwrite.noplayernotifier.utils.format.MessageCombiner;
import net.quickwrite.noplayernotifier.utils.format.MessageFormatter;

import java.util.List;

/**
 * Stores all of the messages
 * that are send to the player.
 */
public class PlayerMessages {
    private TextComponent messageServer;
    private TextComponent messageBungee;

    private TextComponent permissionError;
    private TextComponent reloadSuccess;

    private Configuration defaultConfiguration;

    /**
     * Sets the message for the
     * NoPlayerServer event as a
     * list of Strings.
     *
     * @param messageServer The lines of text for the message.
     */
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

    /**
     * Sets the message for the
     * NoPlayerBungee event as a
     * list of Strings.
     *
     * @param messageBungee The lines of text for the message.
     */
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

    /**
     * <p>Sets the message for the
     * {@code /npnreload} command when
     * the player has no permission.</p>
     *
     * <p>If the String is {@code ""} then the
     * default value in the default
     * {@code config.yml} will be used.</p>
     *
     * @param permissionError The message
     */
    public void setPermissionError(String permissionError) {
        if(permissionError.equals("")) {
            permissionError = getConfig().getString("npnreload.permission_error");
        }

        this.permissionError = MessageFormatter.format(permissionError)[0];
    }

    /**
     * Returns the message when
     * {@code /npnreload} is used,
     * but the user does not have the
     * {@code reload} permission.
     *
     * @return The message
     */
    public TextComponent getPermissionError() {
        return this.permissionError;
    }

    /**
     * <p>Sets the message for the
     * successful {@code /npnreload}
     * command.</p>
     *
     * <p>If the String is {@code ""} then the
     * default value in the default
     * {@code config.yml} will be used.</p>
     *
     * @param reloadSuccess The message
     */
    public void setReloadSuccess(String reloadSuccess) {
        if(reloadSuccess.equals("")) {
            reloadSuccess = getConfig().getString("npnreload.reload_successful");
        }

        this.reloadSuccess = MessageCombiner.createTextComponent(reloadSuccess);
    }

    /**
     * Returns the message when
     * the {@code /npnreload} command
     * is used and the reload is
     * successful.
     *
     * @return The message
     */
    public TextComponent getReloadSuccess() {
        return this.reloadSuccess;
    }

    /**
     * Returns the default configuration that is
     * stored inside of {@code resources/} with
     * the name of {@code config.yml}
     *
     * @return A Configuration Object
     */
    private Configuration getConfig() {
        if(defaultConfiguration == null)
            this.defaultConfiguration = ConfigurationProvider
                    .getProvider(YamlConfiguration.class)
                    .load(NoPlayerNotifier.getInstance().getResourceAsStream("config.yml"));

        return this.defaultConfiguration;
    }
}
