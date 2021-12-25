package net.quickwrite.noplayernotifier.utils.format;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import java.awt.*;

public class MessageIterator {
    private final String message;
    private int iterator = -1;
    private final char identifier;

    private static final String COLOR_CHARS = "0123456789abcdef";
    private static final String ATTRIBUTE_CHARS = "klmno";
    private static final char RESET_IDENTIFIER = 'r';
    private static final char HEX_IDENTIFIER = '#';

    private ChatColor currentColor = getResetColor();
    private final Attributes attributes = new Attributes();

    public MessageIterator(final String message, char identifier) {
        this.message = message + "-";
        this.identifier = identifier;
    }

    public TextComponent toNext() {
        if(!inBounds())
            return null;

        int start = iterator + 1;

        TextComponent component = new TextComponent();
        component.setColor(currentColor);

        char character;

        while(inBounds()) {
            character = getNext();

            if(character == identifier && getNextCode()) {
                break;
            }
        }

        component.setText(message.substring(start, iterator));

        return component;
    }

    private boolean getNextCode() {
        if (!inBounds())
            return false;

        char character = getNext();

        if(HEX_IDENTIFIER == character) {
            final ChatColor buffer = getHex();

            if(buffer == null)
                return false;

            currentColor = buffer;
            return true;
        }

        if (Character.toLowerCase(character) == RESET_IDENTIFIER) {
            currentColor = getResetColor();
            attributes.reset();

            return true;
        }

        if(contains(COLOR_CHARS, character)) {
            currentColor = ChatColor.getByChar(character);

            return true;
        } else if(contains(ATTRIBUTE_CHARS, character)) {
            attributes.set(character);

            return true;
        }

        return false;
    }

    private ChatColor getHex() {
        int start = iterator + 1;
        char character;

        while(inBounds()) {
            character = getNext();

            if (!contains(COLOR_CHARS, character)) {
                iterator--;

                break;
            }
        }

        try {
            Color color = Color.decode("#" + message.substring(start, iterator));

            return ChatColor.of(color);
        } catch (NumberFormatException
                | StringIndexOutOfBoundsException exception) {
            return null;
        }
    }

    private boolean inBounds() {
        return message.length() > (iterator + 1);
    }

    private char getNext() {
        iterator++;
        return message.charAt(iterator);
    }

    private ChatColor getResetColor() {
        return ChatColor.of(Color.WHITE);
    }

    private boolean contains(String list,char character) {
        return list.contains(Character.toLowerCase(character) + "");
    }

    private static class Attributes {
        private boolean isBold = false;
        private boolean isItalic = false;
        private boolean isUnderlined = false;
        private boolean isStrikethrough = false;
        private boolean isObfuscated = false;

        public TextComponent addAttributes(final TextComponent component) {
            component.setBold(isBold);
            component.setItalic(isItalic);
            component.setUnderlined(isUnderlined);
            component.setStrikethrough(isStrikethrough);
            component.setObfuscated(isObfuscated);

            return component;
        }

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
                    return;
            }
        }

        public void reset() {
            isBold = false;
            isItalic = false;
            isUnderlined = false;
            isStrikethrough = false;
            isObfuscated = false;
        }
    }
}
