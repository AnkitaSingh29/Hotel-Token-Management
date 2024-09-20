package org.Application.Service;

import org.Application.DAO.TokenRepo;

import org.Application.Model.Token;

import org.Application.TokenStatus;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class TokenService {

    @Autowired
    private TokenRepo repo;


    public ResponseEntity<?> getTokenByCustomerID(Integer id)
    {
        List<Token> tokens = repo.findByCustomerId(id)
                                .stream()
                                .filter(token -> token.getStatus().equals(TokenStatus.Available))
                                .filter(token -> token.getExpiryDate().isAfter(LocalDateTime.now()))
                                .toList();
        System.out.println(tokens);
        if(tokens.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer has no valid tokens");
        else
            return ResponseEntity.ok(tokens);
    }


    public ResponseEntity<Token> addToken(Token token)
    {
        System.out.println();
        return ResponseEntity.ok(repo.save(token));
    }


    public ResponseEntity<String> updateStatus(Token token, TokenStatus status,UUID registrationID)
    {
        Optional<Token> findToken = repo.findById(token.getTokenID());
        if(findToken.isEmpty() || findToken.get().getStatus()!=TokenStatus.Available)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not available");
        else
        {
            token.setStatus(status);
            token.setRegistrationID(String.valueOf(registrationID));
            repo.save(token);
            return ResponseEntity.status(HttpStatus.OK).body("Token status updated");
        }
    }



}
