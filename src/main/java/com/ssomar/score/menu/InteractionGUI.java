package com.ssomar.score.menu;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.activator.requiredei.RequiredEIGUIManager;
import com.ssomar.score.menu.activator.requiredei.RequiredEIsGUIManager;
import com.ssomar.score.menu.conditions.blockcdt.BlockConditionsGUIManager;
import com.ssomar.score.menu.conditions.blockcdt.BlockConditionsMessagesGUIManager;
import com.ssomar.score.menu.conditions.blockcdt.blockaroundcdt.AroundBlockConditionGUIManager;
import com.ssomar.score.menu.conditions.blockcdt.blockaroundcdt.AroundBlockConditionsGUIManager;
import com.ssomar.score.menu.conditions.customcdt.ei.CustomConditionsGUIManager;
import com.ssomar.score.menu.conditions.customcdt.ei.CustomConditionsMessagesGUIManager;
import com.ssomar.score.menu.conditions.entitycdt.EntityConditionsGUIManager;
import com.ssomar.score.menu.conditions.entitycdt.EntityConditionsMessagesGUIManager;
import com.ssomar.score.menu.conditions.home.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.itemcdt.ItemConditionsGUIManager;
import com.ssomar.score.menu.conditions.itemcdt.ItemConditionsMessagesGUIManager;
import com.ssomar.score.menu.conditions.placeholdercdt.PlaceholdersConditionGUIManager;
import com.ssomar.score.menu.conditions.placeholdercdt.PlaceholdersConditionsGUIManager;
import com.ssomar.score.menu.conditions.playercdt.PlayerConditionsGUIManager;
import com.ssomar.score.menu.conditions.playercdt.PlayerConditionsMessagesGUIManager;
import com.ssomar.score.menu.conditions.worldcdt.WorldConditionsGUIManager;
import com.ssomar.score.menu.conditions.worldcdt.WorldConditionsMessagesGUIManager;
import com.ssomar.score.utils.StringConverter;


