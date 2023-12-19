package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer>  {
}
