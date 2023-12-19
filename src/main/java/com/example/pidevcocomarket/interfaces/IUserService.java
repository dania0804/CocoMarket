package com.example.pidevcocomarket.interfaces;


import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.utils.PagingResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface IUserService {
    List<User> retrieveAllUsers();
    User retrieveUser (Integer id);
    User updateUser (User user);
    User addUser (User user);
    void removeUser (Integer id);
    PagingResponse get3(Specification<User> spec, HttpHeaders headers, Sort sort);
    public void updatePassword(Integer userId, String newPassword);

}
