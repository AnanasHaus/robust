package com.robustgames.robustclient.business.factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.robustgames.robustclient.business.logic.MovementService;
import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import static com.robustgames.robustclient.business.entitiy.EntityType.*;

/**
 * For destructible entities on our map. May later include tiles
 */
public class MapFactory implements EntityFactory {
    @Spawns("Background")
    public Entity spawnBackground(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.GREY))
                .with(new IrremovableComponent())
                .zIndex(-100)
                .build();
    }

    @Spawns("mountain")
    public Entity spawnMountain(SpawnData data) {
        return FXGL.entityBuilder(data).type(MOUNTAIN)
                .viewWithBBox("mountain.png")
                .build();
    }

    //Tile Grafik
    @Spawns("floorTile")
    public Entity spawnFloor(SpawnData data) {
        return FXGL.entityBuilder(data).type(TILE)
                .zIndex(-10)
                .build();
    }

    //Aufleuchtende Tiles, die können auch dann für die visualisierung von move und shoot verwendet werden
    @Spawns("hoverTile")
    public Entity spawnHoverFloor(SpawnData data) {
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(
                0.0, 0.0,    // Top
                64.0, 32.0,  // Right
                0.0, 64.0,   // Bottom
                -64.0, 32.0     // Left
        );
        diamond.setOpacity(0.40);
        var cell = FXGL.entityBuilder(data).type(TILE).viewWithBBox(diamond)
                .onClick(tile -> {
                    MovementService.moveTank(tile);
                    //MovementService.rotateAutomatically(tile);
                })
                .build();
        diamond.fillProperty().bind(
                Bindings.when(cell.getViewComponent().getParent().hoverProperty())
                        .then(Color.DARKGREEN)
                        .otherwise(Color.TRANSPARENT)
        );

        return cell;
    }

    @Spawns("moveTiles")
    public Entity spawnMoveTiles(SpawnData data) {

        return FXGL.entityBuilder(data)
                .onClick(MovementService::moveTank).type(ACTIONSELECTION)
                .viewWithBBox("Tile_move_selection.png")
                .build();
    }
    @Spawns("AttackTargetTiles")
    public Entity spawnAttackTargetTiles(SpawnData data) {
        return FXGL.entityBuilder(data)
                .onClick(MovementService::moveTank).type(ACTIONSELECTION)
                .viewWithBBox("Tile_attack_selection.png")
                .build();
    }
}
