package com.mingul.bikerentalservice.contoller;

import com.mingul.bikerentalservice.model.Customer;
import com.mingul.bikerentalservice.model.Reservation;
import com.mingul.bikerentalservice.repository.CustomerRepository;
import com.mingul.bikerentalservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class CustomerController {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(path = {"/dashboard"})
    public String dashboard(HttpSession session, Model model){
        if(session.getAttribute("user") == null) {
            model.addAttribute("error", "You need to login first!");
            return "loginPage";
        }
        Customer customer = (Customer) session.getAttribute("user");
        Iterable<Reservation> activeReservations = reservationRepository.findReservationByCustomerAndStatusEquals(customer, "travelling");
        Iterable<Reservation> pastReservations = reservationRepository.findReservationByCustomerAndStatusEquals(customer, "returned");
        model.addAttribute("user", customer);
        model.addAttribute("activeRes", activeReservations);
        model.addAttribute("pastRes", pastReservations);
        return "dashboard";
    }

    @RequestMapping(path = {"/dashboard/update"})
    public String updateDetails(HttpSession session, Model model){
        Customer customer = (Customer) session.getAttribute("user");
        customer = customerRepository.findById(customer.getId()).get();
        model.addAttribute("customerInfo", customer);
        model.addAttribute("update", "update");
        return "registerPage";
    }

    @RequestMapping(path = {"/dashboard/update"}, method = RequestMethod.POST)
    public RedirectView saveUpdatedDetails(HttpSession session, Model model, RedirectAttributes redirectAttributes, @ModelAttribute Customer customerDetails){
        Customer customer = (Customer) session.getAttribute("user");
        customer = customerRepository.findById(customer.getId()).get();
        customer.setEmailId(customerDetails.getEmailId());
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());
        customer.setPassword(customerDetails.getPassword());
        customerRepository.save(customer);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/dashboard");
        session.setAttribute("user", customer);
        redirectAttributes.addFlashAttribute("success", "Details updated successfully!");
        return redirectView;
    }
}
