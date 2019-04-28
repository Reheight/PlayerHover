package me.reheight.playerhover.events;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import me.reheight.playerhover.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import static me.reheight.playerhover.Main.econ;

public class ChatHover implements Listener {
    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {
        String format = event.getFormat();
        TextComponent newFormat = new TextComponent(format);

        PermissionUser pexUser = PermissionsEx.getUser(event.getPlayer());

        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(event.getPlayer());
        Faction faction = fPlayer.getFaction();

        HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color("" +
                "&dName: &f" + event.getPlayer().getName() +
                "\n" + "&dBalance: &f" + econ.format(econ.getBalance(event.getPlayer())) +
                "\n" + "&dRank: &f" + Main.getPermissions().getPrimaryGroup(event.getPlayer()) +
                "\n" + "&dFaction: &f" + faction.getTag() +
                "\n" + "&dPower: &f" + Math.round(fPlayer.getPower()) + "/" + Math.round(fPlayer.getPowerMax()))).create());

        newFormat.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + event.getPlayer().getName()));
        newFormat.setHoverEvent(hover);
        newFormat.setText(event.getPlayer().getDisplayName() + color(" &8» &r") + event.getMessage());
        event.setCancelled(true);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(newFormat);
        }
    }
}
