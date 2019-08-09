

package br.com.cacodev.tacocloud.web.controller;

import br.com.cacodev.tacocloud.model.data.JdbcIngredientRepository;
import br.com.cacodev.tacocloud.model.data.JdbcTacoRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.cacodev.tacocloud.model.entity.Ingredient;
import br.com.cacodev.tacocloud.model.entity.Taco;
import br.com.cacodev.tacocloud.model.entity.Ingredient.Type;
import br.com.cacodev.tacocloud.model.entity.Order;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController{
    
    private final JdbcIngredientRepository ingredientRepo;
    private final JdbcTacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(JdbcIngredientRepository ingredientRepo, 
        JdbcTacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }
        
    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }
    
    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }
    
    @GetMapping
    public String showDesignForm(Model model){
        
//        List<Ingredient> ingredients = Arrays.asList(
//            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//            new Ingredient("CHED", "Cheddar", Type.CHEESE),
//            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//            new Ingredient("SLSA", "Salsa", Type.SAUCE),
//            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//          );
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach( i -> ingredients.add(i) );

        Type[] types = Ingredient.Type.values();
        for(Type type : types){
            model.addAttribute(type.toString().toLowerCase(), 
              filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, 
        @ModelAttribute Order order){
        if(errors.hasErrors()){
            return "design";
        }
        Taco saved = tacoRepo.save(design);
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
        return ingredients.stream()
            .filter( i -> i.getType().equals(type) )
            .collect(Collectors.toList());
    }
}