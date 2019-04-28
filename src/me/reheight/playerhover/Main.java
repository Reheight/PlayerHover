package me.reheight.playerhover;

import me.reheight.playerhover.events.ChatHover;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Economy econ = null;
    private static Permission perms = null;
    private ChatHover chatHover = new ChatHover();
    @Override
    public void onEnable() {
        if (!setupEconomy())
        {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!setupPermissions())
        {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("PlayerHover enabled");

        getServer().getPluginManager().registerEvents(chatHover, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerHover disabled");
    }


    private boolean setupEconomy()
    {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = (Economy)rsp.getProvider();
        return econ != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }


    public static Permission getPermissions() {
        return perms;
    }
}
