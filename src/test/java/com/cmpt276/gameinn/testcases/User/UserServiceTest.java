package com.cmpt276.gameinn.testcases.User;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.IUserRepository;
import com.cmpt276.gameinn.services.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @MockBean
    private IUserRepository userRepository;

    @InjectMocks
    private UserService service;

    
    // Test addUser
    @Test
    public void shouldUserServiceAddUserCreateNewUserCorrectly(){
        User returnedUser = service.addUser("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        Assertions.assertNotNull(returnedUser, "addUser creates new user successfully");
    }

    @Test
    public void shouldUserServiceAddUserReturnExistingUserCorrectly(){
        User testUser = new User("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        doReturn(testUser).when(userRepository).save(any());

        User returnedUser = service.addUser("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        Assertions.assertNotNull(returnedUser, "addUser returns existing user successfully");
    }

    // Test getUsers
    @Test
    public void shouldUserServiceGetUsersReturnAllUsersCorrectly(){
        User testUser1 = new User("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        User testUser2 = new User("asd", "fwfwf", "qwd@trc.com", "phto_ex", "ma");
        doReturn(Arrays.asList(testUser1, testUser2)).when(userRepository).findAll();

        List<User> returnedUserList = service.getUsers();
        Assertions.assertEquals(2, returnedUserList.size(), "getUsers returns all 2 users successfully");
    }

    // Test getUserById
    @Test
    public void shouldUserServiceGetUserByIdReturnExistingUserCorrectly(){
        User testUser = new User("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        Long id = 1L;
        doReturn(Optional.of(testUser)).when(userRepository).findById(id);

        User returnedUser = service.getUserById(id);
        Assertions.assertNotNull(returnedUser, "getUserById returns existing user successfully with given id");
    }

    @Test
    public void shouldUserServiceGetUserByIdReturnNonExistingUserFail(){
        User testUser = new User("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        Long id = 1L;
        doReturn(Optional.of(testUser)).when(userRepository).findById(id);

        Long nonExistId = 2L;

        Throwable exception = assertThrows(
        IllegalArgumentException.class, () -> {
                User returnedUser = service.getUserById(nonExistId);
            }
        );
        Assertions.assertEquals("No User with " + nonExistId, exception.getMessage());
    }

    // Test getUserBySub
    @Test
    public void shouldUserServiceGetUserBySubReturnExistingUserCorrectly(){
        String testSub = "123456";
        User testUser = new User(testSub, "Lee", "trc@trc.com", "phto_ex", "ma");
        doReturn(testUser).when(userRepository).findUserBySub(testSub);

        User returnedUser = service.getUserBySub(testSub);
        Assertions.assertNotNull(returnedUser, "getUserBySub returns existing user successfully with given sub");
    }

    @Test
    public void shouldUserServiceGetUserBySubReturnNonExistingUserFail(){
        String testSub = "123456";
        User testUser = new User(testSub, "Lee", "trc@trc.com", "phto_ex", "ma");
        Long id = 1L;
        doReturn(Optional.of(testUser)).when(userRepository).findById(id);

        String nonExistId = "aaa";
        User returnedUser = service.getUserBySub(nonExistId);

        Assertions.assertNull(returnedUser, "getUserBySub returns non existing user fail with given sub");
    }

    // Test updateUser
    @Test
    public void shouldUserServiceUpdateUserReturnUpdateUserSuccessfully(){
        User testUser = new User("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        when(userRepository.findUserBySub("123456")).thenReturn(testUser); //expect a fetch, return a "fetched" person;
        testUser.setName("kwak");
        testUser.setEmail("zzz@zzz.com");
        testUser.setPhoto("master");
        testUser.setRole("manager");
        User returnedUser = service.updateUser(testUser);

        Assertions.assertEquals(returnedUser.getName(), testUser.getName(), "updateUser update name of existing user with given name");
        Assertions.assertEquals(returnedUser.getEmail(), testUser.getEmail(), "updateUser update email of existing user with given email");
        Assertions.assertEquals(returnedUser.getPhoto(), testUser.getPhoto(), "updateUser update photo of existing user with given photo");
        Assertions.assertEquals(returnedUser.getRole(), testUser.getRole(), "updateUser update role of existing user with given role");
    }

    @Test
    public void shouldUserServiceUpdateNonExistingUserReturnExceptionUserFail(){
        User testUser = new User("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        when(userRepository.findUserBySub("123456")).thenReturn(testUser); //expect a fetch, return a "fetched" person;
        testUser.setSubId("asdfasd");
        testUser.setName("kwak");
        testUser.setEmail("zzz@zzz.com");
        testUser.setPhoto("master");
        testUser.setRole("manager");

        Throwable exception = assertThrows(
        IllegalArgumentException.class, () -> {
                User returnedUser = service.updateUser(testUser);
            }
        );
        Assertions.assertEquals("No User with asdfasd", exception.getMessage());
    }

    // Test deleteUser
    @Test
    public void shouldUserServiceDeleteUserReturnDeleteUserSuccessfully(){
        User testUser = new User("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        when(userRepository.findUserBySub("123456")).thenReturn(testUser);
        service.deleteUser(testUser.getSubId());
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    public void shouldUserServiceDeleteNonExistingUserReturnDeleteUserFail(){
        User testUser = new User("123456", "Lee", "trc@trc.com", "phto_ex", "ma");
        when(userRepository.findUserBySub("123456")).thenReturn(testUser);
        
        Throwable exception = assertThrows(
        IllegalArgumentException.class, () -> {
                service.deleteUser("asdasd");
            }
        );
        Assertions.assertEquals("No User with asdasd", exception.getMessage());
    }
}
