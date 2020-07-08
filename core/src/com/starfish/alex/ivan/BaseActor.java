package com.starfish.alex.ivan;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BaseActor extends Actor {

    public BaseActor(float x, float y, Stage s) {
        super();
        setPosition(x, y);
        s.addActor(this);
    }

}
