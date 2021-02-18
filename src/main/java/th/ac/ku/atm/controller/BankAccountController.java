package th.ac.ku.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Customer;
import th.ac.ku.atm.service.BankAccountService;

import javax.swing.*;

@Controller
@RequestMapping("/bankaccount")
public class BankAccountController {

    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public String getBankAccountPage(Model model) {
        model.addAttribute("bankAccountList", bankAccountService.getBankAccountList());

        return "bankaccount";
    }

    @PostMapping
    public String createBankAccount(@ModelAttribute BankAccount bankAccount, Model model) {
        // 1. เอา id ไปเช็คกับข้อมูล customer ที่มีอยู่ ว่าตรงกันบ้างไหม
        Customer matchingCustomer = bankAccountService.checkCustomer(bankAccount.getCustomerId());

        // 2. ถ้าตรง สามารถเปิด bank account ได้
        if (matchingCustomer != null) {
            bankAccountService.createBankAccount(bankAccount);
            model.addAttribute("bankAccountList", bankAccountService.getBankAccountList());
        } else {
            // 3. ถ้าไม่ตรง แจ้งว่าไม่มีข้อมูล customer นี้
            model.addAttribute("alertText", "Can't Find Customer ID: " + bankAccount.getCustomerId());
        }
        return "bankaccount";
    }
}
