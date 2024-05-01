package com.calcapl.userservice.service;

import com.calcapl.userservice.collection.User;

public interface EmailService {
    boolean sendActivationMail(User user);
}
