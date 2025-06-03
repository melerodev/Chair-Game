package io.github.melerodev.chairgame.api;

public class LocationFormatOptions {
    public static final LocationFormatOptions WORLD_X_Y_Z = new LocationFormatOptions().withWorld(true).withYawPitch(false);
    public static final LocationFormatOptions X_Y_Z = new LocationFormatOptions().withWorld(false).withYawPitch(false);
    public static final LocationFormatOptions X_Y_Z_YAW_PITCH = new LocationFormatOptions().withWorld(false).withYawPitch(true);
    public static final LocationFormatOptions FULL = new LocationFormatOptions().withWorld(true).withYawPitch(true);

    public boolean includeWorld = true;
    public boolean includeYawPitch = false;

    public LocationFormatOptions withWorld(boolean value) {
        this.includeWorld = value;
        return this;
    }

    public LocationFormatOptions withYawPitch(boolean value) {
        this.includeYawPitch = value;
        return this;
    }
}
