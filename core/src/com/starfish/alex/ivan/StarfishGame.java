package com.starfish.alex.ivan;

public class StarfishGame extends BaseGame {
    @Override
    public void create() {
        super.create();
        setActiveScreen(new MenuScreen());
    }
}
