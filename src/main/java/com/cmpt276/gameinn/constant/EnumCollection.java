package com.cmpt276.gameinn.constant;

public class EnumCollection {
	public enum RequiredLevel {
		ANY, BEGINNER, INTERMEDIATE, EXPERT;
	} public enum GameStyle {
		CASUAL, PROFESSIONAL;
	} public enum Platform {
		YOUTUBE("YouTube"), TWITTER("Twitter"), REDDIT("Reddit"), TWITCH(
			"Twitch");

		private final String platformName;

		Platform(String platformName) {
			this.platformName = platformName;
		}

		public String getPlatformName() {
			return this.platformName;
		}
	} }
