package main.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import main.AssetManager;
import main.Main;
import main.components.*;

@All({Player.class})
public class PlayerManager extends IteratingSystem {

    @Wire
    Main applet;

    ComponentMapper<Player> playerMapper;
    ComponentMapper<Shield> shieldMapper;
    ComponentMapper<Haste> hasteMapper;
    ComponentMapper<Transform> transformMapper;

    public float reload = .0f;

    @Override
    protected void process(int entityId) {
        Player player = playerMapper.get(entityId);
        Transform transform = transformMapper.get(entityId);

        if(applet.keyPressed) {
            if (applet.key == 'a') {
                transform.x -= 100 * world.getDelta();
                if (transform.x < 0) transform.x += applet.width;
            } else if (applet.key == 'd') {
                transform.x += 100 * world.getDelta();
                if (transform.x >= applet.width) transform.x -= applet.width;
            }
        }

        if(reload <= .0f) {
            reload += 0.6f;
            int projectile = world.create();
            world.edit(projectile).
                    add(new Projectile(true, 0, -200)).
                    add(new Transform(transform.x, transform.y - 6, 0)).
                    add(new Sprite(AssetManager.images.get("projectile")));
        }
        reload -= world.getDelta();

        if(hasteMapper.has(entityId)) {
            reload -= world.getDelta();
            Haste haste = hasteMapper.get(entityId);
            haste.duration -= world.getDelta();
            if(haste.duration <= 0) world.edit(entityId).remove(haste);
        }

        if(shieldMapper.has(entityId)) {
            Shield shield = shieldMapper.get(entityId);
            shield.duration -= world.getDelta();
            if(shield.duration <= 0) world.edit(entityId).remove(shield);
        }
    }
}
