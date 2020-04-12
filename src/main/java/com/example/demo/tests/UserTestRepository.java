package com.example.demo.tests;

import com.example.demo.models.Client;
import com.example.demo.models.Librarian;
import com.example.demo.models.User;
import com.example.demo.utils.RoleEnum;
import org.hibernate.annotations.GenerationTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserTestRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void populate() {
        Client andrei = new Client();
        andrei.setFirstName("Andrei");
        andrei.setLastName("Buzgar");
        andrei.setAddress("str. Muncitorilor, nr.22, ap.30, bloc M9, scara 3");
        andrei.setEmail("buzgarandrei@gmail.com");
        andrei.setPassword("123");
        andrei.setPhoneNumber("0743049803");
        andrei.setRoleEnum();
        entityManager.persist(andrei);

        Client marcu = new Client();
        marcu.setPhoneNumber("0648936587");
        marcu.setEmail("marcu.marian@gmail.com");
        marcu.setPassword("124");
        marcu.setAddress("str. Salajului, nr. 33, ap.14, bloc F, scara 6");
        marcu.setFirstName("Marcu");
        marcu.setLastName("Marian");
        marcu.setRoleEnum();

        entityManager.persist(marcu);

        Librarian tudor = new Librarian();
        tudor.setLastName("Rotaru");
        tudor.setFirstName("Tudor");
        tudor.setAddress("str. Bucovinei, nr.98, ap.22, bloc A, scara 1");
        tudor.setEmail("tudor.rotaru@gmail.com");
        tudor.setPassword("125");
        tudor.setPhoneNumber("0743947236");
        tudor.setRoleEnum();
        entityManager.persist(tudor);
    }

    @Transactional
    public List<User> getUsers() {
        Query query = entityManager.createQuery("select users from User  users ", User.class);
        List<User> users = query.getResultList();
        return users;
    }
}
