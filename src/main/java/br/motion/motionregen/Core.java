package br.motion.motionregen;

import br.motion.motionregen.listeners.BreakBlockEvent;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {
    public static Core instance;
    public static Core getInstance() {
        return instance;
    }
    private void setInstance(Core instance) {
        Core.instance = instance;
    }
    public void onEnable() {
        saveDefaultConfig();
        setInstance(this);
        Bukkit.getPluginManager().registerEvents(new BreakBlockEvent(), this);
    }
    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        if ((!(plugin instanceof WorldGuardPlugin))) {
            return null;
        }
        return (WorldGuardPlugin)plugin;
    }
}
