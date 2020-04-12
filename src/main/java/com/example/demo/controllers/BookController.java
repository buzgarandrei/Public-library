package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.requests.LoanRequest;
import com.example.demo.requests.RequestWithId;
import com.example.demo.requests.ReturnRequest;
import com.example.demo.responses.ReturnResponse;
import com.example.demo.responses.StateResponse;
import com.example.demo.services.AuthenticationService;
import com.example.demo.requests.RequestWithString;
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
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping("books")
    public List<Book> getAllBooks(HttpServletRequest request) {
        boolean validated = authenticationService.validateTokenAndRole(request, RoleEnum.LIBRARIAN);
        if(validated == false) return null;
        List<Book> bookList = bookRepository.getAllBooks();
        for (Book book : bookList) {
            book.setClientList(null);
        }
        return bookList;
    }

    @RequestMapping(value = "addBook", method = RequestMethod.POST)
    public StateResponse addBook(HttpServletRequest httpServletRequest, @RequestBody Book book) {
        boolean validated = authenticationService.validateTokenAndRole(httpServletRequest, RoleEnum.LIBRARIAN);
        if(validated == false) return null;
        return bookRepository.addBook(book);
    }

    @RequestMapping(value = "updateBook", method = RequestMethod.POST)
    public StateResponse updateBook(HttpServletRequest request, @RequestBody Book book) {
        boolean validated = authenticationService.validateTokenAndRole(request, RoleEnum.LIBRARIAN);
        if(validated == false) return null;
        return bookRepository.updateBook(book);
    }

    @RequestMapping(value = "deleteBook", method = RequestMethod.POST)
    public StateResponse deleteBook(HttpServletRequest request, @RequestBody RequestWithId requestWithId) {
        boolean validated = authenticationService.validateTokenAndRole(request, RoleEnum.LIBRARIAN);
        if(validated == false) return null;
        StateResponse stateResponse = new StateResponse();
        try {
            bookRepository.deleteBook(requestWithId);
            stateResponse.setSuccess(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            stateResponse.setSuccess(false);
        }
        return stateResponse;
    }

    @RequestMapping(value = "search")
    public List<Book> search(@RequestBody RequestWithString request ) {
        String keyword = request.getKeyword();
        List<Book> returnList = bookRepository.search(keyword);
        for (Book book : returnList) {
            book.setClientList(null);
        }
        return returnList;
    }

    @RequestMapping(value = "loanBook", method = RequestMethod.POST)
    public StateResponse loanBook(HttpServletRequest httpServletRequest, @RequestBody LoanRequest loanRequest) throws ParseException {
        boolean validated = authenticationService.validateTokenAndRole(httpServletRequest, null);
        if(validated == false) return null;
        return bookRepository.loanBook(loanRequest);
    }

    @RequestMapping(value = "returnBook",method = RequestMethod.POST)
    public ReturnResponse returnBook(@RequestBody ReturnRequest returnRequest, HttpServletRequest httpServletRequest) {
        boolean validated = authenticationService.validateTokenAndRole(httpServletRequest, null);
        if(validated == false) return null;
        return bookRepository.returnBook(returnRequest);

    }
}
