package com.example.demo.services;

import com.example.demo.responses.LoginResponse;
import com.example.demo.responses.StateResponse;
import com.example.demo.utils.RoleEnum;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    public StateResponse registerLogin(LoginResponse response);

    public boolean validateTokenAndRole(HttpServletRequest request, RoleEnum roleToBeVerified);

    public StateResponse logout(HttpServletRequest request);
}
