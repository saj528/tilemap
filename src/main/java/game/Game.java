package game;

import game.states.GameState;
import game.states.State;
import game.states.StateManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private Display display;
    private String title;
    private int width, height;
    private Thread thread;
    private boolean running = false;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    int x = 0;

    //states
    private State gameState;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    private void init() {
        display = new Display(title, width, height);
        Assets.init();
        gameState = new GameState();
        StateManager.setState(gameState);
    }

    private void tick() {
        if(StateManager.getCurrentState() != null){
            StateManager.getCurrentState().tick();
        }
    }

    private void render() {
        bufferStrategy = display.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();

        //clear screen
        graphics.clearRect(0, 0, width, height);

        //Draw start
        if(StateManager.getCurrentState() != null){
            StateManager.getCurrentState().render(graphics);
        }
        //Draw end

        bufferStrategy.show();
        graphics.dispose();


    }

    public void run() {
        init();

        int fps = 60;
        double timePerTick = (double) 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while (running) {

            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                delta = 0;
            }

        }

        stop();
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) return;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
