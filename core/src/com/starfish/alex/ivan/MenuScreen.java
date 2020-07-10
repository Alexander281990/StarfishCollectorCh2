package com.starfish.alex.ivan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;

public class MenuScreen extends BaseScreen {
    public void initialize()
    {
        BaseActor ocean = new BaseActor(0,0, mainStage);
        ocean.loadTexture( "water.jpg" );
        ocean.setSize(800,600);

        BaseActor title = new BaseActor(0,0, mainStage);
        title.loadTexture( "starfish-collector.png" );
        title.centerAtPosition(400,300);
        title.moveBy(0,100);

        TextButton startButton = new TextButton( "Start", BaseGame.textButtonStyle );
        startButton.setPosition(150,150);
        uiStage.addActor(startButton);
        startButton.addListener(
                new EventListener() {
                    @Override
                    public boolean handle(Event e) {
                        if (!(e instanceof InputEvent) ||
                                !((InputEvent) e).getType().equals(Type.touchDown))
                            return false;
                        StarfishGame.setActiveScreen(new LevelScreen());
                        return false;
                    }
                }
        );
        TextButton quitButton = new TextButton( "Quit", BaseGame.textButtonStyle );
        quitButton.setPosition(500,150);
        uiStage.addActor(quitButton);
        quitButton.addListener(
                new EventListener() {
                    @Override
                    public boolean handle(Event e) {
                        if (!(e instanceof InputEvent) ||
                                !((InputEvent) e).getType().equals(Type.touchDown))
                            return false;
                        Gdx.app.exit();
                        return false;
                    }
                }
        );


    }

    public void update(float dt)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            StarfishGame.setActiveScreen( new LevelScreen() );
    }

    // метод, позволяющий загружать и закрывать игру с клавиатуры клавишами ENTER and ESC
    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
            StarfishGame.setActiveScreen( new LevelScreen() );

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();

        return false;

    }

}