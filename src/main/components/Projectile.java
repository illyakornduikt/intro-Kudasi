package main.components;

import com.artemis.Component;

public class Projectile extends Component {

    public boolean isPlayer;
    public float dx, dy;

    public Projectile() {}

    public Projectile(boolean isPlayer, float dx, float dy) {
        this.isPlayer = isPlayer;
        this.dx = dx;
        this.dy = dy;
    }
}
