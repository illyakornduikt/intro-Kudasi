package main.components;

import com.artemis.Component;

public class Invader extends Component {

    public float descend = 0;
    public int direction;

    public Invader() {}

    public Invader(int direction) {
        this.direction = direction;
    }
}
