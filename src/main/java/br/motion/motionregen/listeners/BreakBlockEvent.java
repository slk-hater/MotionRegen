package br.motion.motionregen.listeners;

import br.motion.motionregen.Core;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.sk89q.worldedit.Vector;

public class BreakBlockEvent implements Listener {
    protected boolean isInRegion(Location loc, String region) {
        if (Core.getInstance().getWorldGuard() != null) {
            Vector v = new Vector(loc.getX(), loc.getBlockY(), loc.getZ());
            return Core.getInstance().getWorldGuard().getRegionManager(loc.getWorld()).getApplicableRegionsIDs(v).contains(region);
        }return false;
    }
    @EventHandler
    public void onBlockBreakEvent(org.bukkit.event.block.BlockBreakEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Material material = block.getType();
        Location location = block.getLocation();
        FileConfiguration config = Core.getInstance().getConfig();
        if(player.getGameMode() == GameMode.SURVIVAL && material.toString().contains("_ORE") && !player.hasPermission("motion.quebrar"+material.toString())) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cVocê não pode quebrar &4"+material.toString()+"&c."));
        }
        if(isInRegion(location, config.getString("worldguard-regiao"))) {
            for(String s : config.getStringList("regen.black-list")) if(block.getType() == Material.matchMaterial(s)) e.setCancelled(true);
            for(String key : config.getConfigurationSection("regen.white-list").getKeys(false)) if(block.getType() == Material.matchMaterial(key)) Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> location.getBlock().setType(Material.valueOf(key)), config.getInt("regen.white-list." + key) * 20L);
        }
    }
}
