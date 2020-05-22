package com.knightwars.game.environment.buildings;

import com.badlogic.gdx.math.Vector2;
import com.knightwars.game.environment.Building;
import com.knightwars.game.players.Player;

/**
 * This is a forge : increase Knights defense
 */
public class ForgeCastle1 extends FortifiedCastle {
    public ForgeCastle1(Player owner, Vector2 coordinates, int knights, boolean knightGrowth) {
        super(owner, coordinates, knights, knightGrowth);
    }

    public ForgeCastle1(Building building) {
        super(building);
    }

    public ForgeCastle1 Copy() {
        return new ForgeCastle1(this);
    }
}
