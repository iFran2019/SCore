package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;

/* STRIKELIGHTNING */
public class StrikeLightning extends EntityCommand{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
		if(!entity.isDead()) {
			entity.getWorld().strikeLightningEffect(entity.getLocation());
			if(entity instanceof Creeper) {
				((Creeper)entity).setPowered(true);
			}
		}
	}

	@Override
	public String verify(List<String> args) {
		return "";
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("STRIKELIGHTNING");
		return names;
	}

	@Override
	public String getTemplate() {
		return "STRIKELIGHTNING";
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
