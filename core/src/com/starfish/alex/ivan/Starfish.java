package com.starfish.alex.ivan;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Starfish extends BaseActor {

    public boolean collected; // переменная, котороя будет отслеживать

    public Starfish(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("starfish.png");

        setBoundaryPolygon(8); // создание восьмиугольника вокруг обьекта(Для обнаружения столкновения)

        Action spin = Actions.rotateBy(30, 1); // вращение вокруг оси на 30 градусов за 1 секунду
        this.addAction(Actions.forever(spin));

        collected = false;
    }

    // метод, который возвращает значение собранной переменной
    public boolean isCollected() {
        return collected;
    }

    // метод, который устанавливает isCollected в true и применяет анимированный эффект затухания, после чего он удаляется со сцены
    public void collect() {
        collected = true;
        clearActions();
        addAction(Actions.fadeOut(1));
        addAction(Actions.after(Actions.removeActor()));
    }
}
