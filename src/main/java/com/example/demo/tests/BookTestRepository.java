package com.example.demo.tests;

import com.example.demo.models.*;
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
public class BookTestRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void populate() {
        Book idiot1 = new Book();
        idiot1.setName("The Idiot");
        idiot1.setAuthor("Dostoievsky Brothers");
        idiot1.setAvailable(true);
        idiot1.setDaysForLoaning(10);
        idiot1.setEdition(4);
        idiot1.setPublishingHouse("RAO Publishing House");
        idiot1.setPricePerDay(0.7f);
        entityManager.persist(idiot1);

        Book idiot2 = new Book();
        idiot2.setName("The Idiot");
        idiot2.setAuthor("Dostoievsky Brothers");
        idiot2.setAvailable(true);
        idiot2.setDaysForLoaning(10);
        idiot2.setEdition(3);
        idiot2.setPublishingHouse("RAO Publishing House");
        idiot2.setPricePerDay(0.8f);
        entityManager.persist(idiot2);

        Book idiot3 = new Book();
        idiot3.setName("The Idiot");
        idiot3.setAuthor("Dostoievsky Brothers");
        idiot3.setAvailable(true);
        idiot3.setDaysForLoaning(10);
        idiot3.setEdition(3);
        idiot3.setPublishingHouse("Diverta");
        idiot3.setPricePerDay(0.7f);
        entityManager.persist(idiot3);

        Book gameOfThrones = new Book();
        gameOfThrones.setPublishingHouse("RAO Publishing House");
        gameOfThrones.setEdition(1);
        gameOfThrones.setDaysForLoaning(14);
        gameOfThrones.setAvailable(true);
        gameOfThrones.setAuthor("George R. R. Martin");
        gameOfThrones.setName("A Game of Thrones");
        gameOfThrones.setPricePerDay(0.5f);
        entityManager.persist(gameOfThrones);

        Book gameOfThrones2 = new Book();
        gameOfThrones2.setAvailable(true);
        gameOfThrones2.setDaysForLoaning(10);
        gameOfThrones2.setPricePerDay(0.7f);
        gameOfThrones2.setAuthor("George R.R Martin");
        gameOfThrones2.setEdition(1);
        gameOfThrones2.setName("A Clash of Kings");
        gameOfThrones2.setPublishingHouse("RAO Publishig House");
        entityManager.persist(gameOfThrones2);

        Book gameOfthrones3 = new Book();
        gameOfthrones3.setPublishingHouse("RAO Publishing House");
        gameOfthrones3.setName("A Storm Of Swords");
        gameOfthrones3.setEdition(1);
        gameOfthrones3.setPricePerDay(0.7f);
        gameOfthrones3.setDaysForLoaning(10);
        gameOfthrones3.setAuthor("Geroge R.R. Martin");
        gameOfthrones3.setAvailable(true);
        entityManager.persist(gameOfthrones3);

        Book gameOfThrones4 = new Book();
        gameOfThrones4.setAvailable(true);
        gameOfThrones4.setAuthor("George R.R. Martin");
        gameOfThrones4.setDaysForLoaning(10);
        gameOfThrones4.setPricePerDay(0.8f);
        gameOfThrones4.setEdition(1);
        gameOfThrones4.setName("A Feast For Crows");
        gameOfThrones4.setDaysForLoaning(10);
        entityManager.persist(gameOfThrones4);

        Book gameOfThrones5 = new Book();
        gameOfThrones5.setDaysForLoaning(10);
        gameOfThrones5.setName("A Dance With Dragons");
        gameOfThrones5.setEdition(2);
        gameOfThrones5.setPricePerDay(0.8f);
        gameOfThrones5.setAvailable(true);
        gameOfThrones5.setAuthor("George R.R. Martin");
        gameOfThrones5.setPublishingHouse("Humanitas");
        entityManager.persist(gameOfThrones5);
        
        Book ion = new Book();
        ion.setPublishingHouse("Humanitas");
        ion.setAuthor("Liviu Rebreanu");
        ion.setAvailable(true);
        ion.setPricePerDay(0.3f);
        ion.setDaysForLoaning(9);
        ion.setName("Ion");
        ion.setEdition(5);
        entityManager.persist(ion);

        Book moaraCuNoroc = new Book();
        moaraCuNoroc.setName("Moara cu noroc");
        moaraCuNoroc.setAuthor("Ioan Slavici");
        moaraCuNoroc.setAvailable(true);
        moaraCuNoroc.setDaysForLoaning(8);
        moaraCuNoroc.setEdition(6);
        moaraCuNoroc.setPublishingHouse("Carturesti");
        moaraCuNoroc.setPricePerDay(0.4f);
        entityManager.persist(moaraCuNoroc);
    }

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
        client.getBookList().add(book);

        entityManager.merge(book);
        entityManager.persist(appointment);
        stateResponse.setSuccess(true);
        stateResponse.setReturnDate(endDate);
        stateResponse.setIdAppointment(appointment.getId());
        return stateResponse;
    }

    @Transactional
    public ReturnResponse returnBook(ReturnRequest returnRequest) {
        ReturnResponse response = new ReturnResponse();
        if(returnRequest.getIdBook() == null || returnRequest.getIdUser() == null) {
            response.setSuccess(false);
            return response;
        }
        Client client = entityManager.find(Client.class, returnRequest.getIdUser());
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
        Appointment appointment = (Appointment) query.getSingleResult();
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
        response.setAmount(Utils.getNoDays(appointment.getEndDate(),date) * book.getPricePerDay());
        appointment.setAmount(response.getAmount());
        entityManager.merge(appointment);
        entityManager.merge(book);

        response.setSuccess(true);
        return response;
    }

}
