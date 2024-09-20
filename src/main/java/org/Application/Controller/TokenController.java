package org.Application.Controller;

import org.Application.Model.Token;
import org.Application.Model.TokenRequest;
import org.Application.Service.RequestService;
import org.Application.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService service;

    @Autowired
    private RequestService requestToken;


    @GetMapping("/{id}")
    public ResponseEntity<?> getTokenByCustomerID(@PathVariable Integer id)
    {
        return service.getTokenByCustomerID(id);
    }

    @PostMapping("")
    public ResponseEntity<?> addToken(@RequestBody Token token)
    {
        return service.addToken(token);
    }
    @GetMapping("")
    public ResponseEntity<?> requestToken(@RequestBody TokenRequest tokenReq)
    {
        return requestToken.requestToken(tokenReq);
    }

}
