package org.Application.Service;


import org.Application.DAO.CustomerRepo;
import org.Application.Model.Customer;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo repo;

    public Customer addCustomer(Customer customer)
    {
        return repo.save(customer);
    }

    public ResponseEntity<?> getCustomerByID(Integer id)
    {
        Customer findCustomer = repo.findById(id).orElse(null);
        if(findCustomer ==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found. Please provide valid customerID");
        else
        {
            return ResponseEntity.ok(findCustomer);
        }
    }
    public List<Customer> getCustomer()
    {
        return repo.findAll();
    }

    public ResponseEntity<?> updateCustomer(Customer customer)
    {
        Customer findCustomer = repo.findById(customer.getId()).orElse(null);
        if(findCustomer ==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found. Please provide valid customerID");
        else
        {
           return ResponseEntity.ok(repo.save(customer));
        }
    }


    public ResponseEntity<?> deleteCustomerByID(Integer id)
    {
        Customer findCustomer = repo.findById(id).orElse(null);
        if(findCustomer ==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found. Please provide valid customerID");
        else
        {
            repo.deleteById(id);
            return ResponseEntity.ok("Customer successfully deleted");
        }
    }


}
