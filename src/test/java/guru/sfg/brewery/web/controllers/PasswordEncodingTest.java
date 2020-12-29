package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;
import org.thymeleaf.cache.StandardParsedTemplateEntryValidator;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class PasswordEncodingTest {

    static final String PASSWORD = "password";

    @Test
    void testBcrypt15() {
        PasswordEncoder bcrypt15 = new BCryptPasswordEncoder(15);
        System.out.println(bcrypt15.encode("tiger"));
    }

    @Test
    void testSHA256() {
        PasswordEncoder sha256 = new StandardPasswordEncoder();
        System.out.println(sha256.encode(PASSWORD));
    }

    @Test
    void testBcript() {
        PasswordEncoder bcript = new BCryptPasswordEncoder();
        System.out.println(bcript.encode(PASSWORD));
        System.out.println(bcript.encode(PASSWORD));
        System.out.println(bcript.encode("guru"));

    }

    @Test
    void testNoop() {
        PasswordEncoder noop = NoOpPasswordEncoder.getInstance();
        System.out.println(noop.encode(PASSWORD));
        System.out.println(noop.encode(PASSWORD));
    }

    @Test
    void testLDAP() {
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        System.out.println(ldap.encode(PASSWORD));
        System.out.println(ldap.encode(PASSWORD));

        String encodedPw = ldap.encode(PASSWORD);
        System.out.println(encodedPw);

        assertTrue(ldap.matches(PASSWORD, encodedPw));
        System.out.println(ldap.encode("tiger"));

    }

    @Test
    void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        String salted = PASSWORD + "MysaltedValue";

        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));


    }
}
