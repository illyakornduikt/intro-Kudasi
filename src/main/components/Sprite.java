package main.components;

import com.artemis.Component;
import processing.core.PImage;

public class Sprite extends Component {

    public PImage image;
    public boolean centered = true;

    public Sprite() {}

    public Sprite(PImage image) {
        this.image = image;
    }

    public Sprite(PImage image, boolean centered) {
        this.image = image;
        this.centered = centered;
    }
}
