package net.quickwrite.noplayernotifier.utils;

/**
 * The permissions that the plugin is checking.
 *
 * @author QuickWrite
 */
public enum Permission {
    /**
     * The permission for the {@code reload} command.
     */
    RELOAD,

    /**
     * The permission so that no message will be send.
     */
    BYPASS;

    /**
     * Returns a String variant of the permission
     * with a {@code noplayernotifier.} in front
     *
     * @return The whole permission
     */
    public String toString() {
        return "noplayernotifier." + super.toString().toLowerCase();
    }
}
