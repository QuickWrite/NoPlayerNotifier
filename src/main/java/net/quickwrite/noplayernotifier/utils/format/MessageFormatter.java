package net.quickwrite.noplayernotifier.utils.format;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple class that is there for formatting messages
 *
 * @author QuickWrite
 */
public final class MessageFormatter {

    /**
     * <p>Formats the message with color codes
     * and custom hex codes.</p>
     *
     * <p>All of the color codes are case-insensitive.</p>
     *
     * <p>The color codes from {@code 1}-{@code f} can
     * be used with an {@code &}.
     * They behave exactly like the color codes
     * Minecraft itself gives with the {@code §}. </p>
     *
     * <p>Also the attributes that make the message
     * bold ({@code l}), italic ({@code o}),
     * underlined ({@code n}), strikethrough ({@code m})
     * can be used. These stack on top of each other
     * which means that a single segment can use
     * multiple attributes: {@code &m&lmessage}</p>
     *
     * <p>To reset all of the styles the code {@code r}
     * can be used.</p>
     *
     * <p>So a message with the standard Minecraft color codes
     * would look like this:</p> <br>
     *
     * <pre>
     *     {@code &4&lThis&r is a &1simple&r &8&nexamp&lle &4message}
     * </pre>
     *
     * <br>
     *
     * <!-- Separate the two parts -->
     * <hr>
     * <br>
     *
     * <p>To use Hexadecimal colors the message must contain
     * the identifier (which is {@code &}) and a
     * {@code #} symbol. After that the hex
     * color code will start.
     * The hex color code will stop when there is
     * a character that is not in the range of
     * {@code 0}-{@code f} or there is a {@code ;}. </p>
     *
     * <p>So when a message starts with a letter like an {@code a}
     * the message should use a {@code ;} so that this
     * letter is not read as a part of the hex color code.</p>
     *
     * <p>When the hex color code is not valid it will be read
     * as a normal string and included into the message.</p>
     *
     * <p>So a message with hex color codes would look
     * like this:</p> <br>
     *
     * <pre>
     *     {@code &#da6000;A simple&r test &#B7088Amessage.}
     * </pre>
     *
     * <hr>
     * <br>
     *
     * <p>Both of the ways of formatting a message - as seen
     * in the previous example - can be used together.</p>
     *
     * @param message The raw message as a string
     * @return An array of TextComponents that resemble the message
     */
    public static TextComponent[] format(String message) {
        MessageIterator messageIterator = new MessageIterator(message);

        List<TextComponent> textComponentList = new ArrayList<>();

        TextComponent nextItem = messageIterator.toNext();

        while(nextItem != null) {
            if(!nextItem.getText().equals("")) {
                textComponentList.add(nextItem);
            }

            nextItem = messageIterator.toNext();
        }

        return textComponentList.toArray(new TextComponent[0]);
    }
}
