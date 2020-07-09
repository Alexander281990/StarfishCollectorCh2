package com.starfish.alex.ivan;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class StarfishCollector extends GameBeta {
    private Turtle turtle;
    private Starfish starfish;
    private BaseActor ocean;
    private Rock rock;

    @Override
    public void initialize() {
        ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);

        starfish = new Starfish(380, 380, mainStage);

        turtle = new Turtle(20, 20, mainStage);

        rock = new Rock(200, 200, mainStage);
    }

    @Override
    public void update(float dt) {

        turtle.preventOverlap(rock); // если черепаха косается камня, то камень стоит на месте, а черепаха не может проплыть через него (препятствие)
                                     // усли turtle and rock поменять местами, то черепаха будет двигать камень


        if (turtle.overlaps(starfish) && !starfish.isCollected()){
            starfish.collect();

            Whirlpool whirl = new Whirlpool(0, 0, mainStage);
            whirl.centerAtActor(starfish);
            whirl.setOpacity(0.25f);

            BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

    }

}
