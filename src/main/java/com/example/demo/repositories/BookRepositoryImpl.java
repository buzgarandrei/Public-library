package com.example.demo.repositories;

import com.example.demo.models.Appointment;
import com.example.demo.models.Book;
import com.example.demo.models.Client;
import com.example.demo.models.User;
import com.example.demo.requests.LoanRequest;
import com.example.demo.requests.RequestWithId;
import com.example.demo.requests.ReturnRequest;
import com.example.demo.responses.ReturnResponse;
import com.example.demo.responses.StateResponse;
import com.example.demo.utils.Utils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public List<Book> getAllBooks() {
        Query query = entityManager.createQuery("select books from Book books", Book.class);
        List<Book> bookList = query.getResultList();
        return bookList;
    }

    @Override
    @Transactional
    public StateResponse addBook(Book book) {
        StateResponse stateResponse = new StateResponse();
       if(book.getPricePerDay() == 0.f || book.getDaysForLoaning() == 0 || book.getAuthor() == null ||
           book.getEdition() == 0 || book.getName() == null || book.getPublishingHouse() == null) {
           stateResponse.setSuccess(false);
           return stateResponse;
       }
       entityManager.persist(book);
       stateResponse.setSuccess(true);
       return stateResponse;
    }

    @Override
    @Transactional
    public StateResponse updateBook(Book book) {
        StateResponse stateResponse = new StateResponse();
        Book bookFromDb = entityManager.find(Book.class,book.getId());
        if(bookFromDb == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        if(book.getPublishingHouse() != null)
            bookFromDb.setPublishingHouse(book.getPublishingHouse());
        if(book.getName() != null)
            bookFromDb.setName(book.getName());
        if(book.getEdition() != 0)
            bookFromDb.setEdition(book.getEdition());
        if(book.getAuthor() != null)
            bookFromDb.setAuthor(book.getAuthor());
        if(book.getPricePerDay() != 0.f)
            bookFromDb.setPricePerDay(book.getPricePerDay());
        if(book.getDaysForLoaning() != 0)
            bookFromDb.setDaysForLoaning(book.getDaysForLoaning());
        if(book.isAvailable() != bookFromDb.isAvailable())
            bookFromDb.setAvailable(book.isAvailable());

        entityManager.merge(bookFromDb);
        stateResponse.setSuccess(true);
        return stateResponse;

    }

    @Override
    @Transactional
    public void deleteBook(RequestWithId request) {
        Book book = entityManager.find(Book.class,request.getId());
        entityManager.remove(book);
    }

    @Override
    @Transactional
    public List<Book> search(String keyword) {
        Query query = entityManager.createQuery("SELECT books FROM Book books " +
                                                    "where books.name like :keyword  and books.isAvailable = true", Book.class)
                                    .setParameter("keyword", "%" + keyword + "%");
        List<Book> bookList = query.getResultList();
        return bookList;
    }

    @Override
    @Transactional
    public StateResponse loanBook(LoanRequest loanRequest) throws ParseException {
        StateResponse stateResponse = new StateResponse();
        if(loanRequest.getIdBook() == null || loanRequest.getIdUser() == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        Client client = entityManager.find(Client.class, loanRequest.getIdUser());
        if(client == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        Book book = entityManager.find(Book.class,loanRequest.getIdBook());
        if(book == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }

        Appointment appointment = new Appointment();
        appointment.setBook(book);
        appointment.setClient(client);
        Date date = new Date();
        String dateStartParsed = Utils.DATE_FORMAT.format(date);
        appointment.setStartDate(Utils.DATE_FORMAT.parse(dateStartParsed));

        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(book.getDaysForLoaning());
        Date endDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String endDateParsed = Utils.DATE_FORMAT.format(endDate);
        appointment.setEndDate(Utils.DATE_FORMAT.parse(endDateParsed));
        appointment.setReturned(false);
        book.setAvailable(false);
        if(!client.getBookList().contains(book))
            client.getBookList().add(book);

        entityManager.merge(client);
        entityManager.merge(book);
        entityManager.persist(appointment);
        stateResponse.setSuccess(true);
        stateResponse.setReturnDate(endDate);
        stateResponse.setIdAppointment(appointment.getId());
        return stateResponse;
    }

    @Override
    @Transactional
    public ReturnResponse returnBook(ReturnRequest returnRequest) {
        ReturnResponse response = new ReturnResponse();
        if(returnRequest.getIdBook() == null || returnRequest.getIdUser() == null) {
            response.setSuccess(false);
            return response;
        }
        Client client= entityManager.find(Client.class, returnRequest.getIdUser());
        if(client == null) {
            response.setSuccess(true);
            return response;
        }
        Book book = entityManager.find(Book.class,returnRequest.getIdBook());
        if(book == null) {
            response.setSuccess(false);
            return response;
        }

        Query query = entityManager.createQuery("select app from Appointment  app " +
                "where app.id = :id and app.returned = false ",Appointment.class)
                .setParameter("id",returnRequest.getId());
        Appointment appointment = (Appointment) query.getResultList().stream().findFirst().orElse(null);
        if(appointment == null) {
            response.setSuccess(false);
            return response;
        }
        if(appointment.getBook() != book) {
            response.setSuccess(false);
            return response;
        }
        if(appointment.getClient() != client) {
            response.setSuccess(false);
            return response;
        }
        book.setAvailable(true);
        Date date = new Date();
        float toBePaid = Utils.getNoDays(appointment.getEndDate(),date) * book.getPricePerDay();
        if(toBePaid <= 0.f)
            response.setAmount(0.f);
        else
            response.setAmount(toBePaid);
        appointment.setAmount(response.getAmount());
        appointment.setReturned(true);
        entityManager.merge(appointment);
        entityManager.merge(book);

        response.setSuccess(true);
        return response;
    }
}