public class InteractionGUI implements Listener{

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {

		if(!(e.getWhoClicked() instanceof Player)) return;	

		String title= e.getView().getTitle();
		Player player = (Player) e.getWhoClicked();
		try {
			if(e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
		}catch(NullPointerException error) {
			return;
		}

		int slot = e.getSlot();
		ItemStack itemS = e.getView().getItem(slot);
		//boolean notNullItem = itemS!=null;

		try {

			if(title.contains(StringConverter.coloredString("Editor - Conditions"))) {
				this.manage(player, itemS, title, "ConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - RequiredEIs"))) {
				this.manage(player, itemS, title, "RequiredEIsGUIManager", e);
			}
			
			else if(title.contains(StringConverter.coloredString("Editor - RequiredEI"))) {
				this.manage(player, itemS, title, "RequiredEIGUIManager", e);
			}
			
			else if(title.contains(StringConverter.coloredString("Editor - Player Conditions Messages"))) {
				this.manage(player, itemS, title, "PlayerConditionsMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Player Conditions"))) {
				this.manage(player, itemS, title, "PlayerConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - World Conditions Messages"))) {
				this.manage(player, itemS, title, "WorldConditionsMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - World Conditions"))) {
				this.manage(player, itemS, title, "WorldConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Entity Conditions Messages"))) {
				this.manage(player, itemS, title, "EntityConditionsMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Entity Conditions"))) {
				this.manage(player, itemS, title, "EntityConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Placeholders Conditions"))) {
				this.manage(player, itemS, title, "PlaceholdersConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Plch condition"))) {
				this.manage(player, itemS, title, "PlaceholdersConditionGUIManager", e);
			}
			
			else if(title.contains(StringConverter.coloredString("Editor - Around block Conditions"))) {
				this.manage(player, itemS, title, "AroundBlockConditionsGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Around-Block condition"))) {
				this.manage(player, itemS, title, "AroundBlockConditionGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Item Conditions Messages"))) {
				this.manage(player, itemS, title, "ItemConditionMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Item Conditions"))) {
				this.manage(player, itemS, title, "ItemConditionGUIManager", e);
			}
			
			else if(title.contains(StringConverter.coloredString("Editor - Block Conditions Messages"))) {
				this.manage(player, itemS, title, "BlockConditionMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Block Conditions"))) {
				this.manage(player, itemS, title, "BlockConditionGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Custom Conditions Messages"))) {
				this.manage(player, itemS, title, "CustomConditionsMessagesGUIManager", e);
			}

			else if(title.contains(StringConverter.coloredString("Editor - Custom Conditions"))) {
				this.manage(player, itemS, title, "CustomConditionsGUIManager", e);
			}

		}catch (NullPointerException error) {
			error.printStackTrace();
		}
	}

	public void manage(Player player, ItemStack itemS, String title, String guiType, InventoryClickEvent e) {

		e.setCancelled(true);

		if(itemS == null) return;

		if(!itemS.hasItemMeta()) return;

		if(itemS.getItemMeta().getDisplayName().isEmpty()) return;

		//String itemName = itemS.getItemMeta().getDisplayName();

		boolean isShiftLeft = e.getClick().equals(ClickType.SHIFT_LEFT);
		
		switch (guiType) {
		case "ConditionsGUIManager":
			ConditionsGUIManager.getInstance().clicked(player, itemS);
			break;

		case "RequiredEIsGUIManager":
			if(isShiftLeft) {
				RequiredEIsGUIManager.getInstance().shiftLeftClicked(player, itemS, title);
			}
			else RequiredEIsGUIManager.getInstance().clicked(player, itemS, title);
			break;
			
		case "RequiredEIGUIManager":
			RequiredEIGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "EntityConditionsGUIManager":
			EntityConditionsGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "EntityConditionsMessagesGUIManager":
			EntityConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "WorldConditionsMessagesGUIManager":
			 WorldConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "WorldConditionsGUIManager":
			WorldConditionsGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "PlayerConditionsMessagesGUIManager":
			PlayerConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "PlayerConditionsGUIManager":
			PlayerConditionsGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "PlaceholdersConditionsGUIManager":
			PlaceholdersConditionsGUIManager.getInstance().clicked(player, itemS, title, e.getClick());
			break;

		case "PlaceholdersConditionGUIManager":
			PlaceholdersConditionGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;
			
		case "AroundBlockConditionsGUIManager":
			AroundBlockConditionsGUIManager.getInstance().clicked(player, itemS, title, e.getClick());
			break;

		case "AroundBlockConditionGUIManager":
			AroundBlockConditionGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "ItemConditionMessagesGUIManager":
			ItemConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "ItemConditionGUIManager":
			ItemConditionsGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;
			
		case "BlockConditionMessagesGUIManager":
			BlockConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "BlockConditionGUIManager":
			BlockConditionsGUIManager.getInstance().clicked(player, itemS,e.getClick());
			break;

		case "CustomConditionsMessagesGUIManager":
			CustomConditionsMessagesGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		case "CustomConditionsGUIManager":
			CustomConditionsGUIManager.getInstance().clicked(player, itemS, e.getClick());
			break;

		default:
			break;
		}
	}


	public String getActually(ItemStack item) {
		List<String> lore = item.getItemMeta().getLore();
		for(String s: lore) {
			if(StringConverter.decoloredString(s).contains("actually: ")) return StringConverter.decoloredString(s).split("actually: ")[1];
		}
		return null;
	}



	@EventHandler(priority = EventPriority.LOWEST)
	public void onChatEvent(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();
		if(PlayerConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			PlayerConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(PlayerConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			PlayerConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(BlockConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			BlockConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(EntityConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			EntityConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(EntityConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			EntityConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(WorldConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			WorldConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(WorldConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			WorldConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(PlaceholdersConditionGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			PlaceholdersConditionGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(AroundBlockConditionGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			AroundBlockConditionGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(ItemConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			ItemConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(ItemConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			ItemConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(BlockConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			BlockConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(CustomConditionsMessagesGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			CustomConditionsMessagesGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(CustomConditionsGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			CustomConditionsGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
		else if(RequiredEIGUIManager.getInstance().getRequestWriting().containsKey(p)) {
			e.setCancelled(true);
			RequiredEIGUIManager.getInstance().receivedMessage(p, e.getMessage());
		}
	}	
}
