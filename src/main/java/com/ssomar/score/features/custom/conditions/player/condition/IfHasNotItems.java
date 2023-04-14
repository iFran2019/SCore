package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.ifhas.items.group.HasItemGroupFeature;
import com.ssomar.score.utils.messages.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfHasNotItems extends PlayerConditionFeature<HasItemGroupFeature, IfHasNotItems> {

    public IfHasNotItems(FeatureParentInterface parent) {
        super(parent, "ifHasNotItems", "If has not items", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        boolean verif = (!getCondition().verifHasNot(player.getInventory().getContents(), player.getInventory().getHeldItemSlot()));
        //SsomarDev.testMsg("ifHasNotItems >>"+hasCondition()+" >> "+verif, true);
        if (hasCondition() && verif) {
            //SsomarDev.testMsg("ifHasNotItems >> PASSE FALSE ", true);
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        //SsomarDev.testMsg("ifHasNotItems >>>>>>>>>>>>>>  "+verif, true);
        return true;
    }

    @Override
    public IfHasNotItems getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new HasItemGroupFeature(getParent(), "ifHasNotItems", "If has not items", new String[]{}, Material.STONE, false, true));
    }

    @Override
    public boolean hasCondition() {
        return !getCondition().getHasItems().isEmpty();
    }

    @Override
    public IfHasNotItems getNewInstance(FeatureParentInterface parent) {
        return new IfHasNotItems(parent);
    }
}
