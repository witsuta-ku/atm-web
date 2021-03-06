package th.ac.ku.atm.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.atm.data.CustomerRepository;
import th.ac.ku.atm.model.Customer;
import th.ac.ku.atm.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    private CustomerRepository repository;

    public CustomerRestController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Customer> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Customer getOne(@PathVariable int id) {
        return repository.findById(id).get();
    }


    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        customer.setPin(hash(customer.getPin()));
        Customer record = repository.save(customer);
        return record;
    }

    @DeleteMapping("/{id}")
    public Customer delete(@PathVariable int id) {
        Customer record = repository.findById(id).get();
        repository.deleteById(id);
        return record;
    }

    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }
}



