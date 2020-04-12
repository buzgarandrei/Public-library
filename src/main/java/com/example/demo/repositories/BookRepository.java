package com.example.demo.repositories;

import com.example.demo.models.Book;
import com.example.demo.requests.LoanRequest;
import com.example.demo.requests.RequestWithId;
import com.example.demo.requests.ReturnRequest;
import com.example.demo.responses.ReturnResponse;
import com.example.demo.responses.StateResponse;

import java.text.ParseException;
import java.util.List;


public interface BookRepository  {
    List<Book> getAllBooks();

    StateResponse addBook(Book book);

    StateResponse updateBook(Book book);

    void deleteBook(RequestWithId request);

    List<Book> search(String keyword);

    StateResponse loanBook(LoanRequest loanRequest) throws ParseException;

    ReturnResponse returnBook(ReturnRequest returnRequest);
}
