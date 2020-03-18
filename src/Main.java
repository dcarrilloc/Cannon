import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

public class Main {
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    public static void main(String[] args) throws SlickException {
        CanonGame cg = new CanonGame("Cannon Game");
        AppGameContainer ap =  new AppGameContainer(cg, WIDTH, HEIGHT, false);
        ap.setTargetFrameRate(60);
        ap.start();
    }
}

class CanonGame extends BasicGame {
    Ball ball = new Ball();
    Target target = new Target();
    Landscape landscape = new Landscape();
    Cannon cannon = new Cannon();

    public CanonGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {


    }

    // Aquesta funció s’encarrega de mantenir
    // l’estat del joc.
    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        cannon.update(gameContainer.getInput());
        ball.update();
        target.update();
        landscape.update();
    }

    // És el mètode on hi hem de posar el codi que dibuixarà cada frame del joc. El número de vegades
    // que s’invoca aquest mètode per segon determina el FPS.
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {




        ball.render();
        target.render();
        landscape.render();
    }
}

class Ball {
    public void update() {

    }

    public void render() {

    }
}

class Target {
    public void update() {

    }

    public void render() {

    }
}

class Landscape {
    public void update() {

    }

    public void render() {

    }
}

class Cannon {
    private double rotation = 45;
    private double strength = 5;

    public void update(Input input) {
        if(input.isKeyDown(Input.KEY_DOWN)) {
            updateStrength(-0.7);

            System.out.println(getStrength());
        } else if(input.isKeyDown(Input.KEY_LEFT)) {
            updateRotation(0.7);

            System.out.println(getRotation());
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            updateRotation(-0.7);

            System.out.println(getRotation());
        } else if (input.isKeyDown(Input.KEY_UP)) {
            updateStrength(0.7);

            System.out.println(getStrength());
        } else if (input.isKeyPressed(Input.KEY_SPACE)) {
            fire();
        }
    }

    public void render() {

    }

    public void fire(){

    }

    public void updateRotation(double deltaRotation) {
        rotation += deltaRotation;
        if (getRotation() > 85) {
            rotation = 85;
        } else if (getRotation() < 5) {
            rotation = 5;
        }
    }

    public void updateStrength(double deltaStrength) {
        strength += deltaStrength;
        if (getStrength() > 100) {
            strength = 100;
        } else if (getStrength() < 5) {
            strength = 5;
        }
    }

    public double getRotation() {
        return rotation;
    }

    public double getStrength() {
        return strength;
    }
}