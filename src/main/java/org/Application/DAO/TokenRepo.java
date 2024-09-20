package org.Application.DAO;

import org.Application.Model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {

        List<Token> findByCustomerId(Integer customerId);
}
