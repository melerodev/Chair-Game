package io.github.melerodev.chairgame.permission;

public enum Permissions {
    BASE_PERMISSION("chairgame.command"),
    ADMIN_PERMISSION(BASE_PERMISSION + ".admin"),
    RELOAD_PERMISSION(ADMIN_PERMISSION + ".reload"),
    SET_PERMISSION(ADMIN_PERMISSION + ".set"),
    SET_LOBBY_PERMISSION(SET_PERMISSION + ".lobby"),
    SET_SPAWN_PERMISSION(SET_PERMISSION + ".spawn"),
    SET_SPAWN_LOBBY_PERMISSION(SET_SPAWN_PERMISSION + ".lobby"),
    SET_SPAWN_PLAYER_PERMISSION(SET_SPAWN_PERMISSION + ".player"),

    // User permissions
    JOIN_PERMISSION(BASE_PERMISSION + ".join"),
    LEAVE_PERMISSION(BASE_PERMISSION + ".leave");

    private final String node;

    Permissions(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }
}