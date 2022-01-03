package net.quickwrite.noplayernotifier.utils.channel;

import net.md_5.bungee.api.event.ChatEvent;

/**
 * The ChannelType the message
 * should be checked against.
 */
public abstract class ChannelType {
    /**
     * Checks if the message is send in the local chat
     * or if the message is send in a different chat.
     *
     * @param event The {@link ChatEvent} that is checked
     * @param prefix The prefix that is stored in the config
     * @return If the message is send in the local chat
     */
    public abstract boolean isLocal(final ChatEvent event, final String prefix);

    /**
     * Returns if the message starts with the
     * prefix that is specified in the config
     *
     * @param message The message that is checked
     * @param prefix The prefix that is checked against
     * @return If the message starts with the prefix
     */
     protected boolean hasPrefix(String message, String prefix) {
        if(prefix == null) return true;

        return message.startsWith(prefix);
    }
}
