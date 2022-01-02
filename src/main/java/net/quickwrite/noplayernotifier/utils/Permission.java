package net.quickwrite.noplayernotifier.utils;

/**
 * @author QuickWrite
 */
public enum Permission {
    RELOAD,
    BYPASS;

    /**
     * Returns a String variant of the permission
     * with a <code>noplayernotifier.</code> in front
     *
     * @return The whole permission
     */
    public String toString() {
        return "noplayernotifier." + super.toString().toLowerCase();
    }
}
