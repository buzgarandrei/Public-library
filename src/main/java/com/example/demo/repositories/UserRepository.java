package com.example.demo.repositories;

import com.example.demo.models.Appointment;
import com.example.demo.models.Book;
import com.example.demo.models.IssueBookRequest;
import com.example.demo.models.User;
import com.example.demo.requests.AddPersonRequest;
import com.example.demo.requests.LoginRequest;
import com.example.demo.requests.RequestIssueBook;
import com.example.demo.requests.RequestWithId;
import com.example.demo.responses.LoginResponse;
import com.example.demo.responses.StateResponse;

import java.text.ParseException;
import java.util.List;

public interface UserRepository {
    List<User> getUserList();

    StateResponse addUser(AddPersonRequest user);

    StateResponse updateUser(AddPersonRequest user);

    void deleteUser(RequestWithId requestWithId);

    LoginResponse login(LoginRequest request);

    List<Book> getLoanHistory(RequestWithId request);

    StateResponse issueBook(RequestIssueBook issueBook);

    void deleteIssue(RequestWithId request);

    List<IssueBookRequest> getIssueBooks();

    List<IssueBookRequest> getIssueHistory(RequestWithId request);

    List<Appointment> getActiveLoans(RequestWithId request) throws ParseException;
}
