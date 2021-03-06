package ru.itis.flaremarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.flaremarket.models.enums.sellitem.SellType;
import ru.itis.flaremarket.security.details.UserDetailsImpl;
import ru.itis.flaremarket.service.SellService;
import ru.itis.flaremarket.service.UserService;

@Controller
public class RecommendationController {

    @Autowired
    private UserService userService;

    @Autowired
    private SellService sellService;

    @GetMapping("/recommendation")
    public String recommendation(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model, @RequestParam(required = false, defaultValue = "24") String filterHours){
        model.addAttribute("user", userService.getUserByEmail(userDetails.getUsername()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("deal", sellService.findAllByOrderByChaneeAsc());

        if(filterHours.equals("24"))
            model.addAttribute("market", sellService.findAllBySellTypeOf24H(SellType.PRIMARY));
        else if(filterHours.equals("12"))
            model.addAttribute("market", sellService.findAllBySellTypeOf12H(SellType.PRIMARY));
        else
            model.addAttribute("market", sellService.findAllBySellTypeOf8H(SellType.PRIMARY));

        return "recommendation";
    }
}
