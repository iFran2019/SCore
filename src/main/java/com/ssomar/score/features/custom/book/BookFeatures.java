package com.ssomar.score.features.custom.book;

import com.ssomar.score.SCore;
import com.ssomar.score.features.*;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.list.ListColoredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ResetSetting;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class BookFeatures extends FeatureWithHisOwnEditor<BookFeatures, BookFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItem {

    private BooleanFeature enable;
    private ColoredStringFeature author;
    private ColoredStringFeature title;
    private ListColoredStringFeature pages;

    public BookFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.bookFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.enable = new BooleanFeature(this, false, FeatureSettingsSCore.enable);
        this.author = new ColoredStringFeature(this, Optional.empty(), FeatureSettingsSCore.author);
        this.title = new ColoredStringFeature(this, Optional.empty(), FeatureSettingsSCore.title);
        this.pages = new ListColoredStringFeature(this, new ArrayList<>(), FeatureSettingsSCore.pages, Optional.empty());

    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            error.addAll(this.enable.load(plugin, section, isPremiumLoading));
            error.addAll(this.title.load(plugin, section, isPremiumLoading));
            error.addAll(this.author.load(plugin, section, isPremiumLoading));
            error.addAll(this.pages.load(plugin, section, isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.enable.save(section);
        this.title.save(section);
        this.author.save(section);
        this.pages.save(section);
    }

    public BookFeatures getValue() {
        return this;
    }

    @Override
    public BookFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 5] = GUI.PREMIUM;
        else finalDescription[finalDescription.length - 5] = GUI.CLICK_HERE_TO_CHANGE;
        if (enable.getValue())
            finalDescription[finalDescription.length - 4] = "&7Enabled: &a&l✔";
        else
            finalDescription[finalDescription.length - 4] = "&7Enabled: &c&l✘";

        finalDescription[finalDescription.length - 3] = "&7Title: &e" + title.getValue().orElse("&cNONE");

        finalDescription[finalDescription.length - 2] = "&7Author: &e" + author.getValue().orElse("&cNONE");

        finalDescription[finalDescription.length - 1] = "&7Pages: &e" + pages.getValues().size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public BookFeatures clone(FeatureParentInterface newParent) {
        BookFeatures dropFeatures = new BookFeatures(newParent);
        dropFeatures.setEnable(this.enable.clone(dropFeatures));
        dropFeatures.setTitle(this.title.clone(dropFeatures));
        dropFeatures.setAuthor(this.author.clone(dropFeatures));
        dropFeatures.setPages(this.pages.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.enable);
        features.add(this.title);
        features.add(this.author);
        features.add(this.pages);
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof BookFeatures) {
                BookFeatures dropFeatures = (BookFeatures) feature;
                dropFeatures.setEnable(this.enable);
                dropFeatures.setTitle(this.title);
                dropFeatures.setAuthor(this.author);
                dropFeatures.setPages(this.pages);
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
        GenericFeatureParentEditorManager.getInstance().startEditing(player, this);
    }

    public String getSimpleLocString(Location loc) {
        return loc.getWorld().getName() + "-" + loc.getBlockX() + "-" + loc.getBlockY() + "-" + loc.getBlockZ();
    }

    @Override
    public boolean isAvailable() {
        return !SCore.is1v11Less();
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForItemArgs args) {
        return args.getMeta() instanceof BookMeta;
    }

    @Override
    public void applyOnItemMeta(@NotNull FeatureForItemArgs args) {

        if(isAvailable() && isApplicable(args)){
            BookMeta bookMeta = (BookMeta) args.getMeta();
            if (getAuthor().getValue().isPresent())
                bookMeta.setAuthor(StringConverter.coloredString(getAuthor().getValue().get()));
            if (getTitle().getValue().isPresent())
                bookMeta.setTitle(StringConverter.coloredString(getTitle().getValue().get()));
            if (!getPages().getValue().isEmpty()) {
                List<String> pages = new ArrayList<>();
                for (String page : getPages().getValue()) pages.add(StringConverter.coloredString(page));
                bookMeta.setPages(pages);
            }
        }
    }

    @Override
    public void loadFromItemMeta(@NotNull FeatureForItemArgs args) {

        if(isAvailable() && isApplicable(args)){
            BookMeta bookMeta = (BookMeta) args.getMeta();
            if (bookMeta.hasAuthor())
                getAuthor().setValue(bookMeta.getAuthor());
            if (bookMeta.hasTitle())
                getTitle().setValue(bookMeta.getTitle());
            if (!bookMeta.getPages().isEmpty()) {
                List<String> pages = new ArrayList<>(bookMeta.getPages());
                getPages().setValues(pages);
            }
        }
    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.BOOK;
    }
}
