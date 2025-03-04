package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;

public class IfBlockLocationY extends BlockConditionFeature<NumberConditionFeature, IfBlockLocationY> {

    public IfBlockLocationY(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifBlockLocationY);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(request.getPlayerOpt(), request.getSp()).get(), request.getBlock().getLocation().getY())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfBlockLocationY getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifBlockLocationY));
    }

    @Override
    public IfBlockLocationY getNewInstance(FeatureParentInterface parent) {
        return new IfBlockLocationY(parent);
    }
}
