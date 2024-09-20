package org.Application.Controller;

import org.Application.Model.Customer;
import org.Application.Service.CustomerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("")
    public ResponseEntity<Customer> add(@RequestBody Customer c) {
        return ResponseEntity.ok(service.addCustomer(c));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerByID(@PathVariable Integer id) {
        return service.getCustomerByID(id);

    }

    @GetMapping("")
    public ResponseEntity<?> getAllCustomer() {
        List<Customer> fetchedCustomer = service.getCustomer();
        if(fetchedCustomer !=null)
            return ResponseEntity.ok(fetchedCustomer);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer Not found");
    }

    @PutMapping("")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer c) {
        return service.updateCustomer(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id)
    {
        return service.deleteCustomerByID(id);
    }

}
