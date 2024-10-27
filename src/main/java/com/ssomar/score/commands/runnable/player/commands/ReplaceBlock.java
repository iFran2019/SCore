package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReplaceBlock extends PlayerCommand {

    public ReplaceBlock() {
        CommandSetting material = new CommandSetting("material", 0, Material.class, Material.STONE);
        List<CommandSetting> settings = getSettings();
        settings.add(material);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        Material material = (Material) sCommandToExec.getSettingValue("material");

        Block block = receiver.getTargetBlock(null, 5);
        if (block.getType() != Material.AIR) {
            if (material != null) {
                if (SCore.hasWorldGuard) {
                    if (new WorldGuardAPI().canBuild(receiver, new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()))) {
                        block.setType(material);
                    }
                } else {
                    block.setType(material);
                }
            } else {
                RunConsoleCommand.runConsoleCommand("execute at " + receiver.getName() + " run setblock " + block.getX() + " " + block.getY() + " " + block.getZ() + " " + material.toString().toLowerCase(), sCommandToExec.getActionInfo().isSilenceOutput());
            }
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REPLACE_BLOCK");
        names.add("REPLACEBLOCK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REPLACE_BLOCK material:STONE";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }
}
