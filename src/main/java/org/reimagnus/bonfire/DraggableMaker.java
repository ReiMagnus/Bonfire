package org.reimagnus.bonfire;

import javafx.scene.Node;

public class DraggableMaker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(Node node) {

        node.setOnMousePressed(mouseEvent -> {
            //mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {

            //node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);

            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);

            System.out.println("--------------------");
            //System.out.println(mouseEvent.getSceneX()-mouseAnchorX);
            System.out.println(mouseEvent.getSceneY()-mouseAnchorY);
        });

    }

}
