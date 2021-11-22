package com.mingul.bikerentalservice.contoller;

import com.mingul.bikerentalservice.model.Bike;
import com.mingul.bikerentalservice.model.EmailId;
import com.mingul.bikerentalservice.model.Office;
import com.mingul.bikerentalservice.model.PhoneNumber;
import com.mingul.bikerentalservice.repository.BikeRepository;
import com.mingul.bikerentalservice.repository.OfficeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/office")
public class OfficeController {
    private final OfficeRepository officeRepository;
    private final BikeRepository bikeRepository;

    public OfficeController(OfficeRepository officeRepository, BikeRepository bikeRepository) {
        this.officeRepository = officeRepository;
        this.bikeRepository = bikeRepository;
    }

    @GetMapping(path = {"/", ""})
    public String getAllOffices(Model model){
        model.addAttribute("offices", officeRepository.findAll());
        return "officeHome";
    }

    @GetMapping(path = "/{location}")
    public @ResponseBody Office getAllBikes(@PathVariable Office location){
        return location;
    }

    @PostMapping(path = "/add")
    public RedirectView createOffice(RedirectAttributes redirectAttributes, @ModelAttribute Office officeInfo, BindingResult errors){
        officeRepository.save(officeInfo);
        String message = "Office " + officeInfo.getAddress() + " has been created";
        RedirectView redirectView = new RedirectView("/office", true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return redirectView;
    }

    @GetMapping(path = "/add")
    public String createOffice(Model model){
        Office o = new Office();
        o.getEmailBook().add(new EmailId());
        o.getPhoneBook().add(new PhoneNumber());
        model.addAttribute("officeInfo", o);
        return "addOffice";
    }


    @PostMapping(path = "/addEmail")
    public String addEmailRow(Office office, Model model, HttpSession session){
        System.out.println("addEmailId");
        office.getEmailBook().add(new EmailId());
        model.addAttribute("officeInfo", office);
        return "addOffice :: emails"; // returning the updated section
    }

    @PostMapping(path = "/removeEmail")
    public String removeEmail(Office office, @RequestParam("removeEmail") Integer emailIdIndex, Model model){
        office.getEmailBook().remove(emailIdIndex.intValue());
        model.addAttribute("officeInfo", office);
        return "addOffice :: emails"; // returning the updated section
    }

    @PostMapping(path = "/addPhone")
    public String addPhoneNumber(Office office, Model model){
        office.getPhoneBook().add(new PhoneNumber());
        model.addAttribute("officeInfo", office);
        return "addOffice :: phoneNumbers"; // returning the updated section
    }

    @PostMapping(path = "/removePhone")
    public String removePhoneNumber(Office office, @RequestParam("removePhone") Integer phoneNumberIndex, Model model){
        office.getPhoneBook().remove(phoneNumberIndex.intValue());
        model.addAttribute("officeInfo", office);
        return "addOffice :: phoneNumbers"; // returning the updated section
    }

}

