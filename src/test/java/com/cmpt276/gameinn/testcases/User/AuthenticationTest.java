package com.cmpt276.gameinn.testcases.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.Test;

public class AuthenticationTest {
    @Test
    public void givenUserNameAndPasswordThenReturnTokenSuccessfully() throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://gameinn.us.auth0.com/oauth/token")
            .header("content-type", "application/json")
            .body("{\"connection\":\"Username-Password-Authentication\",\"username\":\"admin%40admin.com\",\"password\":\"qwe123QWE%40\",\"client_id\":\"3T69QL1FF2h55f02ujE1ImtMuJLP8Awj\",\"client_secret\":\"B0lOI_6lk2_tpsAgCVCLj4r_YoKDRS6QK4fcGG8KmalaImYkIJKMOCiq_N5KNgqB\",\"audience\":\"https://gameinn.us.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
            .asString();

            assertNotNull(response.getBody());
            assertEquals(200, response.getStatus());
        }
}