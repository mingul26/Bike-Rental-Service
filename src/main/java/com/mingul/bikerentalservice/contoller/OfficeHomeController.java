package com.mingul.bikerentalservice.contoller;

import com.mingul.bikerentalservice.model.Bike;
import com.mingul.bikerentalservice.model.Office;
import com.mingul.bikerentalservice.repository.BikeRepository;
import com.mingul.bikerentalservice.repository.OfficeRepository;
import com.mingul.bikerentalservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(path = "/home")
public class OfficeHomeController {
    private final BikeRepository bikeRepository;
    private final ReservationRepository reservationRepository;
    private final OfficeRepository officeRepository;

    public OfficeHomeController(BikeRepository bikeRepository,
                                ReservationRepository reservationRepository,
                                OfficeRepository officeRepository) {
        this.bikeRepository = bikeRepository;
        this.reservationRepository = reservationRepository;
        this.officeRepository = officeRepository;
    }

    @GetMapping(path = {"/", ""})
    public String home(Model model,
                       HttpSession session) {
        Office office = officeRepository.findById(((Office) session.getAttribute("office")).getId()).get();
        model.addAttribute("office", office);
        model.addAttribute("bikes", office.getBikes());
        model.addAttribute("activeRes", reservationRepository.findReservationByReturnLocationAndStatusEquals(office, "travelling"));
        model.addAttribute("completedDrops", reservationRepository.findReservationByReturnLocationAndStatusEquals(office, "returned"));
        model.addAttribute("completedPickups", reservationRepository.findReservationByPickupLocationAndStatusEquals(office, "returned"));
        return "officeDashboard";
    }

    @GetMapping(path = "/addBike")
    public String addBikePage(Model model,
                              HttpSession session){
        Office o = (Office) session.getAttribute("office");
        model.addAttribute("location", o);
        Bike bikeInfo = new Bike();
        bikeInfo.setSourceLocation(o);
        model.addAttribute("bikeInfo", bikeInfo);
        return "addBike";
    }

    @PostMapping(path = "/addBike")
    public RedirectView saveBike(RedirectAttributes redirectAttributes,
                                 @ModelAttribute Bike bikeInfo){
        bikeInfo.setAvailability(true);
        bikeRepository.save(bikeInfo);
        String msg = "Bike has been added to " + bikeInfo.getSourceLocation().getAddress();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/office");
        redirectAttributes.addFlashAttribute("success", msg);
        return redirectView;
    }

    @GetMapping(path = "/updateBike/{bikeValue}")
    public Object updateBikePage(RedirectAttributes redirectAttributes,
                                 Model model,
                                 HttpSession session,
                                 @PathVariable String bikeValue){
        Office o = (Office) session.getAttribute("office");
        RedirectView redirectView = new RedirectView();
        Integer bikeId;
        Bike bike = null;
        try {
            bikeId = Integer.parseInt(bikeValue);
            Optional<Bike> bikeTemp = bikeRepository.findById(bikeId);
            if(bikeTemp.isPresent()){
                bike = bikeTemp.get();
            }
            if(bike == null || !Objects.equals(bike.getSourceLocation().getId(), o.getId())){
                redirectAttributes.addFlashAttribute("error", "You are not allowed to update this bike");
                redirectView.setUrl("/home");
            }
        } catch (NumberFormatException e){
            String msg = bikeValue + " is not a bike id";
            redirectAttributes.addFlashAttribute("error", msg);
            redirectView.setUrl("/home");
        }

        if(redirectView.getUrl() != null){
            return redirectView;
        }

        assert bike != null;

        model.addAttribute("location", o);
        model.addAttribute("bikeInfo", bike);
        model.addAttribute("update", "update");

        session.setAttribute("bikeId", bike.getId());

        return "addBike";
    }

    @PostMapping(path = "/updateBike")
    public RedirectView saveUpdatedBike(RedirectAttributes redirectAttributes,
                                        HttpSession session,
                                        @ModelAttribute Bike bikeInfo){
        Integer bikeId = (Integer) session.getAttribute("bikeId");
        session.removeAttribute("bikeId");

        Bike b = bikeRepository.findById(bikeId).get();
        b.setBrand(bikeInfo.getBrand());
        b.setModel(bikeInfo.getModel());
        b.setColor(bikeInfo.getColor());
        bikeRepository.save(b);
        String msg = "Bike has been updated";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/home");
        redirectAttributes.addFlashAttribute("success", msg);
        return redirectView;
    }

}
