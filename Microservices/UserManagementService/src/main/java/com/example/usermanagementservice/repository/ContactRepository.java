package com.example.usermanagementservice.repository;

import com.example.usermanagementservice.domain.Contact;
import com.example.usermanagementservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByUserAndContact(User user, User contact);
    void deleteByUserAndContact(User user, User contact);
}
