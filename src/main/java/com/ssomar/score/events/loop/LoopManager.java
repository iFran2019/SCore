package com.ssomar.score.events.loop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ssomar.executableblocks.blocks.activators.ActivatorEB;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.executableitems.items.activators.ActivatorEI;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.sactivator.SActivator;

public class LoopManager {

	private static LoopManager instance;

	public static final int DELAY = 5;

	private Map<SActivator, Integer> loopActivators = new HashMap<>();

	public void setup() {
		this.runLoop();
	}


	public void runLoop() {
		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				List<SActivator> toActiv = new ArrayList<>();
				for(SActivator activator: loopActivators.keySet()) {
					int delay;
					if((delay = loopActivators.get(activator)) != 0) {
						int toRemove = LoopManager.DELAY;
						if(delay <= toRemove) toRemove = delay;	
						loopActivators.put(activator, delay-toRemove);
						continue;
					}
					else {
						toActiv.add(activator);
						if(activator.isDelayInTick()) loopActivators.put(activator, activator.getDelay());
						else loopActivators.put(activator, activator.getDelay()*20);
					}
				}
				if(!toActiv.isEmpty()) {

					if(SCore.hasExecutableItems) {	
						List<ActivatorEI> listEI = new ArrayList<>();

						for(SActivator sActivator : toActiv) {
							if(sActivator instanceof ActivatorEI) {
								listEI.add((ActivatorEI) sActivator);
							}
						}

						if(!listEI.isEmpty()) {
							for(Player player : Bukkit.getServer().getOnlinePlayers()) {
								LoopEvent e = new LoopEvent();

								com.ssomar.executableitems.events.EventInfos eInfo = new com.ssomar.executableitems.events.EventInfos(e, e);
								eInfo.setPlayer(player);
								com.ssomar.executableitems.events.EventsManager.getInstance().manage(eInfo, listEI);

							}
						}

					}

					if(SCore.hasExecutableBlocks) {	
						List<ActivatorEB> listEB = new ArrayList<>();

						for(SActivator sActivator : toActiv) {
							if(sActivator instanceof ActivatorEB) {
								listEB.add((ActivatorEB) sActivator);
							}
						}

						if(!listEB.isEmpty()) {
							Map<Location, ExecutableBlockPlaced> mapEBP = ExecutableBlockPlacedManager.getInstance().getExecutableBlocksPlaced();
							for(Location loc : mapEBP.keySet()) {

								ExecutableBlockPlaced eBP =  mapEBP.get(loc);
								if(!eBP.hasLoop()) continue;
								LoopEvent e = new LoopEvent();

								com.ssomar.executableblocks.events.EventInfos eInfo = new com.ssomar.executableblocks.events.EventInfos(e, e);
								com.ssomar.executableblocks.events.EventsManager.getInstance().manage(eBP, eInfo, listEB);
							}
						}

					}
				}
			}
		};
		runnable.runTaskTimerAsynchronously(SCore.plugin, 0L, DELAY);
	}


	public void addLoopActivators(ActivatorEI activator) {
		loopActivators.put(activator, activator.getDelay());
	}

	public void resetLoopActivatorsEB() {
		Map<SActivator, Integer> update = new HashMap<>();
		for(SActivator sAct : loopActivators.keySet()) {
			if(!(sAct instanceof ActivatorEB)) update.put(sAct, loopActivators.get(sAct));
		}
		loopActivators = update;
	}
	
	public void resetLoopActivatorsEI() {
		Map<SActivator, Integer> update = new HashMap<>();
		for(SActivator sAct : loopActivators.keySet()) {
			if(!(sAct instanceof ActivatorEI)) update.put(sAct, loopActivators.get(sAct));
		}
		loopActivators = update;
	}

	public static LoopManager getInstance() {
		if(instance == null)  instance = new LoopManager();
		return instance;
	}

	public Map<SActivator, Integer> getLoopActivators() {
		return loopActivators;
	}

	public void setLoopActivators(Map<SActivator, Integer> loopActivators) {
		this.loopActivators = loopActivators;
	}
}
