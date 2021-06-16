package com.ssomar.score.usedapi;

import java.util.List;
import java.util.Optional;

import org.bukkit.entity.Player;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;

public class IridiumSkyblockTool {

	public static boolean playerIsOnHisIsland(Player player) {
		
		Optional<Island> islandOpt = IridiumSkyblockAPI.getInstance().getIslandViaLocation(player.getLocation());
		if(!islandOpt.isEmpty()) {
			Island island = islandOpt.get();
			List<User> members = island.getMembers();
			for(User user : members) {
				if(player.getUniqueId().equals(user.getUuid())) return true;
			}
		}

		return false;
	}
}
