package com.robustgames.robustclient.business.entitiy.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.ImageView;

public class SelectableTargetComponent extends Component {

    private ImageView targetSelectionMarker;

    @Override
    public void onAdded() {
        System.out.println("SelectableTargetComponent hinzugefügt zu: " + entity);

        // Lade das rote Rechteck (z.B. 72x72)
        targetSelectionMarker = FXGL.getAssetLoader().loadTexture("targetselection.png");

        // Verschiebung für Zentrierung
        targetSelectionMarker.setTranslateX(-36); // oder -32 je nach Größe
        targetSelectionMarker.setTranslateY(-36);

        // Nach vorne holen – falls es überdeckt wird
        targetSelectionMarker.toFront();

        entity.getViewComponent().addChild(targetSelectionMarker);
    }

    @Override
    public void onRemoved() {
        if (entity.getViewComponent().getChildren().contains(targetSelectionMarker)) {
            entity.getViewComponent().removeChild(targetSelectionMarker);
        }
    }
}