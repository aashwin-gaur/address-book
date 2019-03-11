package com.pwc.addressbook.service;

import com.pwc.addressbook.model.Friend;
import com.pwc.addressbook.model.User;
import com.pwc.addressbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addFriend(String userId, Friend friend) {
        User user = getUser(userId);
        user.getAddressBook().add(friend);
        return save(user);
    }

    public Set<Friend> getAddressBook(String userId) {
        User user = getUser(userId);
        return user.getAddressBook();
    }

    public Set<String> getFriendsThatAreNotInListProvided(String userId, Set<Friend> addressbook) {
        User user = getUser(userId);

        Set<String> providedNames = addressbook
                .stream()
                .map(Friend::getName)
                .collect(Collectors.toSet());

        Set<String> usersFriends = user.getAddressBook()
                .stream()
                .map(Friend::getName)
                .collect(Collectors.toSet());

        return getMutuallyExclusiveItems(usersFriends, providedNames);
    }

    private User getUser(String userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException(String.format("User [%s] could not be found!", userId)));
    }

    private Set<String> getMutuallyExclusiveItems(Set<String> usersFriends, Set<String> providedNames) {
        Set<String> result = new HashSet<String>(providedNames);
        for (String name : usersFriends) {
            if (!result.add(name)) {
                result.remove(name);
            }
        }
        return result;
    }

}
