package com.mingul.bikerentalservice.contoller;

import com.mingul.bikerentalservice.model.Bike;
import com.mingul.bikerentalservice.model.Customer;
import com.mingul.bikerentalservice.model.Office;
import com.mingul.bikerentalservice.model.Reservation;
import com.mingul.bikerentalservice.repository.BikeRepository;
import com.mingul.bikerentalservice.repository.CustomerRepository;
import com.mingul.bikerentalservice.repository.OfficeRepository;
import com.mingul.bikerentalservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class ReservationController {

    private final OfficeRepository officeRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final BikeRepository bikeRepository;

    public ReservationController(OfficeRepository officeRepository,
                                 CustomerRepository customerRepository,
                                 ReservationRepository reservationRepository,
                                 BikeRepository bikeRepository) {
        this.officeRepository = officeRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.bikeRepository = bikeRepository;
    }

    @RequestMapping(path = "/reserve", params = {"reserve"})
    public RedirectView reservation(RedirectAttributes redirectAttributes, @RequestParam(value="id") Bike bike, @RequestParam(value="officeId") Office office, HttpSession session) {
        RedirectView redirectView = new RedirectView();
        Reservation r = new Reservation();
        r.setBike(bike);
        r.setPickupLocation(office);
        if (session.getAttribute("user") == null) {
            String msg = "You must be logged in to make a reservation";
            String url = "/login";
            redirectAttributes.addFlashAttribute("error", msg);
            redirectView.setUrl(url);
            return redirectView;
        }
        redirectAttributes.addFlashAttribute("reservationInfo", r);
        redirectAttributes.addFlashAttribute("officeList", officeRepository.findAll());
        redirectView.setUrl("/reservation");
        return redirectView;
    }

    @RequestMapping(path = "/reserve", params = {"return"})
    public RedirectView returnBike(RedirectAttributes redirectAttributes, @RequestParam(value="id") Bike bike, @RequestParam(value="officeId") Office office) {
        Reservation r = reservationRepository.findReservationByBikeOrderByIdDesc(bike).iterator().next();

        bike.setAvailability(true);
        bike.setSourceLocation(r.getReturnLocation());
        bike.setTargetLocation(null);

        r.setStatus("returned");
        bikeRepository.save(bike);
        reservationRepository.save(r);

        String msg = "Successfully returned to " + r.getReturnLocation().getAddress();
        String url = "/home";
        RedirectView redirectView = new RedirectView(url, true);
        redirectAttributes.addFlashAttribute("success", msg);
        return redirectView;
    }

    @RequestMapping(path = "/reservation")
    public String reservation(@ModelAttribute Reservation reservationInfo) {
        return "reservation";
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    public RedirectView saveReservation(RedirectAttributes redirectAttributes, @ModelAttribute Reservation reservationInfo, HttpSession session) {
        RedirectView redirectView = new RedirectView();
        reservationInfo.setAmount(50.0F);
        reservationInfo.setStatus("travelling");
        Customer customer = (Customer) session.getAttribute("user");
        customer = customerRepository.findById(customer.getId()).get();
        reservationInfo.setCustomer(customer);
        Bike b = reservationInfo.getBike();
        reservationRepository.save(reservationInfo);
        b.setAvailability(false);
        b.setTargetLocation(reservationInfo.getReturnLocation());
        bikeRepository.save(b);
        String message = "Reservation for the bike " +
                reservationInfo.getBike().getBrand() + ", " +
                reservationInfo.getBike().getModel() + ", " +
                reservationInfo.getBike().getColor() + ", from the location " +
                reservationInfo.getPickupLocation().getAddress() + " to " +
                reservationInfo.getReturnLocation().getAddress() + " has been confirmed.";
        String url = "/dashboard";
        redirectView.setUrl(url);
        redirectAttributes.addFlashAttribute("success", message);
        return redirectView;
    }

    @RequestMapping(path = "/reserve/success")
    public String success() {
        return "reservation";
    }




}
