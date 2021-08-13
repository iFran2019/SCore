package com.ssomar.score.commands.runnable.util.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.ssomar.score.commands.runnable.SCommand;

public class Nothing implements SCommand{

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("NOTHING");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "NOTHING*{number}";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return ChatColor.YELLOW;
	}

	@Override
	public ChatColor getExtraColor() {
		// TODO Auto-generated method stub
		return ChatColor.GOLD;
	}
}
