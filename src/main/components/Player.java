package main.components;

import com.artemis.Component;

public class Player extends Component {

    public int hp = 5;
    public int playerNumber;

    public Player() {}

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
