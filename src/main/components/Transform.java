package main.components;

import com.artemis.Component;

public class Transform extends Component {

    public float x, y, rotation;

    public Transform() {}

    public Transform(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
}
