package com.cmpt276.gameinn.testcases.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.Test;

public class AuthenticationTest {
    // Header
    private final String CONTENT_TYPE = "content-type";
    private final String CONTENT_TYPE_CONTENT = "application/json";

    // Body content
    private final String TOKEN_URL = "https://gameinn.us.auth0.com/oauth/token";
    private final String userEmail = "admin@admin.com";
    private final String CONNECTION = "Username-Password-Authentication";
    private final String EMAIL_LOGIN = "admin%40admin.com";
    private final String PASSWORD_LOGIN = "qwe123QWE%40";
    private final String CLIENT_ID = "3T69QL1FF2h55f02ujE1ImtMuJLP8Awj";
    private final String CLIENT_SECRET = "B0lOI_6lk2_tpsAgCVCLj4r_YoKDRS6QK4fcGG8KmalaImYkIJKMOCiq_N5KNgqB";
    private final String AUDIENCE = "https://gameinn.us.auth0.com/api/v2/";
    private final String GRAND_TYPE = "client_credentials";

    @Test
    public void givenUserNameAndPasswordThenReturnTokenSuccessfully() throws UnirestException {
        HttpResponse<String> response = getToken();
        
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatus());
    }

    private final String USER_URL = "https://gameinn.us.auth0.com/api/v2/users/auth0%7C60e56d0a68dabc00690a5eab?include_fields=true";

    @Test
    public void givenUserNameAndPasswordThenReturnUserInfoSuccessfully() throws UnirestException {
        HttpResponse<String> token = getToken();

        String[] couple = token.getBody().split(",");

        String accessToken = "";
        for(int i = 0; i < couple.length ; i++) {
            String[] items =couple[i].split(":");
            if (items[0].contains("access_token")) {
                StringBuffer sb = new StringBuffer(items[1]);
                sb.delete(items[1].length() - 1, items[1].length());

                sb.delete(0, 1);
                accessToken = sb.toString();
                break;
            }
        }

        String bearerToken = "Bearer " + accessToken;
        HttpResponse<String> response = Unirest.get(USER_URL)
            .header("authorization", bearerToken)
            .asString();

        String[] responseMap = response.getBody().split(",");
        String responseEmail = "";

        for(int i = 0; i < responseMap.length ; i++) {
            String[] items =responseMap[i].split(":");
            if (items[0].contains("email")) {
                StringBuffer sb = new StringBuffer(items[1]);
                sb.delete(items[1].length() - 1, items[1].length());

                sb.delete(0, 1);
                responseEmail = sb.toString();
                break;
            }
        }

        assertNotNull(response.getBody());
        assertEquals(userEmail, responseEmail);
    }

    private HttpResponse<String> getToken() throws UnirestException {
        String bodyContent = String.format("{\"connection\":\"%s\",\"username\":\"%s\",\"password\":\"%s\",\"client_id\":\"%s\",\"client_secret\":\"%s\",\"audience\":\"%s\",\"grant_type\":\"%s\"}", CONNECTION, EMAIL_LOGIN, PASSWORD_LOGIN, CLIENT_ID, CLIENT_SECRET, AUDIENCE, GRAND_TYPE);

        HttpResponse<String> response = Unirest.post(TOKEN_URL)
            .header(CONTENT_TYPE, CONTENT_TYPE_CONTENT)
            .body(bodyContent)
            .asString();

        return response;
    }
}