package com.example.demo.tests;

import com.example.demo.models.Appointment;
import com.example.demo.models.Book;
import com.example.demo.models.Client;
import com.example.demo.models.User;
import com.example.demo.utils.Utils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Repository
public class AppointmentTestRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void populate() throws ParseException {

        Appointment appointment = new Appointment();
        Book book = entityManager.find(Book.class,1L);
        appointment.setBook(book);
        appointment.setClient(entityManager.find(Client.class,1L));
        Date date = new Date();
        String dateStartParsed = Utils.DATE_FORMAT.format(date);
        appointment.setStartDate(Utils.DATE_FORMAT.parse(dateStartParsed));

        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(book.getDaysForLoaning());
        Date endDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String endDateParsed = Utils.DATE_FORMAT.format(endDate);
        appointment.setEndDate(Utils.DATE_FORMAT.parse(endDateParsed));
        appointment.setReturned(false);
        entityManager.persist(appointment);


    }
}
