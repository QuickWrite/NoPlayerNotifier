package net.quickwrite.noplayernotifier.utils.format;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.quickwrite.noplayernotifier.utils.Pair;

import java.awt.*;

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
        component = attributes.addAttributes(component);

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

    private boolean contains(String list, char character) {
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
