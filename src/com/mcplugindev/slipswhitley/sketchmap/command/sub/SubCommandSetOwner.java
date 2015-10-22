package com.mcplugindev.slipswhitley.sketchmap.command.sub;

import com.mcplugindev.slipswhitley.sketchmap.SketchMapAPI;
import com.mcplugindev.slipswhitley.sketchmap.SketchMapUtils;
import com.mcplugindev.slipswhitley.sketchmap.command.SketchMapSubCommand;
import com.mcplugindev.slipswhitley.sketchmap.map.SketchMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandSetOwner extends SketchMapSubCommand
{
    @Override
    public String getSub()
    {
        return "setowner";
    }

    @Override
    public String getPermission()
    {
        return "sketchmap.privacy";
    }

    @Override
    public String getDescription()
    {
        return "Sets the owner of a SketchMap";
    }

    @Override
    public String getSyntax()
    {
        return "/sketchmap setowner <MAP-ID> <NEW-OWNER-NAME>";
    }

    @Override
    public void onCommand(final CommandSender sender, final String[] args, final String prefix)
    {
        if (args.length != 2)
        {
            sender.sendMessage(ChatColor.RED + prefix + "Error in Command Syntax. Try, \"" + this.getSyntax() + "\"");
            return;
        }

        SketchMap map = SketchMapAPI.getMapByID(args[0]);
        if (map == null)
        {
            sender.sendMessage(ChatColor.RED + prefix + "Could not find Map \"" + args[0].toLowerCase() + "\"");
            return;
        }

        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            if (!player.getUniqueId().equals(map.getOwnerUUID()) && !SketchMapUtils.hasPermission(player, "sketchmap.privacy.admin"))
            {
                player.sendMessage(ChatColor.RED + prefix + "You are not the owner of that map!");
                return;
            }
        }

        Player newOwner = Bukkit.getServer().getOfflinePlayer(args[1]).getPlayer();
        map.setOwnerUUID(newOwner.getUniqueId());
    }
}