package com.starfish.alex.ivan;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Whirlpool extends BaseActor {
    public Whirlpool(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("whirlpool.png", 2, 5, 0.1f, false);
    }

    // метод нужен для того, чтобы удалить обьект со сцены по завершении анимации
    public void act(float dt) {
        super.act(dt);
        if (isAnimationFinished())
            remove();
    }
}
