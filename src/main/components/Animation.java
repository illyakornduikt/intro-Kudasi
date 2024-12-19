package main.components;

import com.artemis.Component;
import processing.core.PImage;

public class Animation extends Component {

    public float frame_time;
    public boolean looping;
    public PImage[] frames;

    public Animation() {}

    public Animation(float frame_time, boolean looping, PImage...frames) {
        this.frame_time = frame_time;
        this.looping = looping;
        this.frames = frames;
    }
}
