package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class IfIsInTheAir extends PlayerConditionFeature<BooleanFeature, IfIsInTheAir> {


    public IfIsInTheAir(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifIsInTheAir);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Player player = request.getPlayer();
            Location pLoc = player.getLocation();
            pLoc.subtract(0, 0.1, 0);

            Block block = pLoc.getBlock();
            Material type = block.getType();
            if (!(type.equals(Material.AIR)
                    || (SCore.is1v17Plus() && (type.equals(Material.LIGHT) || type.equals(Material.CAVE_AIR)))
                    || (SCore.is1v18Plus() && type.equals(Material.VOID_AIR))

            )) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfIsInTheAir getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(this, false, FeatureSettingsSCore.ifIsInTheAir));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfIsInTheAir getNewInstance(FeatureParentInterface parent) {
        return new IfIsInTheAir(parent);
    }
}
