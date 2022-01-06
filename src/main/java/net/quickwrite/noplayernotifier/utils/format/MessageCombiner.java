package net.quickwrite.noplayernotifier.utils.format;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

/**
 * A class that holds static methods
 * that make the concatenation and
 * creation of messages easier.
 */
public class MessageCombiner {
    private static String msgPrefix;

    /**
     * Sets the msgPrefix for all of the messages.
     * @param messagePrefix The Message Prefix.
     */
    public static void setMsgPrefix(String messagePrefix) {
        msgPrefix = messagePrefix;
    }

    /**
     * Creates a TextComponent from a list of strings with
     * the msgPrefix and color codes
     *
     * @param text The list of lines of test
     * @return The TextComponent
     */
    public static TextComponent createTextComponent(List<String> text) {
        return new TextComponent(MessageFormatter.format(concatenateStrings(msgPrefix, text)));
    }

    /**
     * Creates a TextComponent from a String with the
     * msgPrefix and color codes.
     *
     * @param text The text
     * @return The TextComponent
     */
    public static TextComponent createTextComponent(String text) {
        return new TextComponent(MessageFormatter.format(msgPrefix + text));
    }

    /**
     * Concatenates the strings in the list with
     * \n so that these can be used as one
     * simple string itself.
     *
     * @param messages The List of strings
     * @param prefix The prefix that should be used as the first characters
     * @return The concatenated string
     */
    public static String concatenateStrings(String prefix, List<String> messages) {
        return prefix + String.join("\n", messages);
    }
}
