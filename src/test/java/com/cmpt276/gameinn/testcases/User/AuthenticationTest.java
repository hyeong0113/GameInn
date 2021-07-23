package com.cmpt276.gameinn.testcases.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.Test;

public class AuthenticationTest {

    private final String userEmail = "admin@admin.com";

    @Test
    public void givenUserNameAndPasswordThenReturnTokenSuccessfully() throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://gameinn.us.auth0.com/oauth/token")
            .header("content-type", "application/json")
            .body("{\"connection\":\"Username-Password-Authentication\",\"username\":\"admin%40admin.com\",\"password\":\"qwe123QWE%40\",\"client_id\":\"3T69QL1FF2h55f02ujE1ImtMuJLP8Awj\",\"client_secret\":\"B0lOI_6lk2_tpsAgCVCLj4r_YoKDRS6QK4fcGG8KmalaImYkIJKMOCiq_N5KNgqB\",\"audience\":\"https://gameinn.us.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
            .asString();

            assertNotNull(response.getBody());
            assertEquals(200, response.getStatus());
    }


    @Test
    public void givenUserNameAndPasswordThenReturnUserInfoSuccessfully() throws UnirestException {
        HttpResponse<String> token = Unirest.post("https://gameinn.us.auth0.com/oauth/token")
            .header("content-type", "application/json")
            .body("{\"connection\":\"Username-Password-Authentication\",\"username\":\"admin%40admin.com\",\"password\":\"qwe123QWE%40\",\"client_id\":\"3T69QL1FF2h55f02ujE1ImtMuJLP8Awj\",\"client_secret\":\"B0lOI_6lk2_tpsAgCVCLj4r_YoKDRS6QK4fcGG8KmalaImYkIJKMOCiq_N5KNgqB\",\"audience\":\"https://gameinn.us.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
            .asString();

            // openid profile email
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
        HttpResponse<String> response = Unirest.get("https://gameinn.us.auth0.com/api/v2/users/auth0%7C60e56d0a68dabc00690a5eab?include_fields=true")
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
}