package net.quickwrite.noplayernotifier;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.quickwrite.noplayernotifier.commands.NPNReload;
import net.quickwrite.noplayernotifier.data.Config;
import net.quickwrite.noplayernotifier.listeners.MessageListener;

import java.io.*;

/**
 * The main class of the plugin
 *
 * @author QuickWrite
 */
public final class NoPlayerNotifier extends Plugin {
    @Override
    public void onEnable() {
        Config config = getConfig(getConfiguration());

        PluginManager pluginManager = this.getProxy().getPluginManager();

        MessageListener messageListener = new MessageListener(config);

        pluginManager.registerCommand(this, new NPNReload(this, messageListener));
        pluginManager.registerListener(this, messageListener);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Gets the configuration file of the plugin
     * as a Configuration object
     *
     * Creates a new directory with a config when
     * no config is available. The config is
     * loaded from the config.yml in the
     * resources folder.
     *
     * @return A Configuration object
     */
    public Configuration getConfiguration() {
        if (!getDataFolder().exists()) {
            if(!getDataFolder().mkdir())
                throw new RuntimeException("Unable to create the data folder directory");
        }

        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                if(file.createNewFile())
                    throw new RuntimeException();

                try (InputStream is = getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(file)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }

        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch(IOException e) {
            throw new RuntimeException("Unable to read configuration file", e);
        }
    }

    public Config getConfig(Configuration configuration) {
        return new Config(
                configuration.getString("prefix"),
                configuration.getString("msg_nobody_online_server"),
                configuration.getString("msg_nobody_online_bungee")
        );
    }
}
