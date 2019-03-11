package com.pwc.addressbook;

import com.pwc.addressbook.model.Friend;
import com.pwc.addressbook.model.User;
import com.pwc.addressbook.repository.UserRepository;
import com.pwc.addressbook.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Mock
    private UserRepository userRepository;

    private String userId;
    private UserService toTest;


    @Before
    public void setup() {
        toTest = new UserService(userRepository);
        userId = "1";
    }

    @Test
    public void userCanGetAddressBook() {
        User currentUser = new User();
        Friend friend = new Friend("Joe", "21312312");
        currentUser.setAddressBook(new HashSet<>(Arrays.asList(friend)));
        when(userRepository.findById("1")).thenReturn(Optional.of(currentUser));

        Set<Friend> book = toTest.getAddressBook(userId);

        assertTrue(!book.isEmpty());
        assertEquals(book.size(), 1);
        assertTrue(book.contains(friend));
    }

    @Test
    public void anExceptionIsThrownWhenUserDoesNotExist() {
        User currentUser = new User();
        when(userRepository.findById("1")).thenReturn(Optional.ofNullable(null));
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("User [1] could not be found!");
        Set<Friend> book = toTest.getAddressBook(userId);
    }

    @Test
    public void userCanGetUniqueFriendsListAfterComparingAnotherList() {
        User currentUser = new User();
        Friend f1 = new Friend("Joe", "21312312");
        Friend f2 = new Friend("Sally", "21312312");
        Friend f3 = new Friend("Marge", "21312312");

        currentUser.setAddressBook(new HashSet<>(Arrays.asList(f1, f2, f3)));
        when(userRepository.findById("1")).thenReturn(Optional.of(currentUser));

        Friend s1 = new Friend("Marge", "21312312");
        Friend s2 = new Friend("Joe", "21312312");
        Friend s3 = new Friend("Tony", "21312312");
        Set<Friend> target = new HashSet<>(Arrays.asList(s1, s2, s3));

        Set<String> names = toTest.getFriendsThatAreNotInListProvided(userId, target);

        assertEquals(names.size(), 2);
        assertTrue(names.contains(f2.getName()));
        assertTrue(names.contains(s3.getName()));
    }

    @Test
    public void newUserCanBeCreated() {
        User user = new User();
        User repoUser = user;
        repoUser.setId("set");
        when(userRepository.save(user)).thenReturn(repoUser);

        User created = toTest.save(user);

        assertEquals(created.getId(), repoUser.getId());
        assertEquals(created.getName(), user.getName());
        assertEquals(created.getAddressBook(), user.getAddressBook());
    }

}
