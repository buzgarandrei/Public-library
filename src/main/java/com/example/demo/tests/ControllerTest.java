package com.example.demo.tests;

import com.example.demo.models.Book;
import com.example.demo.models.User;
import com.example.demo.requests.LoanRequest;
import com.example.demo.requests.RequestWithString;
import com.example.demo.requests.ReturnRequest;
import com.example.demo.responses.ReturnResponse;
import com.example.demo.responses.StateResponse;
import com.sipios.springsearch.anotation.SearchSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class ControllerTest {

    @Autowired
    ServiceTest serviceTest;

    @Autowired
    BookRepositoryTest2 bookRepository;

    @Autowired
    BookTestRepository bookTestRepository;

    @RequestMapping(value = "/populate")
    public void populate() throws ParseException {
        serviceTest.populate();
    }

    @Transactional
    @RequestMapping(value = "/searchh")
    public List<Book> search(@RequestBody RequestWithString request ) {
        String keyword = request.getKeyword();
        List<Book> returnList = bookRepository.search(keyword);
        for (Book book : returnList) {
            book.setClientList(null);
        }
        return returnList;
    }

    @Transactional
    @RequestMapping(value = "/userss")
    public List<User> getUsers() {
        List<User> users = serviceTest.getUsers();

        return users;
    }

    @Transactional
    @RequestMapping(value = "/bookss")
    public ResponseEntity<List<Book>> searchForBooks(@SearchSpec Specification<Book> specs) {

        List<Book> bookList = bookRepository.findAll(Specification.where(specs));
        for (Book book : bookList) {
            book.setClientList(null);
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @RequestMapping(value = "/loanBookk", method = RequestMethod.POST)
    public StateResponse loanBook(@RequestBody LoanRequest loanRequest) throws ParseException {

        return bookTestRepository.loanBook(loanRequest);
    }

    @RequestMapping(value = "/returnBookk",method = RequestMethod.POST)
    public ReturnResponse returnBook(@RequestBody ReturnRequest returnRequest) {

        return bookTestRepository.returnBook(returnRequest);

    }


}
