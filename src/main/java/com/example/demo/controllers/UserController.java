package com.example.demo.controllers;

import com.example.demo.models.Appointment;
import com.example.demo.models.Book;
import com.example.demo.models.IssueBookRequest;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.*;
import com.example.demo.responses.LoginResponse;
import com.example.demo.responses.StateResponse;
import com.example.demo.services.AuthenticationService;
import com.example.demo.utils.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "users")
    public List<User> getUserList(HttpServletRequest request) {
        boolean validated = authenticationService.validateTokenAndRole(request, RoleEnum.CLIENT);
        if(validated == false) return null;

        return userRepository.getUserList();
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public StateResponse addUser(@RequestBody AddPersonRequest request) {
        return userRepository.addUser(request);
    }

    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public StateResponse updateUser(@RequestBody AddPersonRequest request, HttpServletRequest httpServletRequest) {
        boolean validated = authenticationService.validateTokenAndRole(httpServletRequest, RoleEnum.LIBRARIAN);
        if(validated == false) return null;
        return userRepository.updateUser(request);
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    public StateResponse deleteUser(HttpServletRequest request, @RequestBody RequestWithId requestWithId) {
        boolean validated = authenticationService.validateTokenAndRole(request, RoleEnum.CLIENT);
        if(validated == false) return null;
        StateResponse stateResponse = new StateResponse();
        try {
            userRepository.deleteUser(requestWithId);
            stateResponse.setSuccess(true);
        }catch (Exception e) {
            stateResponse.setSuccess(false);
        }
        return stateResponse;
    }

    @RequestMapping(value = "login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        LoginResponse response = userRepository.login(request);
        if(response != null){
            if(response.getToken()!= null && response.getRole()!= null && response.getEmail()!= null && response.getId()!= null)
                authenticationService.registerLogin(response);
        }
        return  response;
    }

    @RequestMapping(value = "logout")
    public StateResponse logout(HttpServletRequest request) {
        return authenticationService.logout(request);
    }

    @RequestMapping(value = "getLoanHistory")
    public List<Book> getLoanHistory(HttpServletRequest servletRequest, @RequestBody RequestWithId request) {
        boolean validated = authenticationService.validateTokenAndRole(servletRequest, null);
        if(validated == false) return null;
        List<Book> bookList= userRepository.getLoanHistory(request);
        for (Book book : bookList) {
            book.setClientList(null);
        }
        return bookList;
    }

    @RequestMapping(value = "issueBook", method = RequestMethod.POST)
    public StateResponse issueBook(@RequestBody RequestIssueBook issueBook, HttpServletRequest request ) {
        boolean validated = authenticationService.validateTokenAndRole(request, null);
        if(validated == false) return null;
        return userRepository.issueBook(issueBook);
    }

    @RequestMapping(value = "deleteIssue", method = RequestMethod.POST)
    public StateResponse deleteIssue(@RequestBody RequestWithId request, HttpServletRequest httpServletRequest) {
        boolean validated = authenticationService.validateTokenAndRole(httpServletRequest, RoleEnum.LIBRARIAN);
        if(validated == false) return null;
        StateResponse stateResponse = new StateResponse();
        try {
            userRepository.deleteIssue(request);
            stateResponse.setSuccess(true);
        }catch (Exception e) {
            stateResponse.setSuccess(false);
        }
        return stateResponse;
    }


    @RequestMapping(value = "getIssueBooks")
    public List<IssueBookRequest> getIssueBooks(HttpServletRequest request) {
        boolean validated = authenticationService.validateTokenAndRole(request, RoleEnum.LIBRARIAN);
        if(validated == false) return null;
        List<IssueBookRequest> list= userRepository.getIssueBooks();
        for (IssueBookRequest book : list) {
            book.setClient(null);
        }
        return list;
    }

    @RequestMapping(value = "getIssueHistory")
    public List<IssueBookRequest> getIssueHistory (HttpServletRequest httpServletRequest, @RequestBody RequestWithId request) {
        boolean validated = authenticationService.validateTokenAndRole(httpServletRequest, null);
        if(validated == false) return null;
        List<IssueBookRequest> list = userRepository.getIssueHistory(request);
        for (IssueBookRequest book : list) {
            book.setClient(null);
        }
        return list;
    }

    @RequestMapping(value = "getActiveLoans")
    public List<Appointment> getActiveLoans(HttpServletRequest request, @RequestBody RequestWithId id) throws ParseException {
        boolean validated = authenticationService.validateTokenAndRole(request, null);
        if(validated == false) return null;
        return userRepository.getActiveLoans(id);
    }
}
