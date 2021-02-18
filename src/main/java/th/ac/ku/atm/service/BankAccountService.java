package th.ac.ku.atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService {
    private List<BankAccount> bankAccountList;

    @Autowired
    private CustomerService customerService;

    @PostConstruct
    public void postConstruct() {
        this.bankAccountList = new ArrayList<>();
    }

    public void createBankAccount(BankAccount bankAccount) {
        bankAccountList.add(bankAccount);
    }

    public List<BankAccount> getBankAccountList() {
        return new ArrayList<>(this.bankAccountList);
    }

    public Customer findCustomer(int id) {
        for (Customer customer : customerService.getCustomers()) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    public Customer checkCustomer(int customerId) {
        // 1. หา customer ที่มี id ตรงกับ parameter
        Customer storedCustomer = findCustomer(customerId);
        // 2. ถ้ามี id ตรง ให้คืนค่า customer
        if (storedCustomer != null) {
            return storedCustomer;
        }
        // 3. ถ้าไม่ตรง ต้องคืนค่า null
        return null;
    }
}
