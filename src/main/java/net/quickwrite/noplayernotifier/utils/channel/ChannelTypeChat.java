package net.quickwrite.noplayernotifier.utils.channel;

import net.md_5.bungee.api.event.ChatEvent;

/**
 * The {@link ChannelType} if no
 * other Chat Plugin is installed that
 * is supported.
 */
public final class ChannelTypeChat extends ChannelType {
    /**
     * Checks if the message is send in the local chat
     * or if the message is send in a different chat.
     *
     * It checks if the message starts with the prefix and determines
     * like this if the message itself is send with the intention
     * of sending it to multiple servers in mind.
     *
     * @param event The {@link ChatEvent} that is checked
     * @param prefix The prefix that is stored in the config
     * @return If the message is send in the local chat
     */
    @Override
    public boolean isLocal(final ChatEvent event, final String prefix) {
        return !hasPrefix(event.getMessage(), prefix);
    }
}
