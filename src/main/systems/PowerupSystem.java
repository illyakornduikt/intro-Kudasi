package main.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import main.Utils;
import main.components.*;

@All({Powerup.class})
public class PowerupSystem extends IteratingSystem {

    ComponentMapper<Player> playerMapper;
    ComponentMapper<Powerup> powerupMapper;
    ComponentMapper<Hitbox> hitboxMapper;
    ComponentMapper<Transform> transformMapper;

    IntBag players;

    @Override
    protected void initialize() {
        players = world.getAspectSubscriptionManager().get(Aspect.all(Player.class)).getEntities();
    }

    @Override
    protected void process(int entityId) {
        Powerup powerup = powerupMapper.get(entityId);
        Transform transform = transformMapper.get(entityId);

        transform.y += 100 * world.getDelta();

        for(int i = 0; i < players.size(); i++) {
            Player player = playerMapper.get(players.get(i));
            Hitbox playerHitbox = hitboxMapper.get(players.get(i));
            Transform playerTransform = transformMapper.get(players.get(i));
            if(Utils.collideCircles(transform.x, transform.y, 5, playerTransform.x, playerTransform.y, playerHitbox.radius)) {
                switch (powerup.type) {
                    case Powerup.HEALTH -> player.hp = Math.min(player.hp + 3, 5);
                    case Powerup.SHIELD -> world.edit(players.get(i)).add(new Shield());
                    case Powerup.HASTE -> world.edit(players.get(i)).add(new Haste());
                }
                world.delete(entityId);
                break;
            }
        }
    }
}
