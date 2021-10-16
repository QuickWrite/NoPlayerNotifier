package net.quickwrite.noplayernotifier.data;

/**
 *
 * @author QuickWrite
 */
public class Messages {
    private final String prefix;
    private final String messageServer;
    private final String messageBungee;

    /**
     *
     * @param messageServer The message when nobody is on the same server
     * @param messageBungee The message when nobody is on the same bungee
     */
    public Messages(String prefix, String messageServer, String messageBungee) {
        if(prefix == "")
            this.prefix = prefix;
        else
            this.prefix = null;

        this.messageServer = messageServer;
        this.messageBungee = messageBungee;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getMessageServer() {
        return this.messageServer;
    }

    public String getMessageBungee() {
        return this.messageBungee;
    }
}
