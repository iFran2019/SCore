package com.ssomar.scoretestrecode.features.custom.conditions.world.block.parent;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.custom.conditions.world.block.WorldConditionFeature;
import com.ssomar.scoretestrecode.features.custom.conditions.world.block.condition.IfWeather;
import com.ssomar.scoretestrecode.features.custom.conditions.world.block.condition.IfWorldTime;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorldConditionsFeature extends FeatureWithHisOwnEditor<WorldConditionsFeature, WorldConditionsFeature, WorldConditionsFeatureEditor, WorldConditionsFeatureEditorManager> {

    private List<WorldConditionFeature> conditions;

    public WorldConditionsFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription) {
        super(parent, name, editorName, editorDescription, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        conditions = new ArrayList<>();
        /** List world features **/
        conditions.add(new IfWeather(this));

        /** Number condition **/
        conditions.add(new IfWorldTime(this));

    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        for(WorldConditionFeature condition : conditions) {
            error.addAll(condition.load(plugin, config, isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (WorldConditionFeature condition : conditions) {
            condition.save(section);
        }
    }

    @Override
    public WorldConditionsFeature getValue() {
        return this;
    }

    @Override
    public WorldConditionsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = "&7World condition(s) enabled: &e" + getBlockConditionEnabledCount();
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;


        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    public int getBlockConditionEnabledCount() {
        int i = 0;
        for(WorldConditionFeature condition : conditions) {
            if(condition.hasCondition()) {
                i++;
            }
        }
        return i;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public WorldConditionsFeature clone() {
        WorldConditionsFeature clone = new WorldConditionsFeature(getParent(), getName(), getEditorName(), getEditorDescription());
        List<WorldConditionFeature> clones = new ArrayList<>();
        for (WorldConditionFeature condition : conditions) {
            clones.add((WorldConditionFeature) condition.clone());
        }
        clone.setConditions(clones);
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(conditions);
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if(section.isConfigurationSection(this.getName())) {
            return section.getConfigurationSection(this.getName());
        }
        else return section.createSection(this.getName());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof WorldConditionsFeature) {
                WorldConditionsFeature bCF = (WorldConditionsFeature) feature;
                List<WorldConditionFeature> clones = new ArrayList<>();
                for (WorldConditionFeature condition : conditions) {
                    clones.add((WorldConditionFeature) condition);
                }
                bCF.setConditions(clones);
                break;
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        WorldConditionsFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
