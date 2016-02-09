package cz.m0bY_czE.dragons;

public enum GameState {
    IN_LOBBY(true),
    PRE_GAME(false),
    IN_GAME(false),
    RESETTING(false);

    private boolean canJoin;
    private static GameState currentState;

    private GameState(boolean canJoin) {
        this.canJoin = canJoin;
    }

    public boolean canJoin() {
        return this.canJoin;
    }

    public static void setState(GameState state) {
        currentState = state;
    }

    public static boolean isState(GameState state) {
        return currentState == state;
    }

    public static GameState getState() {
        return currentState;
    }
}

