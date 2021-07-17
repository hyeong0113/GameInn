package com.cmpt276.gameinn.services;

import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service public class IGDBService {
	@Value("${TWITCH_CLIENT_ID}") private String CLIENT_ID;
	@Value("${TWITCH_CLIENT_SECRET}") private String CLIENT_SECRET;

	private TwitchAuthenticator tAuth;
	private TwitchToken requestToken;
	private TwitchToken twitchToken;
	private String accessToken;

	@PostConstruct public void postConstruct() {
		tAuth = TwitchAuthenticator.INSTANCE;
		requestToken = tAuth.requestTwitchToken(CLIENT_ID, CLIENT_SECRET);
		twitchToken = tAuth.getTwitchToken();
		accessToken = twitchToken.getAccess_token();
	}

	public String getClientId() {
		return CLIENT_ID;
	}

	public String getClientSecret() {
		return CLIENT_SECRET;
	}

	public TwitchToken getRequestToken() {
		return requestToken;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
