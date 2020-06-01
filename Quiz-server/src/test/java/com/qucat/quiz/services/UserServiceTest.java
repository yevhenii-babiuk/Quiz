package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.UserDao;
import com.qucat.quiz.repositories.entities.NotificationSettings;
import com.qucat.quiz.repositories.entities.Token;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.enums.Lang;
import com.qucat.quiz.repositories.entities.enums.MessageInfo;
import com.qucat.quiz.repositories.entities.enums.UserAccountStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private EmailSender emailSender;

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

        final User TEST_USER = User.builder().id(7).status(UserAccountStatus.ACTIVATED).build();
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
        verify(tokenService).getTokenByUserId(7);

        assertFalse(result);
    }

    @Test
    public void registerUserShouldReturnFalseWhenTokenIsNotExpired() {

        final User TEST_USER = User.builder().id(7).status(UserAccountStatus.UNACTIVATED).build();
        final Date TEST_DATE = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(1));
        final Token TEST_TOKEN = Token.builder().expiredDate(TEST_DATE).build();
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

        assertFalse(result);
    }

    @Test
    public void registerUserShouldReturnTrueAndUpdateUser() {

        final User TEST_USER = User.builder().id(7).status(UserAccountStatus.UNACTIVATED).build();
        final Token TEST_TOKEN = Token.builder()
                .expiredDate(new Date(1590447610L))
                .build();
        final String TEST_USER_LOGIN = "testLogin";
        final String TEST_USER_MAIL = "example@example.com";
        final String TEST_USER_PASS = "examplePass";

        when(mockUser.getLogin()).thenReturn(TEST_USER_LOGIN);
        when(mockUser.getMail()).thenReturn(TEST_USER_MAIL);
        when(mockUser.getPassword()).thenReturn(TEST_USER_PASS);
        when(userDao.getUserByLogin(anyString())).thenReturn(null);
        when(userDao.getUserByMail(anyString())).thenReturn(TEST_USER);
        doNothing().when(userDao).updateUserLanguage(anyInt(), any(Lang.class));
        when(passwordEncoder.encode(anyString())).thenReturn(TEST_USER_PASS);
        when(imageService.addUserProfileImage()).thenReturn(7);
        when(tokenService.getTokenByUserId(anyInt())).thenReturn(TEST_TOKEN);
        doNothing().when(tokenService).saveToken(any(Token.class));
        when(notificationSettingsService.createNotificationSettings(any(NotificationSettings.class))).thenReturn(true);
        doNothing().when(emailSender).sendMessage(anyString(), anyString(), anyString(), any(MessageInfo.MessageInfoItem.class));

        boolean result = mockService.registerUser(mockUser);

        verify(userDao).update(any(User.class));

        assertTrue(result);
    }

    @Test
    public void registerUserShouldReturnFalseWhenSameUserIsAlreadyExist() {

        final String TEST_USER_LOGIN = "testLogin";
        final String TEST_USER_MAIL = "example@example.com";
        final String TEST_USER_PASS = "examplePass";

        when(mockUser.getLogin()).thenReturn(TEST_USER_LOGIN);
        when(mockUser.getMail()).thenReturn(TEST_USER_MAIL);
        when(mockUser.getPassword()).thenReturn(TEST_USER_PASS);
        when(userDao.getUserByLogin(anyString())).thenReturn(null);
        when(userDao.getUserByMail(anyString())).thenReturn(null);
        when(userDao.save(any(User.class))).thenReturn(-1);
        when(passwordEncoder.encode(anyString())).thenReturn(TEST_USER_PASS);
        when(imageService.addUserProfileImage()).thenReturn(7);

        boolean result = mockService.registerUser(mockUser);

        verify(userDao).save(any(User.class));
        assertFalse(result);
    }

    @Test
    public void registerUserShouldReturnTrueAndSaveUser() {

        final String TEST_USER_LOGIN = "testLogin";
        final String TEST_USER_MAIL = "example@example.com";
        final String TEST_USER_PASS = "examplePass";

        when(mockUser.getLogin()).thenReturn(TEST_USER_LOGIN);
        when(mockUser.getMail()).thenReturn(TEST_USER_MAIL);
        when(mockUser.getPassword()).thenReturn(TEST_USER_PASS);
        when(userDao.getUserByLogin(anyString())).thenReturn(null);
        when(userDao.getUserByMail(anyString())).thenReturn(null);
        when(userDao.save(any(User.class))).thenReturn(3);
        doNothing().when(userDao).updateUserLanguage(anyInt(), any(Lang.class));
        when(passwordEncoder.encode(anyString())).thenReturn(TEST_USER_PASS);
        when(imageService.addUserProfileImage()).thenReturn(7);
        doNothing().when(tokenService).saveToken(any(Token.class));
        when(notificationSettingsService.createNotificationSettings(any(NotificationSettings.class))).thenReturn(true);
        doNothing().when(emailSender).sendMessage(anyString(), anyString(), anyString(), any(MessageInfo.MessageInfoItem.class));

        boolean result = mockService.registerUser(mockUser);

        verify(userDao).save(any(User.class));
        assertTrue(result);
    }

}
