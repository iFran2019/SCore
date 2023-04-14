package com.ssomar.score.features.custom.activators.activator;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.loop.LoopFeatures;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.HigherFormSObject;
import com.ssomar.score.sobject.sactivator.EventInfo;
import com.ssomar.score.sobject.sactivator.SOption;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

public abstract class NewSActivator<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends FeatureWithHisOwnEditor<X, X, Y, Z> {

    @Getter
    private final String id;

    @Getter
    private SPlugin sPlugin;

    public NewSActivator(SPlugin sPlugin, FeatureParentInterface parent, String id) {
        super(parent, "activator", TM.g(Text.FEATURES_ACTIVATOR_NAME), TM.gA(Text.FEATURES_ACTIVATOR_DESCRIPTION), Material.BEACON, false);
        this.id = id;
        this.sPlugin = sPlugin;
    }

    public abstract String getParentObjectId();

    public abstract SOption getOption();

    public abstract List<FeatureInterface> getFeatures();

    public abstract LoopFeatures getLoopFeatures();

    public abstract void run(HigherFormSObject parentObject, EventInfo eventInfo);

    public abstract List<String> getMenuDescription();

    /**
     * Return a new instance of the NewActivator
     **/
    public abstract NewSActivator getBuilderInstance(FeatureParentInterface parent, String id);

    public boolean isEqualsOrAClone(NewSActivator activator) {
        return this.getClass().equals(activator.getClass()) && this.id.equals(activator.id) && this.getParentObjectId().equals(activator.getParentObjectId());
    }

    public abstract List<X> extractActivatorsSameClass(List<NewSActivator> toActiv);
    public abstract void activateOptionGlobal(SOption sOption, EventInfo eventInfo, List<NewSActivator> toActiv);
}
