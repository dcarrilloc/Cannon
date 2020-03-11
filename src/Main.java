import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

public class Main {
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    public static void main(String[] args) throws SlickException {
        CanonGame cg = new CanonGame("New Game");
        AppGameContainer ap =  new AppGameContainer(cg, WIDTH, HEIGHT, false);
        ap.setTargetFrameRate(60);
        ap.start();
    }
}

class CanonGame extends BasicGame {

    public CanonGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

    }
}

class Ball {

}

class Target {

}

class Landscape {

}

class Cannon {

}