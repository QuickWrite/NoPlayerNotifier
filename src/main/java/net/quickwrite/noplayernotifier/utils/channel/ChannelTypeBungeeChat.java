package net.quickwrite.noplayernotifier.utils.channel;

import dev.aura.bungeechat.api.account.AccountManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;

import java.util.Objects;

public final class ChannelTypeBungeeChat extends ChannelType {
    /**
     * Checks if the message is send in the local chat
     * or if the message is send in a different chat.
     *
     * It checks the message not only by the prefix as
     * it checks if the message is send in the BungeeChat
     * LOCAL chat as well.
     *
     * @param event The <code>ChatEvent</code> that is checked
     * @param prefix The prefix that is stored in the config
     * @return If the message is send in the local chat
     */
    @Override
    public boolean isLocal(final ChatEvent event, final String prefix) {
        return !hasPrefix(event.getMessage(), prefix) &&
                getChannelType((ProxiedPlayer) event.getSender()) == dev.aura.bungeechat.api.enums.ChannelType.LOCAL;
    }

    /**
     * Returns the channel type of the player
     *
     * @param player The player object
     * @return The channel type
     */
    private dev.aura.bungeechat.api.enums.ChannelType getChannelType(final ProxiedPlayer player) {
        return Objects.requireNonNull(AccountManager.getAccount(player.getUniqueId()).orElse(null)).getChannelType();
    }
}
