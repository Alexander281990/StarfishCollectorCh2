package com.starfish.alex.ivan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class StoryScreen extends BaseScreen {

    Scene scene;
    BaseActor continueKey;

    public void initialize() {

        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture( "oceanside.png" );
        background.setSize(800,600);
        background.setOpacity(0);
        BaseActor.setWorldBounds(background);
        BaseActor turtle = new BaseActor(0,0, mainStage); turtle.loadTexture( "turtle-big.png" );
        turtle.setPosition( -turtle.getWidth(), 0 );
        DialogBox dialogBox = new DialogBox(0,0, uiStage);
        dialogBox.setDialogSize(600, 200);
        dialogBox.setBackgroundColor( new Color(0.6f, 0.6f, 0.8f, 1) );
        dialogBox.setFontScale(0.75f);
        dialogBox.setVisible(false);
        uiTable.add(dialogBox).expandX().expandY().bottom();
        continueKey = new BaseActor(0,0,uiStage);
        continueKey.loadTexture("key-C.png");
        continueKey.setSize(32,32);
        continueKey.setVisible(false);
        dialogBox.addActor(continueKey);
        continueKey.setPosition( dialogBox.getWidth() - continueKey.getWidth(), 0 );

        scene = new Scene();
        mainStage.addActor(scene);
        scene.addSegment( new SceneSegment( background, Actions.fadeIn(1) ));
        scene.addSegment( new SceneSegment( turtle, SceneActions.moveToScreenCenter(2) ));
        scene.addSegment( new SceneSegment( dialogBox, Actions.show() ));
        scene.addSegment( new SceneSegment( dialogBox,

                SceneActions.setText("I want to be the very best . . . Starfish Collector!" ) ));

        scene.addSegment( new SceneSegment( continueKey, Actions.show() ));
        scene.addSegment( new SceneSegment( background, SceneActions.pause() ));
        scene.addSegment( new SceneSegment( continueKey, Actions.hide() ));

        scene.addSegment( new SceneSegment( dialogBox, SceneActions.setText("I've got to collect them all!" ) ));

        scene.addSegment( new SceneSegment( continueKey, Actions.show() ));
        scene.addSegment( new SceneSegment( background, SceneActions.pause() ));
        scene.addSegment( new SceneSegment( continueKey, Actions.hide() ));

        scene.addSegment( new SceneSegment( dialogBox, Actions.hide() ) );
        scene.addSegment( new SceneSegment( turtle, SceneActions.moveToOutsideRight(1) ));
        scene.addSegment( new SceneSegment( background, Actions.fadeOut(1) ));

        // Этот код для тестировки на андроид
//        TextButton startButton = new TextButton( "Start", BaseGame.textButtonStyle );
////        startButton.setPosition(150,150);
////        uiStage.addActor(startButton);
//        startButton.addListener(
//                new EventListener() {
//                    @Override
//                    public boolean handle(Event e) {
//                        if (!(e instanceof InputEvent) ||
//                                !((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
//                            return false;
//                        scene.loadNextSegment();
//                        return false;
//                    }
//                }
//        );
//
//        uiTable.add(startButton);


        scene.start();


    }

    public void update(float dt) {
        if ( scene.isSceneFinished() ) BaseGame.setActiveScreen( new LevelScreen() );
    }

    public boolean keyDown(int keyCode) {
        if ( keyCode == Input.Keys.C && continueKey.isVisible() )
            scene.loadNextSegment();
        return false;
    }

}
