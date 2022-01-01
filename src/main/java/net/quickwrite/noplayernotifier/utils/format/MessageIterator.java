package net.quickwrite.noplayernotifier.utils.format;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.quickwrite.noplayernotifier.utils.Pair;

import java.awt.*;

/**
 * An iterator to format the messages with
 * hexadecimal color codes.
 *
 * @author QuickWrite
 */
public class MessageIterator {
    private final String message;
    private int iterator = -1;
    private final char identifier;

    private static final String COLOR_CHARS = "0123456789abcdef";
    private static final String ATTRIBUTE_CHARS = "klmno";
    private static final char RESET_IDENTIFIER = 'r';
    private static final char HEX_IDENTIFIER = '#';
    private static final char DELIMITER = ';';

    private ChatColor currentColor = getResetColor();
    private final Attributes attributes = new Attributes();

    /**
     * Initializes the message iterator with the
     * respective values.
     *
     * <br><br>
     *
     * Sets the identifier to the value <code>&</code>.
     *
     * @param message The message that should be iterated over.
     */
    public MessageIterator(final String message) {
        this(message, '&');
    }

    /**
     * Initializes the message iterator with the
     * respective values.
     *
     * @param message The message that should be iterated over.
     * @param identifier The iterator that should be used by the iterator
     */
    public MessageIterator(final String message, char identifier) {
        this.message = message + "-";
        this.identifier = identifier;
    }

    /**
     * Gets the TextComponent that has the formatted
     * message to the next code.
     *
     * @return A formatted TextComponent
     */
    public TextComponent toNext() {
        if(!inBounds())
            return null;

        int start = iterator + 1;

        TextComponent component = new TextComponent();
        component.setColor(currentColor);
        attributes.addAttributes(component);

        char character;
        int back = 0;

        while(inBounds()) {
            character = getNext();

            if(character == identifier) {
                back = getNextCode();

                break;
            }
        }

        component.setText(message.substring(start, iterator - back));

        return component;
    }

    /**
     * Checks if a code is valid.
     *
     * @return the number of steps that should be backtracked.
     */
    private int getNextCode() {
        if (!inBounds())
            return 0;

        char character = getNext();

        if(HEX_IDENTIFIER == character) {
            final Pair<ChatColor, Integer> buffer = getHex();

            if(buffer == null)
                return 0;

            currentColor = buffer.getValue1();

            return buffer.getValue2();
        }

        if (Character.toLowerCase(character) == RESET_IDENTIFIER) {
            currentColor = getResetColor();
            attributes.reset();

            return 1;
        }

        if(contains(COLOR_CHARS, character)) {
            currentColor = ChatColor.getByChar(character);

            return 1;
        } else if(contains(ATTRIBUTE_CHARS, character)) {
            attributes.set(character);

            return 1;
        }

        return 0;
    }

    /**
     * Gets the hex color code
     *
     * @return the ChatColor with the color code and the number
     *         of steps that should be backtracked.
     */
    private Pair<ChatColor, Integer> getHex() {
        if(!inBounds())
            return null;

        int start = iterator + 1;
        char character;
        boolean delimiter = false;

        while(inBounds()) {
            character = getNext();

            if(character == DELIMITER) {
                delimiter = true;
                break;
            }

            if (!contains(COLOR_CHARS, character)) {
                iterator--;

                break;
            }
        }

        try {
            Color color = Color.decode("#" + message.substring(start, iterator + (delimiter ? 0 : 1) ));

            return new Pair<>(ChatColor.of(color), iterator - start + 2);
        } catch (NumberFormatException
                | StringIndexOutOfBoundsException exception) {
            return null;
        }
    }

    /**
     * Checks if the iterator is in bounds.
     *
     * @return if the iterator is in bounds
     */
    private boolean inBounds() {
        return message.length() > (iterator + 1);
    }

    /**
     * Returns the next character.
     *
     * @return the next character
     */
    private char getNext() {
        iterator++;
        return message.charAt(iterator);
    }

    /**
     * Returns the color that is the default
     * color of the message.
     *
     * @return the default ChatColor
     */
    private ChatColor getResetColor() {
        return ChatColor.of(Color.WHITE);
    }

    /**
     * Checks if a character is in a String.
     *
     * @param list The String that should be checked.
     * @param character The char that should be in the String
     * @return If the char is in the String
     */
    private boolean contains(String list, char character) {
        return list.contains(Character.toLowerCase(character) + "");
    }

    /**
     * Stores the attributes of a message
     */
    private static class Attributes {
        private boolean isBold = false;
        private boolean isItalic = false;
        private boolean isUnderlined = false;
        private boolean isStrikethrough = false;
        private boolean isObfuscated = false;

        /**
         * Adds the attributes to the TextComponent
         *
         * @param component The TextComponent
         * @return The TextComponent
         */
        public void addAttributes(TextComponent component) {
            component.setBold(isBold);
            component.setItalic(isItalic);
            component.setUnderlined(isUnderlined);
            component.setStrikethrough(isStrikethrough);
            component.setObfuscated(isObfuscated);
        }

        /**
         * Sets the attribute inside the object
         *
         * @param character The character that is the attribute.
         */
        public void set(final char character) {
            switch (character) {
                case 'l':
                    isBold = true;
                    return;
                case 'o':
                    isItalic = true;
                    return;
                case 'n':
                    isUnderlined = true;
                    return;
                case 'm':
                    isStrikethrough = true;
                    return;
                case 'k':
                    isObfuscated = true;
            }
        }

        /**
         * Resets all of the attributes
         */
        public void reset() {
            isBold = false;
            isItalic = false;
            isUnderlined = false;
            isStrikethrough = false;
            isObfuscated = false;
        }
    }
}
