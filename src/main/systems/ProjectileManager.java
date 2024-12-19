package main.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import main.AssetManager;
import main.Main;
import main.Utils;
import main.components.*;

@All({Projectile.class})
public class ProjectileManager extends IteratingSystem {

    @Wire
    Main applet;

    ComponentMapper<Invader> invaderMapper;
    ComponentMapper<Player> playerMapper;
    ComponentMapper<Projectile> projectileMapper;
    ComponentMapper<Hitbox> hitboxMapper;
    ComponentMapper<Transform> transformMapper;
    ComponentMapper<Shield> shieldMapper;

    @Override
    protected void process(int entityId) {
        Projectile projectile = projectileMapper.get(entityId);
        Transform transform = transformMapper.get(entityId);

        transform.x += projectile.dx * world.getDelta();
        transform.y += projectile.dy * world.getDelta();
        transform.rotation = (float) (Math.atan2(projectile.dy, projectile.dx) + Math.PI * 0.5);

        if(transform.y > applet.height || transform.y < 0) {
            world.delete(entityId);
            return;
        }

        IntBag entities = world.getAspectSubscriptionManager().get(Aspect.all(Hitbox.class)).getEntities();
        System.out.println(entities.size());
        for(int i = 0; i < entities.size(); i++) {
            int otherEntityID = entities.get(i);

            if(projectile.isPlayer && playerMapper.has(otherEntityID)) continue;
            if(!projectile.isPlayer && invaderMapper.has(otherEntityID)) continue;

            float radius = hitboxMapper.get(otherEntityID).radius;
            Transform otherTransform = transformMapper.get(otherEntityID);
            if(Utils.pointInCircle(otherTransform.x, otherTransform.y, radius, transform.x, transform.y)) {
                if(invaderMapper.has(otherEntityID)) {
                    if(Math.random() < 0.15) {
                        int type = (int) (Math.random() * 3);
                        String[] types = {"powerup_health", "powerup_shield", "powerup_haste"};
                        int powerup = world.create();
                        world.edit(powerup).
                                add(new Powerup(type)).
                                add(new Transform(otherTransform.x, otherTransform.y, 0)).
                                add(new Sprite(AssetManager.images.get(types[type])));
                    }
                    world.delete(otherEntityID);
                }
                else {
                    if(!shieldMapper.has(otherEntityID)) playerMapper.get(otherEntityID).hp--;
                }
                world.delete(entityId);
            }
        }
    }
}
