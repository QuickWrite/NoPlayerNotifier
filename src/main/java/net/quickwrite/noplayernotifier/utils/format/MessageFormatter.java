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
     * Formats the message with color codes
     * and custom hex codes. <br>
     *
     * All of the color codes are case-insensitive. <br><br>
     *
     * The color codes from <code>1</code>-<code>f</code> can
     * be used with an <code>&</code>.
     * They behave exactly like the color codes
     * Minecraft itself gives with the <code>§</code>. <br><br>
     *
     * Also the attributes that make the message
     * bold (<code>l</code>), italic (<code>o</code>),
     * underlined (<code>n</code>), strikethrough (<code>m</code>)
     * can be used. These stack on top of each other
     * which means that a single segment can use
     * multiple attributes: <code>&m&lmessage</code> <br><br>
     *
     * To reset all of the styles the code <code>r</code>
     * can be used. <br>
     *
     * So a message with the standard Minecraft color codes
     * would look like this: <br><br>
     *
     * <code>&4&lThis&r is a &1simple&r &8&nexamp&lle &4message</code>
     *
     * <br><br>
     * <!-- Separate the two parts -->
     * <hr>
     *
     * To use Hexadecimal colors the message must contain
     * the identifier (which is <code>&</code>) and a
     * <code>#</code> symbol. After that the hex
     * color code will start.
     * The hex color code will stop when there is
     * a character that is not in the range of
     * <code>0</code>-<code>f</code> or there is a <code>;</code>. <br><br>
     *
     * So when a message starts with a letter like an <code>a</code>
     * the message should use a <code>;</code> so that this
     * letter is not read as a part of the hex color code. <br><br>
     *
     * When the hex color code is not valid it will be read
     * as a normal string and included into the message. <br><br>
     *
     * So a message with hex color codes would look
     * like this: <br><br>
     *
     * <code>&#da6000;A simple&r test &#B7088Amessage.</code><br><br>
     *
     * <hr>
     *
     * Both of the ways of formatting a message - as seen
     * in the previous example - can be used together.
     *
     * @param message The raw message as a string
     * @return An array of TextComponents that resemble the message
     */
    public static TextComponent[] format(String message) {
        MessageIterator messageIterator = new MessageIterator(message);

        List<TextComponent> textComponentList = new ArrayList<>();

        TextComponent nextItem = messageIterator.toNext();

        while(nextItem != null) {
            textComponentList.add(nextItem);

            nextItem = messageIterator.toNext();
        }

        return textComponentList.toArray(new TextComponent[0]);
    }
}
