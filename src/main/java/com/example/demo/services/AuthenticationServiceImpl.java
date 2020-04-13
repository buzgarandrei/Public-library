package com.example.demo.services;

import com.example.demo.responses.LoginResponse;
import com.example.demo.responses.StateResponse;
import com.example.demo.utils.RoleEnum;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String TOKEN = "TOKEN";

    private static Map<String,LoginResponse> userSessionMap = new HashMap<>();

    public static final boolean VALIDATION_ACTIVE = false;

    public StateResponse registerLogin(LoginResponse response) {
        StateResponse stateResponse = new StateResponse();
        if(response == null || response.getToken() == null || response.getId() == null || response.getEmail() == null || response.getRole() == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        String token = response.getToken();
        userSessionMap.put(token,response);
        stateResponse.setSuccess(true);
        return stateResponse;
    }

    public boolean validateTokenAndRole(HttpServletRequest request, RoleEnum roleToBeVerified) {
        if(VALIDATION_ACTIVE == false) return true;
        String token = request.getHeader(TOKEN);
        if(token == null)  return false;
        LoginResponse response = userSessionMap.get(token);
        if(response == null) return false;
        if(response.getRole() != roleToBeVerified.toString()) return false;
        return true;
    }
    public StateResponse logout(HttpServletRequest request) {
        StateResponse stateResponse = new StateResponse();
        String token = request.getHeader(TOKEN);
        if(token == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        LoginResponse response = userSessionMap.get(token);
        if(response == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        if(userSessionMap.remove(response) != null)
            stateResponse.setSuccess(true);
        else stateResponse.setSuccess(false);
        return stateResponse;

    }

}
