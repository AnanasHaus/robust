package com.robustgames.robustclient.business.entitiy.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.robustgames.robustclient.business.logic.MapService;
import com.robustgames.robustclient.business.logic.Direction;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.robustgames.robustclient.business.entitiy.EntityType.*;

public class ShootComponent extends Component {

    @Override
    public void onAdded() {
        if (entity.getComponent(APComponent.class).canUse(3)) {
            Point2D tankPos = MapService.isoScreenToGrid(entity.getCenter());
            // Für jede der 4 Hauptachsen schießen
            for (Direction dir : Direction.values()) {
                Point2D current = tankPos;
                while (true) {
                    current = step(current, dir); // Einen Schritt in die Richtung gehen

                    // Prüfe Map-Grenzen
                    if (!MapService.isOverTheEdge(current))
                        break;

                    Point2D posTile = MapService.isoGridToScreen(current);
                    List<Entity> tileList = getGameWorld().getEntitiesAt(posTile);

                    Point2D posEntity = posTile.subtract(64, 64);
                    List<Entity> entityList = getGameWorld().getEntitiesAt(posEntity);

                    if (!entityList.isEmpty()) {
                        if (entityList.size() > 1) {
                            System.err.println("ALERT! TWO ENTITIES AT THE SAME POSITION");
                        }
                        Entity target = entityList.getFirst();
                        MapService.spawnAttackTarget(target);
                        break;
                    } else if (!tileList.isEmpty()) {
                        MapService.spawnAttackTarget(tileList.getFirst());
                    }
                }
            }
        }
        else getNotificationService().pushNotification("Not enough Action Points to shoot!");
    }

    // Hilfsmethode: Einen Schritt in die Richtung gehen
    private Point2D step(Point2D pos, Direction dir) {
        switch (dir) {
            case UP:
                return new Point2D(pos.getX(), pos.getY() - 1);
            case DOWN:
                return new Point2D(pos.getX(), pos.getY() + 1);
            case LEFT:
                return new Point2D(pos.getX() - 1, pos.getY());
            case RIGHT:
                return new Point2D(pos.getX() + 1, pos.getY());
        }
        throw new IllegalArgumentException();
    }

    public void onHoverTargetTile(Point2D tileGridPos, Entity selectedTank) {

        Point2D tankGridPos = MapService.isoScreenToGrid(selectedTank.getCenter());
        Point2D diff = tileGridPos.subtract(tankGridPos);

        String currentImage = getTankImageFilename(selectedTank);

        String richtung = "";
        if (diff.getX() == 0 && diff.getY() < 0) {
            richtung = "UP";
        } else if (diff.getX() == 0 && diff.getY() > 0) {
            richtung = "DOWN";
        } else if (diff.getY() == 0 && diff.getX() > 0) {
            richtung = "RIGHT";
        } else if (diff.getY() == 0 && diff.getX() < 0) {
            richtung = "LEFT";
        }

        switch (currentImage){
            case "tank_top_left.png" -> System.out.println("Tank: " + currentImage + " Hover: " + richtung);

            case "tank_top_right.png" -> System.out.println("Tank: " + currentImage + " Hover: " + richtung);

            case "tank_bottom_left.png" -> System.out.println("Tank: " + currentImage + " Hover: " + richtung);

            case "tank_bottom_right.png" -> System.out.println("Tank: " + currentImage + " Hover: " + richtung);

        }
    }

    private String getTankImageFilename(Entity tank) {
        for (Node e : tank.getViewComponent().getChildren()) {
            if (e instanceof ImageView iv) {
                String url = iv.getImage().getUrl();
                if (url.contains("tank")) {
                    return url.substring(url.lastIndexOf("/") + 1);
                }
            }
        }
        return "";
    }

    @Override
    public void onRemoved() {
        getGameWorld().removeEntities(byType(ACTIONSELECTION));
    }
}
//ROBUST_DEBUG for tests maybe
/*                for (int i = 0; i < getGameScene().getViewport().getX(); i++) {
                    for (int j = 0; j < getGameScene().getViewport().getY(); j++) {
                        Point2D posEntity2 = new Point2D(i - 64, j - 64);
                        List<Entity> targetList2 = getGameWorld().getEntitiesAt(posEntity2);
                        if (targetList2.size() > 1) {
                            System.err.println("ALERT! TWO ENTITIES AT THE SAME POSITION");
                        }
                    }
                }*/