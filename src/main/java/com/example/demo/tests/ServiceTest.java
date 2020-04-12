package com.example.demo.tests;

import com.example.demo.models.Book;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Service
public class ServiceTest {

    @Autowired
    UserTestRepository userTestRepository;

    @Autowired
    BookTestRepository bookTestRepository;

    @Autowired
    AppointmentTestRepository appointmentTestRepository;

    public void populate() throws ParseException {
        bookTestRepository.populate();
        userTestRepository.populate();
        appointmentTestRepository.populate();
    }

    @Transactional
    public List<User> getUsers() {
        return userTestRepository.getUsers();
    }
}
