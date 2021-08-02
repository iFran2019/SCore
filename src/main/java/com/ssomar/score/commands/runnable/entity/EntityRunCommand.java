package com.ssomar.score.commands.runnable.entity;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.SCommand;

public class EntityRunCommand extends RunCommand{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID launcherUUID;

	private UUID entityUUID;

	private boolean silenceOutput;

	public EntityRunCommand(String brutCommand, int delay, ActionInfo aInfo) {
		super(brutCommand, delay, aInfo);
	}

	public EntityRunCommand(String brutCommand, long runTime, ActionInfo aInfo) {
		super(brutCommand, runTime, aInfo);
	}

	@Override
	public void pickupInfo() {
		ActionInfo aInfo = this.getaInfo();

		launcherUUID = aInfo.getLauncherUUID();

		entityUUID = aInfo.getEntityUUID();

		silenceOutput = aInfo.isSilenceOutput();
	}

	@Override
	public void runGetManager() {
		this.runCommand(EntityCommandManager.getInstance());
	}

	@Override
	public void runCommand(SCommand command, List<String> args) {
		EntitySCommand pCommand = (EntitySCommand) command;

		this.pickupInfo();
		
		Player launcher = Bukkit.getPlayer(launcherUUID);
		Entity receiver = Bukkit.getEntity(entityUUID);

		if(receiver != null && !receiver.isDead()) pCommand.run(launcher, receiver, args, this.getaInfo());
	}


	@Override
	public void insideDelayedCommand() {
		runCommand(EntityCommandManager.getInstance());
		CommandsHandler.getInstance().removeDelayedCommand(getUuid(), entityUUID);
	}
	public UUID getLauncherUUID() {
		return launcherUUID;
	}
	public void setLauncherUUID(UUID launcherUUID) {
		this.launcherUUID = launcherUUID;
	}
	public UUID getEntityUUID() {
		return entityUUID;
	}
	public void setEntityUUID(UUID entityUUID) {
		this.entityUUID = entityUUID;
	}
	public boolean isSilenceOutput() {
		return silenceOutput;
	}
	public void setSilenceOutput(boolean silenceOutput) {
		this.silenceOutput = silenceOutput;
	}
}
