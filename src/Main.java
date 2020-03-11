import org.newdawn.slick.*;

public class Main {
    public static void main(String[] args) throws SlickException {
        CanonGame game = new CanonGame("Game");
        AppGameContainer ap =  new AppGameContainer(game, 800, 600, false);
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