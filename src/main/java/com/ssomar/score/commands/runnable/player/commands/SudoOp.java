package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.commands.runnable.player.commands.sudoop.SUDOOPManager;

/* SUDOOP {command} */
public class SudoOp extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		StringBuilder command2 = new StringBuilder();
		for(String s: args) {
			command2.append(s).append(" ");
		}
		command2 = new StringBuilder(command2.substring(0, command2.length() - 1));
		SUDOOPManager.getInstance().runOPCommand(receiver, command2.toString());
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String sudoop= "SUDOOP {command}";
		if(args.size()<1) error = notEnoughArgs+sudoop;

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SUDOOP");
		return names;
	}

	@Override
	public String getTemplate() {
		return "SUDOOP {command}";
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
