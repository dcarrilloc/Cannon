import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

public class Main {
    static final int WIDTH = 1024;
    static final int HEIGHT = 576;
    public static void main(String[] args) throws SlickException {
        CanonGame cg = new CanonGame("Cannon Game de Daniel Carrillo");
        AppGameContainer ap =  new AppGameContainer(cg, WIDTH, HEIGHT, false);
        ap.setTargetFrameRate(60);
        ap.start();
    }
}

class CanonGame extends BasicGame {
    private byte gameState = 0;

    Ball ball;
    UnicodeFont font;
    Target target;
    Landscape landscape;
    Cannon cannon;

    private Rectangle ballRect, targetRect;
    private int score = 0;
    private int dispars = 5;
    private int disparsConsecutius = 0;

    public CanonGame(String title) {
        super(title);
    }

    // Aquesta funció s'encarrega d'inicialitzar els recursos a la memòria
    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        font = ResourceManager.getFont("WHITRABT.TTF", 30);
        target = new Target();
        landscape = new Landscape();
        cannon = new Cannon();
    }

    // Aquesta funció s’encarrega de mantenir l’estat del joc.
    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        Input input = gameContainer.getInput();

        if (gameState == 0) {
            System.out.println("Pantalla de càrrega");
            if(input.isKeyDown(Input.KEY_ENTER)) {
                gameState = 1;
            }
        } else if (gameState == 1) {
            System.out.println("Pantalla del joc");
            cannon.update(gameContainer);
            target.update();
            targetRect = new Rectangle((float) target.targetX + 300, 470, 130, 59);
            landscape.update();
            if(ball == null && input.isKeyDown(Input.KEY_SPACE)) {
                ball = cannon.fire();
                ballRect = new Rectangle((float) ball.posicioInicial[0], (float) ball.posicioInicial[1], 40, 40);
            } else if(ball != null) {
                ball.update();
                ballRect.setX((float) ball.posicioActual[0]);
                ballRect.setY((float) ball.posicioActual[1]);
                if(ball.hasFallen()) {
                    if(score > 0 && score <= 30) {
                        score = 0;
                    } else if (score > 30) {
                        score -= 30;
                    }
                    disparsConsecutius--;
                    dispars--;
                    ball = null;
                    ballRect = null;
                    target = null;
                    target = new Target();
                } else if (ballRect.intersects(targetRect)) {
                    score += 100;
                    disparsConsecutius ++;
                    target = null;
                    ball = null;
                    target = new Target();
                }
            }
            if (disparsConsecutius == 3) {
                dispars++;
                disparsConsecutius = 0;
            }
            if (dispars == 0) {
                gameState = 2;
            }
        }
    }

    // És el mètode on hi hem de posar el codi que dibuixarà cada frame del joc. El número de vegades
    // que s’invoca aquest mètode per segon determina el FPS.
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.landscape.render();
        if (gameState == 0) {
            this.font.drawString(300, 288, "Presioni la tecla ENTER!");
        } else if (gameState == 1) {
            this.target.render();
            this.cannon.render();
            this.font.drawString(100, 50, "Strength: " + (int) this.cannon.getStrength());
            this.font.drawString(400, 50, "Angle: " + (int) this.cannon.getRotation());
            this.font.drawString(700, 50, "Score: " + score);
            this.font.drawString(700, 100, "Vides: " + dispars);
            if(ball != null) {
                ball.render();
            }
        } else {
            this.font.drawString(330, 288, "Ha finalitzat el joc!");
            this.font.drawString(220, 320, "La teva puntuacio ha estat de: " + score);
        }
    }
}

class Ball {
    private final Image ballImg = ResourceManager.getImage("ball.png");
    private Target target;
    public double[] posicioInicial = {55, 470};
    public double[] posicioActual = {0, 0};
    private double angle;
    private double velocitatInicial;
    private double gravity = 4;
    private double time = 0;

    public Ball(double[] posicioInicial, double angle, double velocitatInicial) {
        this.angle = angle * Math.PI / 180f;
        this.velocitatInicial = velocitatInicial;
    }

    public void update() {
        // Calcularem les velocitats horitzontal i vertical
        double vx = velocitatInicial * Math.cos(angle);
        double vy = (-1) * velocitatInicial * Math.sin(angle);

        // Simularem el tir parabòlic amb els paràmetres anteriors
        posicioActual[0] = posicioInicial[0] + vx * time;
        posicioActual[1] = posicioInicial[1] + vy * time + gravity * time * time/2f;
        time += 0.3;
    }

    public void render() {
        if(posicioActual[0] != 0 && posicioActual[1] != 0) {
            this.ballImg.draw((float) posicioActual[0], (float) posicioActual[1]);
        }
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public boolean hasFallen() {
        if (posicioActual[1] > 576) {
            return true;
        } else {
            return false;
        }
    }
}

class Target {
    private final Image targetImg = ResourceManager.getImage("target.png");
    Random r = new Random();
    public double targetX = r.nextInt(700 - 130);

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
        } else if(input.isKeyDown(Input.KEY_LEFT)) {
            updateRotation(1);
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            updateRotation(-1);
        } else if (input.isKeyDown(Input.KEY_UP)) {
            updateStrength(1);
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
        return new Ball(new double[]{55, 470}, this.rotation, this.strength );
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