package com.cmpt276.gameinn.testcases.User;


import com.cmpt276.gameinn.models.User;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserEntityTest {
    // id is auto-generated, so id must not be null
    // socialAccountsList is not implemented, so socialAccountsList will be tested next iteration
    static User user;

    @BeforeAll
    static void setUpName(){
        user = new User();
        user.setName("kwak");
        user.setSubId("subIdTest");
        user.setEmail("test@test.com");
        user.setPhoto("www.resource.com");
        user.setAbout("This is me");
        user.setRole("testRole");
    }

    // Test name setter and getter
    @Test
    public void shouldUserNameSetterGetterCorrectly(){
        assertEquals("kwak", user.getName());
        user.setName("steve");
        assertEquals("steve",user.getName());
    }

    @Test
    public void shouldUserGetNameCorrectly(){
        assertEquals("steve", user.getName());
    }

    
    @Test
    public void shouldUserNameGetterFailWithDifferentName(){
        assertNotEquals("aaaaa",user.getName());
    }


    // Test sub setter and getter
    @Test
    public void shouldUserSubSetterGetterCorrectly(){
        assertEquals("subIdTest", user.getSubId());
        user.setSubId("subIdTestChanged");
        assertEquals("subIdTestChanged",user.getSubId());
    }

    @Test
    public void shouldUserGetSubCorrectly(){
        assertEquals("subIdTestChanged", user.getSubId());
    }
    
    @Test
    public void shouldUserSubGetterFailWithDifferentSub(){
        assertNotEquals("qwe123",user.getSubId());
    }


    // Test email setter and getter
    @Test
    public void shouldUserEmailSetterGetterCorrectly(){
        assertEquals("test@test.com", user.getEmail());
        user.setEmail("changed@test.com");
        assertEquals("changed@test.com", user.getEmail());
    }

    @Test
    public void shouldUserGetEmailCorrectly(){
        assertEquals("changed@test.com", user.getEmail());
    }

    @Test
    public void shouldUserEmailGetterFailWithDifferentEmail(){
        assertNotEquals("qwer@test.com", user.getEmail());
    }

    // Test photo setter and getter
    @Test
    public void shouldUserPhotoSetterGetterCorrectly(){
        assertEquals("www.resource.com", user.getPhoto());
        user.setPhoto("www.abc.com");
        assertEquals("www.abc.com", user.getPhoto());
    }

    @Test
    public void shouldUserGetPhotoCorrectly(){
        assertEquals("www.abc.com", user.getPhoto());
    }

    @Test
    public void shouldUserPhotoGetterFailWithDifferentPhoto(){
        assertNotEquals("fail.com", user.getPhoto());
    }

    // Test about setter and getter
    @Test
    public void shouldUserAboutSetterGetterCorrectly(){
        assertEquals("This is me", user.getAbout());
        user.setAbout("This is not");
        assertEquals("This is not", user.getAbout());
    }

    @Test
    public void shouldUserGetAboutCorrectly(){
        assertEquals("This is me", user.getAbout());
    }

    @Test
    public void shouldUserAboutGetterFailWithDifferentAbout(){
        assertNotEquals("This is fail", user.getAbout());
    }
    
    // Test role setter and getter
    @Test
    public void shouldUserRoleSetterGetterCorrectly(){
        assertEquals("testRole", user.getRole());
        user.setRole("admin");
        assertEquals("admin", user.getRole());
    }

    @Test
    public void shouldUserGetRoleCorrectly(){
        assertEquals("admin", user.getRole());
    }

    @Test
    public void shouldUserRoleGetterFailWithDifferentRole(){
        assertNotEquals("notauth", user.getRole());
    }
}
