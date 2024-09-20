package org.Application.Service;

import org.Application.Helper.HTTPHelper;

import org.Application.Model.Token;
import org.Application.Model.TokenRequest;
import org.Application.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
public class RequestService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RequestService requestService;

    public ResponseEntity<UUID> getUniqueRequestID()
    {
        UUID uniqueRequestID = UUID.randomUUID();
        return ResponseEntity.ok(uniqueRequestID);
    }

    public ResponseEntity<?> requestToken(TokenRequest tokenRequest)
    {
        LocalDateTime checkInDatetime= tokenRequest.getCheckInDate();
        LocalDateTime checkOutDateTime=tokenRequest.getCheckOutDate();

        //1. Date Validation
        if(checkInDatetime.isAfter(checkOutDateTime) || checkInDatetime.isBefore(LocalDateTime.now()))
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide valid check in and check out date");

        long numberOfDays = ChronoUnit.DAYS.between(checkInDatetime,checkOutDateTime);

        //2. Get the tokens list related to the customer
        ResponseEntity<?> tokenDetails = tokenService.getTokenByCustomerID(tokenRequest.getCustomerId());
        List<Token> tokenList;
        if (tokenDetails.getStatusCode().equals(HttpStatus.OK))
            tokenList = (List<Token>) tokenDetails.getBody();
        else
            tokenList = Collections.emptyList();

        //3. Filter the token list with tokens which are available after the checkin and before check out date and extracting only the required number of token
        //based on the number of days of stay

        List<Token> availableToken = !tokenList.isEmpty()?tokenList.stream().filter(token -> token.getExpiryDate().isAfter(checkOutDateTime)).limit(numberOfDays).toList(): null;

        //4. If token is available update the token table status and registrationid
        if(!availableToken.isEmpty()) {
            HttpEntity<String> httpReq = HTTPHelper.getHTTPRequest();
            for(Token t: availableToken) {
                ResponseEntity<?> update= tokenService.updateStatus(t, TokenStatus.Requested, requestService.getUniqueRequestID().getBody());
                if(update.getStatusCode().equals(HttpStatus.NOT_FOUND)) return update;
            }
            return ResponseEntity.status(HttpStatus.OK).body(tokenList);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tokens available for the requested date");
    }
}
