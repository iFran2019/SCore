package com.ssomar.score.features.custom.Instrument;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.MusicIntrusmentFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class InstrumentFeatures extends FeatureWithHisOwnEditor<InstrumentFeatures, InstrumentFeatures, InstrumentFeaturesEditor, InstrumentFeaturesEditorManager> {

    private BooleanFeature enable;
    private MusicIntrusmentFeature instrument;

    public InstrumentFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.instrumentFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.enable = new BooleanFeature(this, false, FeatureSettingsSCore.enable, false);
        this.instrument = new MusicIntrusmentFeature(this, Optional.empty(), FeatureSettingsSCore.instrument);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (isPremiumLoading && config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            error.addAll(this.enable.load(plugin, section, isPremiumLoading));
            error.addAll(this.instrument.load(plugin, section, isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.enable.save(section);
        this.instrument.save(section);
    }

    public InstrumentFeatures getValue() {
        return this;
    }

    @Override
    public InstrumentFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 3] = GUI.PREMIUM;
        else finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        if (enable.getValue())
            finalDescription[finalDescription.length - 2] = "&7Enabled: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Enabled: &c&l✘";

        if(instrument.getValue().isPresent())
            finalDescription[finalDescription.length - 1] = "&7Instrument: &e" + instrument.getValue().get().getKey().getKey();
        else
            finalDescription[finalDescription.length - 1] = "&7Instrument: &cNONE";


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public InstrumentFeatures clone(FeatureParentInterface newParent) {
        InstrumentFeatures dropFeatures = new InstrumentFeatures(newParent);
        dropFeatures.setEnable(this.enable.clone(dropFeatures));
        dropFeatures.setInstrument(this.instrument.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.enable);
        features.add(this.instrument);
        return features;
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return getParent().getConfigurationSection();
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof InstrumentFeatures) {
                InstrumentFeatures dropFeatures = (InstrumentFeatures) feature;
                dropFeatures.setEnable(this.enable);
                dropFeatures.setInstrument(this.instrument);
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
        InstrumentFeaturesEditorManager.getInstance().startEditing(player, this);
    }
}
