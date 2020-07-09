package com.starfish.alex.ivan;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Starfish extends BaseActor {
    public Starfish(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("starfish.png");

        Action spin = Actions.rotateBy(30, 1); // вращение вокруг оси на 30 градусов за 1 секунду
        this.addAction(Actions.forever(spin));
    }
}
