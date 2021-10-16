package net.quickwrite.noplayernotifier;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.quickwrite.noplayernotifier.data.Messages;
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

        Configuration configuration;
        try {
             configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch(IOException e) {
            throw new RuntimeException("Unable to read configuration file", e);
        }

        Messages messages = new Messages(
                configuration.getString("prefix"),
                configuration.getString("msg_nobody_online_server"),
                configuration.getString("msg_nobody_online_bungee")
        );

        getProxy().getPluginManager().registerListener(this, new MessageListener(messages));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
