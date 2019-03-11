package com.pwc.addressbook;

import com.pwc.addressbook.controller.UserController;
import com.pwc.addressbook.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    UserController toTest;

    @Autowired
    UserRepository userRepository;

    TestRestTemplate testRestTemplate;

    @Value("${server.port}")
    private String port;

    @Before
    public void setup() {
        testRestTemplate = new TestRestTemplate();
//        recreateTable(User.class);
    }


    @Test
    public void newUserMustContainName() {
        assert true;
    }

    @Test
    public void newUserMustContainAddressBook() {
        assert true;
    }

}
