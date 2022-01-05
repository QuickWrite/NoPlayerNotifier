package net.quickwrite.noplayernotifier.utils.format;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

public class MessageCombiner {
    private static String msgPrefix;

    public static void setMsgPrefix(String messagePrefix) {
        msgPrefix = messagePrefix;
    }

    /**
     * Creates a TextComponent from a list of strings with
     * color codes
     *
     * @param text The list of lines of test
     * @return The TextComponent
     */
    public static TextComponent createTextComponent(List<String> text) {
        return new TextComponent(MessageFormatter.format(concatenateStrings(msgPrefix, text)));
    }

    public static TextComponent createTextComponent(String text) {
        return new TextComponent(MessageFormatter.format(msgPrefix + text));
    }

    public static TextComponent createTextComponent(String text, boolean hasPrefix) {
        return new TextComponent(MessageFormatter.format((hasPrefix ? msgPrefix : "") + text));
    }

    /**
     * Concatenates the strings in the list with
     * \n so that these can be used as one
     * simple string itself.
     *
     * @param messages The List of strings
     * @return The concatenated string
     */
    public static String concatenateStrings(String prefix, List<String> messages) {
        return prefix + String.join("\n", messages);
    }
}
