package game.states;

import java.awt.*;

public abstract class State {



    public abstract void tick();
    public abstract void render(Graphics graphics);
}
