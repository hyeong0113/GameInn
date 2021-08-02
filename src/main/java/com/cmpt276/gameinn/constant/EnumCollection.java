package com.cmpt276.gameinn.constant;

public class EnumCollection {
    public enum RequiredLevel {
        ANY, BEGINNER, INTERMEDIATE, EXPERT
    }

    public enum GameStyle {
        CASUAL, PROFESSIONAL;
    }

    public enum Platform {
        TWITCH("Twitch"),
        YOUTUBE("Youtube"),
        NINEGAG("9Gag"),
        TWITTER("Twitter");

        public final String platformName;

        Platform(String platformName) {
            this.platformName = platformName;
        }

        public String getPlatformName() {
            return this.platformName;
        }

        public String toString() {
            return this.platformName;
        }
    }
}
