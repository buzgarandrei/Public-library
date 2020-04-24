package com.example.demo.repositories;

import com.example.demo.models.*;
import com.example.demo.requests.*;
import com.example.demo.responses.LoginResponse;
import com.example.demo.responses.StateResponse;
import com.example.demo.utils.RoleEnum;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public List<User> getUserList() {
        Query query = entityManager.createQuery("select user.id, user.address, user.email, user.firstName, user.lastName, user.password, user.phoneNumber from User user", Tuple.class);
        List<Tuple> tuples = query.getResultList();
        List<User> users = new ArrayList<>();
        for (Tuple tuple : tuples) {
            User user = new User();
            user.setId((Long) tuple.get(0));
            user.setAddress((String) tuple.get(1));
            user.setEmail((String) tuple.get(2));
            user.setFirstName((String) tuple.get(3));
            user.setLastName((String) tuple.get(4));
            user.setPassword((String) tuple.get(5));
            user.setPhoneNumber((String) tuple.get(6));
            users.add(user);
        }
        return users;
    }

    @Transactional
    @Override
    public StateResponse addUser(AddPersonRequest request) {
        StateResponse stateResponse = new StateResponse();
        RequestWithString string = request.getString();
        if (string.getKeyword() == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        if (request.getUser() == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        User user = request.getUser();
        if (user.getAddress() == null || user.getEmail() == null || user.getFirstName() == null
                || user.getLastName() == null || user.getPassword() == null || user.getPhoneNumber() == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        if (!(string.getKeyword().equals(RoleEnum.CLIENT.name()) || string.getKeyword().equals(RoleEnum.LIBRARIAN.name()))) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }

        if (string.getKeyword().equals(RoleEnum.LIBRARIAN.name())) {
            Librarian librarian = new Librarian();
            librarian.setAddress(user.getAddress());
            librarian.setEmail(user.getEmail());
            librarian.setFirstName(user.getFirstName());
            librarian.setLastName(user.getLastName());
            librarian.setPhoneNumber(user.getPhoneNumber());
            librarian.setPassword(user.getPassword());
            librarian.setRoleEnum();
            entityManager.persist(librarian);

        }
        if (string.getKeyword().equals(RoleEnum.CLIENT.name())) {
            Client client = new Client();
            client.setAddress(user.getAddress());
            client.setEmail(user.getEmail());
            client.setFirstName(user.getFirstName());
            client.setLastName(user.getLastName());
            client.setPhoneNumber(user.getPhoneNumber());
            client.setPassword(user.getPassword());
            client.setRoleEnum();
            entityManager.persist(client);
        }

        stateResponse.setSuccess(true);
        return stateResponse;

    }

    @Transactional
    @Override
    public StateResponse updateUser(AddPersonRequest request) {
        StateResponse stateResponse = new StateResponse();
        if (request == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        if (request.getUser() == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        User user = request.getUser();
        if (request.getString() == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        if (!(request.getString().getKeyword().equals(RoleEnum.LIBRARIAN.name()) || request.getString().getKeyword().equals(RoleEnum.CLIENT.name()))) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        if (request.getString().getKeyword().equals(RoleEnum.CLIENT.name())) {
            Client clientFromDb = entityManager.find(Client.class, request.getUser().getId());
            if (clientFromDb == null) {
                stateResponse.setSuccess(false);
                return stateResponse;
            }
            if (user.getPhoneNumber() != null)
                if (clientFromDb.getPhoneNumber() != user.getPhoneNumber())
                    clientFromDb.setPhoneNumber(user.getPhoneNumber());
            if (user.getPassword() != null)
                if (clientFromDb.getPassword() != user.getPassword())
                    clientFromDb.setPassword(user.getPassword());
            if (user.getLastName() != null)
                if (user.getLastName() != clientFromDb.getLastName())
                    clientFromDb.setLastName(user.getLastName());
            if (user.getFirstName() != null)
                if (user.getFirstName() != clientFromDb.getFirstName())
                    clientFromDb.setFirstName(user.getFirstName());
            if (user.getEmail() != null)
                if (user.getEmail() != clientFromDb.getEmail())
                    clientFromDb.setEmail(user.getEmail());
            if (user.getAddress() != null)
                if (user.getAddress() != clientFromDb.getAddress())
                    clientFromDb.setAddress(user.getAddress());
            entityManager.merge(clientFromDb);

        }

        if (request.getString().getKeyword().equals(RoleEnum.LIBRARIAN.name())) {
            Librarian librarianFromDb = entityManager.find(Librarian.class, request.getUser().getId());
            if (librarianFromDb == null) {
                stateResponse.setSuccess(false);
                return stateResponse;
            }

            if (user.getPhoneNumber() != null)
                if (librarianFromDb.getPhoneNumber() != user.getPhoneNumber())
                    librarianFromDb.setPhoneNumber(user.getPhoneNumber());
            if (user.getPassword() != null)
                if (librarianFromDb.getPassword() != user.getPassword())
                    librarianFromDb.setPassword(user.getPassword());
            if (user.getLastName() != null)
                if (user.getLastName() != librarianFromDb.getLastName())
                    librarianFromDb.setLastName(user.getLastName());
            if (user.getFirstName() != null)
                if (user.getFirstName() != librarianFromDb.getFirstName())
                    librarianFromDb.setFirstName(user.getFirstName());
            if (user.getEmail() != null)
                if (user.getEmail() != librarianFromDb.getEmail())
                    librarianFromDb.setEmail(user.getEmail());
            if (user.getAddress() != null)
                if (user.getAddress() != librarianFromDb.getAddress())
                    librarianFromDb.setAddress(user.getAddress());
            entityManager.merge(librarianFromDb);
        }
        stateResponse.setSuccess(true);
        return stateResponse;

    }

    @Transactional
    @Override
    public void deleteUser(RequestWithId requestWithId) {
        User user = entityManager.find(User.class, requestWithId.getId());
        entityManager.remove(user);
    }

    @Transactional
    @Override
    public LoginResponse login(LoginRequest request) {
        Query query = entityManager.createQuery("select user.id,user.roleEnum from User user" +
                " where user.email = :email and user.password = :pass", Tuple.class)
                .setParameter("email", request.getEmail())
                .setParameter("pass", request.getPassword());
        Tuple tuple = (Tuple) query.getResultList().stream().findFirst().orElse(null);

        LoginResponse loginResponse = new LoginResponse();
        if (tuple == null) {
            return loginResponse;
        }
        loginResponse.setEmail(request.getEmail());
        loginResponse.setId((Long) tuple.get(0));
        loginResponse.setRole(tuple.get(1).toString());
        loginResponse.setToken(UUID.randomUUID().toString().replace("-", ""));
        return loginResponse;
    }

    @Override
    @Transactional
    public List<Book> getLoanHistory(RequestWithId request) {
        Query query = entityManager.createQuery("select client.bookList from Client client ", Collection.class);
        List<Book> bookList = query.getResultList();
        Client client = entityManager.find(Client.class, request.getId());
        return bookList.stream().
                filter(book -> book.getClientList().contains(client))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StateResponse issueBook(RequestIssueBook issueBook) {
        StateResponse stateResponse = new StateResponse();
        if (issueBook.getAuthor() == null || issueBook.getName() == null || issueBook.getIdUser() == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }
        Client client = entityManager.find(Client.class,issueBook.getIdUser());
        if (client == null) {
            stateResponse.setSuccess(false);
            return stateResponse;
        }

        IssueBookRequest book = new IssueBookRequest();
        book.setAuthor(issueBook.getAuthor());
        book.setName(issueBook.getName());
        book.setClient(client);

        entityManager.persist(book);
        stateResponse.setSuccess(true);
        return stateResponse;
    }

    @Override
    @Transactional
    public void deleteIssue(RequestWithId request) {
        IssueBookRequest issueBookRequest = entityManager.find(IssueBookRequest.class, request.getId());
        entityManager.remove(issueBookRequest);
    }

    @Override
    @Transactional
    public List<IssueBookRequest> getIssueBooks() {
        List<IssueBookRequest> resultList = entityManager.createQuery("select books from IssueBookRequest books", IssueBookRequest.class).getResultList();
        return resultList;
    }

    @Transactional(readOnly = true)
    public List<IssueBookRequest> getIssueHistory(RequestWithId request) {
        Query query = entityManager.createQuery(" select ib from IssueBookRequest  ib where ib.client.id = :id", IssueBookRequest.class);
        query.setParameter("id", request.getId());
        List<IssueBookRequest> books = query.getResultList();
        return books;
    }

    @Override
    @Transactional
    public List<Appointment> getActiveLoans(RequestWithId request) {
        Query query = entityManager.createQuery(" select ap.id, ap.startDate, ap.endDate, ap.book from Appointment ap  where ap.client.id = :id and ap.returned = false ", Tuple.class)
                .setParameter("id", request.getId());
        List<Tuple> queryList = query.getResultList();
        List<Appointment> appointmentsOfAUser = new ArrayList<>();
        for (Tuple tuple : queryList) {
            Appointment app = new Appointment();
            app.setId((Long) tuple.get(0));
            app.setStartDate((Date) tuple.get(1));
            app.setEndDate((Date) tuple.get(2));
            app.setBook((Book) tuple.get(3));
            app.getBook().setClientList(null);
            appointmentsOfAUser.add(app);
        }
        return appointmentsOfAUser;

    }
}
