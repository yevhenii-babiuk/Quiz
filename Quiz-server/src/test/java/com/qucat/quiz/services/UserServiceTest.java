package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.UserDao;
import com.qucat.quiz.repositories.dao.implementation.UserDaoImpl;
import com.qucat.quiz.repositories.entities.Token;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.enums.Role;
import com.qucat.quiz.repositories.entities.enums.UserAccountStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private EmailSender emailSender;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDao userDao;

    @Mock
    private TokenService tokenService;

    @Mock
    private ImageService imageService;

    @Mock
    private NotificationSettingsService notificationSettingsService;

    @InjectMocks
    private UserService mockService;

    @Mock
    private User mockUser;

    @Test
    public void registerUserShouldReturnFalseWhenLoginIsAlreadyExist() {

        final User TEST_USER = User.builder().build();
        final String TEST_USER_LOGIN = "testLogin";

        when(mockUser.getLogin()).thenReturn(TEST_USER_LOGIN);
        when(userDao.getUserByLogin(anyString())).thenReturn(TEST_USER);

        boolean result = mockService.registerUser(mockUser);

        verify(userDao).getUserByLogin(TEST_USER_LOGIN);
        assertFalse(result);
    }

    @Test
    public void registerUserShouldReturnFalseWhenUserStatusIsActivated() {

        final User TEST_USER = User.builder().status(UserAccountStatus.ACTIVATED).build();
        final Token TEST_TOKEN = Token.builder().build();
        final String TEST_USER_LOGIN = "testLogin";
        final String TEST_USER_MAIL = "example@example.com";
        final String TEST_USER_PASS = "examplePass";

        when(mockUser.getLogin()).thenReturn(TEST_USER_LOGIN);
        when(mockUser.getMail()).thenReturn(TEST_USER_MAIL);
        when(mockUser.getPassword()).thenReturn(TEST_USER_PASS);
        when(userDao.getUserByLogin(anyString())).thenReturn(null);
        when(userDao.getUserByMail(anyString())).thenReturn(TEST_USER);
        when(passwordEncoder.encode(anyString())).thenReturn(TEST_USER_PASS);
        when(imageService.addUserProfileImage()).thenReturn(7);
        when(tokenService.getTokenByUserId(anyInt())).thenReturn(TEST_TOKEN);

        boolean result = mockService.registerUser(mockUser);

        verify(passwordEncoder).encode(TEST_USER_PASS);
        verify(userDao).getUserByMail(TEST_USER_MAIL);

        assertFalse(result);
    }

}