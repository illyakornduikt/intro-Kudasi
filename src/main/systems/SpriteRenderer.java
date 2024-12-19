package main.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import main.Main;
import main.components.Sprite;
import main.components.Transform;
import processing.core.PApplet;

@All({Sprite.class, Transform.class})
public class SpriteRenderer extends IteratingSystem {

    @Wire Main applet;

    ComponentMapper<Sprite> spriteMapper;
    ComponentMapper<Transform> transformMapper;

    @Override
    protected void process(int entityId) {
        Sprite sprite = spriteMapper.get(entityId);
        Transform transform = transformMapper.get(entityId);

        applet.imageMode(sprite.centered ? PApplet.CENTER : PApplet.CORNER);
        applet.translate(transform.x, transform.y);
        applet.rotate(transform.rotation);
        applet.image(sprite.image, 0, 0);
        applet.resetMatrix();
    }
}
