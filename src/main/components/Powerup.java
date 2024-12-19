package main.components;

import com.artemis.Component;

public class Powerup extends Component {

    public static final int HEALTH = 0;
    public static final int SHIELD = 1;
    public static final int HASTE = 2;

    public int type;

    public Powerup() {}

    public Powerup(int type) {
        this.type = type;
    }
}
