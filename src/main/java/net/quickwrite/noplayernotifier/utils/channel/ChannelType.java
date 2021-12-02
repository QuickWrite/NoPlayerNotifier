package net.quickwrite.noplayernotifier.utils.channel;

import net.md_5.bungee.api.event.ChatEvent;

public interface ChannelType {
    /**
     * Checks if the message is send in the local chat
     * or if the message is send in a different chat.
     *
     * @param event The <code>ChatEvent</code> that is checked
     * @param prefix The prefix that is stored in the config
     * @return If the message is send in the local chat
     */
    boolean isLocal(final ChatEvent event, final String prefix);

    /**
     * Returns if the message starts with the
     * prefix that is specified in the config
     *
     * @param message The message that is checked
     * @param prefix The prefix that is checked against
     * @return If the message starts with the prefix
     */
    default boolean hasPrefix(String message, String prefix) {
        if(prefix == null) return true;

        return message.startsWith(prefix);
    }
}