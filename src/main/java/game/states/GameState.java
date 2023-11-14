package game.states;

import game.Assets;

import java.awt.*;

public class GameState extends State{

    public GameState() {
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(Assets.dirt,10,10,null);
    }
}
