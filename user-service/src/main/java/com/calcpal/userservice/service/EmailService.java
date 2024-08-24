package com.calcpal.userservice.service;

import com.calcpal.userservice.collection.User;

public interface EmailService {
    boolean sendActivationMail(User user);

    boolean sendResetPasswordMail(User user);
}
