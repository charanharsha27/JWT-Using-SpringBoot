package com.jwt.Repository;

import com.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Integer> {

    public User findByUsername(String username);
}
