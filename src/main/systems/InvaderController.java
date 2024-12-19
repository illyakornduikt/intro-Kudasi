package main.systems;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import main.AssetManager;
import main.Main;
import main.components.*;

@All({Invader.class})
public class InvaderController extends IteratingSystem {

    @Wire
    Main applet;

    ComponentMapper<Invader> invaderMapper;
    ComponentMapper<Player> playerMapper;
    ComponentMapper<Transform> transformMapper;

    private final float INVADER_SPEED_MIN = 30;
    private final float INVADER_SPEED_MAX = 120;
    private final float DESCEND_BORDER = 20;
    private final float DESCEND = 40;
    private final float COLUMN_GAP = 40;

    private int invader_count = 0;
    private int invader_count_max = 0;
    private float invader_speed = INVADER_SPEED_MIN;
    private float shoot_timer = 0f;

    @Override
    protected void initialize() {
        world.getAspectSubscriptionManager().get(Aspect.all(Invader.class)).addSubscriptionListener(
            new EntitySubscription.SubscriptionListener() {
                @Override
                public void inserted(IntBag entities) {
                    invader_count += entities.size();
                }

                @Override
                public void removed(IntBag entities) {
                    invader_count -= entities.size();
                }
            }
        );
        for(int row = 0; row < 5; row++) {
            for(int i = 0; i < 12; i++) {
                int invader = world.create();
                world.edit(invader).
                        add(new Invader(row % 2 == 0 ? 1 : -1)).
                        add(new Hitbox(7)).
                        add(new Transform(DESCEND_BORDER + i * COLUMN_GAP, 40 + row * DESCEND, 0)).
                        add(new Sprite(AssetManager.images.get("invader_" + row + "_0")));
                invader_count_max++;
            }
        }
    }

    @Override
    protected void begin() {
        invader_speed = INVADER_SPEED_MIN + (INVADER_SPEED_MAX - INVADER_SPEED_MIN) * (1f - invader_count / (float) invader_count_max);

        if(shoot_timer <= 0) {
            Transform targetTransform = transformMapper.get(getEntityIds().get((int) (Math.random() * getEntityIds().size())));
            Transform playerTransform = transformMapper.get(world.getAspectSubscriptionManager().get(Aspect.all(Player.class)).getEntities().get(0));
            float dst = (float) Math.hypot(playerTransform.x - targetTransform.x, playerTransform.y - targetTransform.y);

            int projectile = world.create();
            world.edit(projectile).
                    add(new Projectile(false, (playerTransform.x - targetTransform.x) / dst * 120, (playerTransform.y - targetTransform.y) / dst * 120)).
                    add(new Transform(targetTransform.x, targetTransform.y, 0)).
                    add(new Sprite(AssetManager.images.get("projectile")));
            shoot_timer += 0.3f + 0.01f * invader_count;
        }
        shoot_timer -= world.getDelta();
    }

    @Override
    protected void process(int entityId) {
        Invader invader = invaderMapper.get(entityId);
        Transform transform = transformMapper.get(entityId);

        if(invader.descend > 0) {
            float delta_movement = invader_speed * world.getDelta();
            if(delta_movement > invader.descend) {
                transform.y += invader.descend;
                invader.direction = -invader.direction;
                transform.x += (delta_movement - invader.descend) * invader.direction;
                invader.descend = 0;
            }
            else {
                transform.y += delta_movement;
                invader.descend -= delta_movement;
            }
        }
        else {
            transform.x += invader.direction * invader_speed * world.getDelta();

            if(invader.direction > 0 && transform.x >= applet.width - DESCEND_BORDER) {
                invader.descend = DESCEND;
                invader.descend -= transform.x - (applet.width - DESCEND_BORDER);
                transform.y += transform.x - (applet.width - DESCEND_BORDER);
                transform.x = applet.width - DESCEND_BORDER;
            }
            else if(invader.direction < 0 && transform.x <= DESCEND_BORDER) {
                invader.descend = DESCEND;
                invader.descend += transform.x - DESCEND_BORDER;
                transform.y -= transform.x - DESCEND_BORDER;
                transform.x = DESCEND_BORDER;
            }
        }
    }
}
