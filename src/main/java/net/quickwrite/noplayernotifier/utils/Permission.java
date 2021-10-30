package net.quickwrite.noplayernotifier.utils;

/**
 * @author QuickWrite
 */
public enum Permission {
    RELOAD ("reload"),
    BYPASS ("bypass");

    private final String value;

    /**
     * The permissions for the plugin
     *
     * @param value The permission itself
     */
    Permission(String value) {
        this.value = value;
    }

    /**
     * Returns a String variant of the permission
     * with a <code>noplayernotifier.</code> in front
     *
     * @return The whole permission
     */
    public String toString() {
        return "noplayernotifier." + value;
    }
}
