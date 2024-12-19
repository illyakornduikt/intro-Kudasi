package main;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import main.components.Hitbox;
import main.components.Player;
import main.components.Sprite;
import main.components.Transform;
import main.systems.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {

    public static PApplet applet;

    public static void main(String[] args) {
        PApplet.main(Main.class, args);
    }

    public Main() {
        super();
        applet = this;
    }

    @Override
    public void settings() {
        size(480, 720, P2D);
    }

    /////////////////////////////////////////////

    private long lastFrameTime;

    private World world;

    @Override
    public void setup() {
        AssetManager.load();
        world = new World(new WorldConfiguration().
                register( this).
                register("player_count", 1).
                setSystem(InvaderController.class).
                setSystem(PowerupSystem.class).
                setSystem(PlayerManager.class).
                setSystem(ProjectileManager.class).
                setSystem(SpriteRenderer.class).
                setSystem(HealthIndicator.class)
        );
        lastFrameTime = millis();

        int player = world.create();
        world.edit(player).
                add(new Player(0)).
                add(new Hitbox(5)).
                add(new Sprite(AssetManager.images.get("player"))).
                add(new Transform(width >> 1, 680, 0));
    }

    @Override
    public void draw() {
        float delta = (millis() - lastFrameTime) / 1000f;
        lastFrameTime = millis();

        PImage bg = AssetManager.images.get("background");
        for(int x = 0; x < width / bg.width + 1; x++) {
            for(int y = 0; y < height / bg.height + 1; y++) {
                image(bg, x * bg.width, y * bg.height);
            }
        }

        world.setDelta(delta);
        world.process();
    }
}