package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.Spec.UserSpec;
import com.tick42.quicksilver.models.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.Registration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class PasswordConstraintValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testInvalidPassword() {
        UserSpec userSpec = new UserSpec();
        userSpec.setUsername("memory2018");
        userSpec.setPassword("password");
        userSpec.setRepeatPassword("password");

        Set<ConstraintViolation<UserSpec>> constraintViolations = validator.validate(userSpec);

        Assert.assertEquals(constraintViolations.size(), 1);
    }

    @Test
    public void testValidPasswords() {
        UserSpec userSpec = new UserSpec();
        userSpec.setUsername("memory2018");
        userSpec.setPassword("PasswordTest1234Test");
        userSpec.setRepeatPassword("PasswordTest1234Test");

        Set<ConstraintViolation<UserSpec>> constraintViolations = validator.validate(userSpec);

        Assert.assertEquals(constraintViolations.size(), 0);
    }
}