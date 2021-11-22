package com.mingul.bikerentalservice.contoller;

import com.mingul.bikerentalservice.model.Customer;
import com.mingul.bikerentalservice.model.EmailId;
import com.mingul.bikerentalservice.model.Office;
import com.mingul.bikerentalservice.repository.CustomerRepository;
import com.mingul.bikerentalservice.repository.EmailIdRepository;
import com.mingul.bikerentalservice.repository.OfficeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final CustomerRepository customerRepository;
    private final OfficeRepository officeRepository;
    private final EmailIdRepository emailRepo;

    public HomeController(CustomerRepository customerRepository,
                          OfficeRepository officeRepository,
                          EmailIdRepository emailRepo) {
        this.customerRepository = customerRepository;
        this.officeRepository = officeRepository;
        this.emailRepo = emailRepo;
    }

    @RequestMapping(path = {"", "/"})
    public String index() {
        return "index";
    }

    @RequestMapping(path = {"/login"})
    public String login() {
        return "loginPage";
    }

    @PostMapping(path = {"/login"})
    public RedirectView login(RedirectAttributes redirectAttributes,
                              HttpSession session,
                              @RequestParam(name = "username") String username,
                              @RequestParam(name = "password") String password) {
        Iterable<Customer> customerList = customerRepository.findCustomerByEmailIdAndPassword(username, password);
        RedirectView redirectView = new RedirectView();
        if (customerList.iterator().hasNext()) {
            Customer customer = customerList.iterator().next();
            session.setAttribute("userType", "user");
            session.setAttribute("user", customer);
            System.out.println(customer.getName());
            redirectView.setUrl("/dashboard");
            redirectAttributes.addFlashAttribute("success", "Welcome " + customer.getName());
        } else {
            redirectView.setUrl("/login");
            redirectAttributes.addFlashAttribute("error", "Username/Password is incorrect");
        }
        return redirectView;
    }

    @RequestMapping(path = {"/register"})
    public String register(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customerInfo", customer);
        return "registerPage";
    }

    @PostMapping(path = {"/register"})
    public RedirectView register(RedirectAttributes redirectAttributes,
                                 @ModelAttribute Customer customer,
                                 HttpSession session) {
        Customer checkCustomer = customerRepository.findCustomerByEmailId(customer.getEmailId());
        RedirectView redirectView = new RedirectView();
        if(checkCustomer != null) {
            redirectAttributes.addFlashAttribute("error", "A user with Email " + customer.getEmailId() +" already exists");
            redirectView.setUrl("/register");
            return redirectView;
        }
        customerRepository.save(customer);
        session.setAttribute("userType", "user");
        session.setAttribute("user", customer);
        System.out.println(customer.getName());
        redirectView.setUrl("/dashboard");
        String msg = "Successfully created user - " + customer.getName();
        redirectAttributes.addFlashAttribute("success", msg);
        return redirectView;
    }

    @RequestMapping(path = {"/loginOffice"})
    public String loginOffice() {
        return "loginOfficePage";
    }

    @PostMapping(path = {"/loginOffice"})
    public RedirectView loginOffice(RedirectAttributes redirectAttributes,
                                    HttpSession session,
                                    @RequestParam(name = "username") String username,
                                    @RequestParam(name = "password") String password) {
        EmailId emailId = emailRepo.findEmailIdByValue(username);
        RedirectView redirectView = new RedirectView();
        if (emailId == null) {
            redirectView.setUrl("/loginOffice");
            redirectAttributes.addFlashAttribute("error", "No Office exists with " + username + "as email id");
            return redirectView;
        }
        // Email exists
        Iterable<Office> officeList = officeRepository.findOfficeByEmailBookContainingAndPassword(emailId, password);
        if (officeList.iterator().hasNext()) {
            Office office = officeList.iterator().next();
            session.setAttribute("userType", "office");
            session.setAttribute("office", office);
            System.out.println(office.getManagerName());
            redirectView.setUrl("/home");
            redirectAttributes.addFlashAttribute("success", "Welcome " + office.getManagerName());
            return redirectView;
        }
        // Password is wrong
        redirectView.setUrl("/loginOffice");
        redirectAttributes.addFlashAttribute("error", "Username/Password is incorrect");
        return redirectView;
    }

    @RequestMapping(path = {"/logout"})
    public RedirectView logout(RedirectAttributes redirectAttributes,
                               HttpSession session) {
        session.removeAttribute("userType");
        session.removeAttribute("user");
        session.invalidate();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        redirectAttributes.addFlashAttribute("success", "Logged out successfully");
        return redirectView;
    }

    @RequestMapping(path = {"/about"})
    public String about() {
        return "about";
    }


}