import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.util.Random;

public class Main {
    static final int WIDTH = 1024;
    static final int HEIGHT = 576;
    public static void main(String[] args) throws SlickException {
        CanonGame cg = new CanonGame("Cannon Game");
        AppGameContainer ap =  new AppGameContainer(cg, WIDTH, HEIGHT, false);
        ap.setTargetFrameRate(60);
        ap.start();
    }
}

class CanonGame extends BasicGame {
    UnicodeFont font;
    Ball ball;
    Target target;
    Landscape landscape;
    Cannon cannon;

    public CanonGame(String title) {
        super(title);
    }

    // Aquesta funció s'encarrega d'inicialitzar els recursos a la memòria
    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        font = ResourceManager.getFont("WHITRABT.TTF", 30);
        ball = new Ball();
        target = new Target();
        landscape = new Landscape();
        cannon = new Cannon();
    }

    // Aquesta funció s’encarrega de mantenir l’estat del joc.
    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        cannon.update(gameContainer);
        ball.update();
        target.update();
        landscape.update();
    }

    // És el mètode on hi hem de posar el codi que dibuixarà cada frame del joc. El número de vegades
    // que s’invoca aquest mètode per segon determina el FPS.
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.ball.render();
        this.landscape.render();
        this.target.render();
        this.cannon.render();
        this.font.drawString(100, 50, "Strength: " + (int) this.cannon.getStrength());
        this.font.drawString(400, 50, "Angle: " + (int) this.cannon.getRotation());
        this.font.drawString(700, 50, "Score: 00000");
    }
}

class Ball {
    private final Image ballImg = ResourceManager.getImage("ball.png");

    public void update() {

    }

    public void render() {
        this.ballImg.draw(55, 470);
    }


}

class Target {
    private final Image targetImg = ResourceManager.getImage("target.png");
    Random r = new Random();
    private double targetX = r.nextInt(700 - 130);

    public void update() {

    }

    public void render() {
        this.targetImg.draw((float) ((float) 300 + targetX), 470);
    }
}

class Landscape {
    private final Image landscapeImg = ResourceManager.getImage("landscape.jpg");
    private final Image cloudImg = ResourceManager.getImage("cloud.png");
    private boolean endavant = true;
    private int cloudX = 30;

    public void update() {
        if (endavant) {
            cloudX += 1;
        } else {
            cloudX -= 1;
        }

        if (cloudX > 1024-30-200) {
            endavant = false;
        } else if (cloudX < 50) {
            endavant = true;
        }
    }

    public void render() {
        this.landscapeImg.draw(0, 0);
        this.cloudImg.draw(cloudX, 100);
    }
}

class Cannon {
    private final Image cannonImg = ResourceManager.getImage("cannon.png");
    private final Image cannonBaseImg = ResourceManager.getImage("cannon_base.png");
    private double rotation = 0;
    private double strength = 5;
    private double rotationBefore;

    public void update(GameContainer gameContainer) {
        Input input = gameContainer.getInput();
        if(input.isKeyDown(Input.KEY_DOWN)) {
            updateStrength(-1);

            System.out.println(getStrength());
        } else if(input.isKeyDown(Input.KEY_LEFT)) {
            updateRotation(1);

            System.out.println(getRotation());
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            updateRotation(-1);

            System.out.println(getRotation());
        } else if (input.isKeyDown(Input.KEY_UP)) {
            updateStrength(1);

            System.out.println(getStrength());
        } else if (input.isKeyPressed(Input.KEY_SPACE)) {
            fire();
        }
    }

    public void render() {
        this.cannonImg.draw(45, 450);
        this.cannonBaseImg.draw(48, 470);
        this.cannonImg.setCenterOfRotation(37, 37);

        if (this.rotationBefore > this.rotation) {
            this.cannonImg.rotate(+1);
            this.rotationBefore = this.rotation;
        } else if (this.rotationBefore < this.rotation) {
            this.cannonImg.rotate(-1);
            this.rotationBefore = this.rotation;
        }
    }

    public Ball fire(){
        return new Ball();
    }

    public void updateRotation(double deltaRotation) {
        this.rotationBefore = rotation;
        rotation += deltaRotation;
        if (getRotation() > 85) {
            rotation = 85;
        } else if (getRotation() < 0) {
            rotation = 0;
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