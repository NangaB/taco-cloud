package com.example.demo.web;


import com.example.demo.Ingredient;
import com.example.demo.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.Ingredient.Type;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @GetMapping()
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "pszenna", Type.WRAP),
                new Ingredient("COTO", "kukurydziana", Type.WRAP),
                new Ingredient("GRBF", "mielona wołowina", Type.PROTEIN),
                new Ingredient("CARN", "kawałki mięsa", Type.PROTEIN),
                new Ingredient("LETC", "sałata", Type.VEGGIES),
                new Ingredient("CHED", "cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterey Jack", Type.CHEESE),
                new Ingredient("SLSA", "pikantny sos pomidorowy", Type.SAUCE),
                new Ingredient("SRCR", "śmietana", Type.SAUCE)
        );

        Type [] types = Ingredient.Type.values(); //tu są wszyskie typy czyli: WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE

        for(Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";

    }

    @PostMapping
    public String processDesgin(@Valid Taco taco, Errors errors){

        if(errors.hasErrors()){
            return "design";
        }

        log.info("Przetwarzanie projektu taco: " + taco);
        return "redirect:/orders/current";
    }
    //jak nie ma określongego w formularzu action= to po wpisaniu danych jest to wciąż przekazywane do metody z get
    // a jak tworzymy @PostMapping to dane są przekazywane tutaj


    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }


}
