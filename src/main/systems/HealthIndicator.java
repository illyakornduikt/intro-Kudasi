package main.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import main.AssetManager;
import main.Main;
import main.components.Player;

@All({Player.class})
public class HealthIndicator extends IteratingSystem {

    @Wire
    Main applet;

    @Wire(name = "player_count")
    int playerCount;

    ComponentMapper<Player> playerMapper;

    @Override
    protected void process(int entityId) {
        Player player = playerMapper.get(entityId);
        applet.image(AssetManager.images.get("health" + (5 - player.hp)), (float) applet.width / (playerCount + 1) * (player.playerNumber + 1), applet.height - 20);
    }
}
