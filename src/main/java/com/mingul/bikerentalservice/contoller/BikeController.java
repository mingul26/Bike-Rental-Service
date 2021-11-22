package com.mingul.bikerentalservice.contoller;

import com.mingul.bikerentalservice.model.Office;
import com.mingul.bikerentalservice.repository.BikeRepository;
import com.mingul.bikerentalservice.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/office/{location}/bike")
public class BikeController {
    private final BikeRepository bikeRepository;

    public BikeController(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @GetMapping("/list")
    public String getAllBikes(@PathVariable Office location, Model model) {
        model.addAttribute("office", location);
        model.addAttribute("bikes", bikeRepository.findBikesBySourceLocation(location));
        model.addAttribute("incomingBikes", bikeRepository.findBikesByTargetLocation(location));
        return "bikeHome";
    }
}


