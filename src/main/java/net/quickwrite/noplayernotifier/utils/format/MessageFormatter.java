package net.quickwrite.noplayernotifier.utils.format;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public final class MessageFormatter {
    public static TextComponent[] format(String message) {
        MessageIterator messageIterator = new MessageIterator(message, '&');

        List<TextComponent> textComponentList = new ArrayList<>();

        TextComponent nextItem = messageIterator.toNext();

        while(nextItem != null) {
            textComponentList.add(nextItem);

            nextItem = messageIterator.toNext();
        }

        return textComponentList.toArray(new TextComponent[0]);
    }
}
