package com.starfish.alex.ivan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turtle extends BaseActor {

    public Turtle(float x, float y, Stage s) {
        super(x, y, s);

        String[] filenames = {"turtle-1.png","turtle-2.png","turtle-3.png","turtle-4.png","turtle-5.png","turtle-6.png"};
        loadAnimationFromFiles(filenames, 0.1f, true);

        setBoundaryPolygon(8); // создание восьмиугольника вокруг обьекта(Для обнаружения столкновения)

        // инициализация физичечких параметров черепахи
        setAcceleration(400); // скорость будет увеличиваться на этот параметр в секунду
        setMaxSpeed(100); // максимальная скорость черепахи
        setDeceleration(400);
    }

    // метод, который проверяет, нажаты ли клавиши
    public void act(float dt) {
        super.act(dt);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            accelerateAtAngle(180);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            accelerateAtAngle(0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            accelerateAtAngle(90);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            accelerateAtAngle(270);

        applyPhysics(dt);
        setAnimationPaused(!isMoving());
        if (getSpeed() > 0)
            setRotation(getMotionAngle());

        boundToWorld(); // держит черепаху в пределах мировых границ
    }


}
