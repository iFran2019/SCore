package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfPlayerHealth extends PlayerConditionFeature<NumberConditionFeature, IfPlayerHealth> {

    public IfPlayerHealth(FeatureParentInterface parent) {
        super(parent, "ifPlayerHealth", "If player health", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(Optional.of(player), messageSender.getSp()).get(), player.getHealth())) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public IfPlayerHealth getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, "ifPlayerHealth", "If player health", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfPlayerHealth getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerHealth(parent);
    }
}
