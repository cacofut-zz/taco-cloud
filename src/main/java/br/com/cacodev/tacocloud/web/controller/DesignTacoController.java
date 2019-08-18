

package br.com.cacodev.tacocloud.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cacodev.tacocloud.model.data.IngredientRepository;
import br.com.cacodev.tacocloud.model.data.JdbcIngredientRepository;
import br.com.cacodev.tacocloud.model.data.JdbcTacoRepository;
import br.com.cacodev.tacocloud.model.data.TacoRepository;
import br.com.cacodev.tacocloud.model.entity.Ingredient;
import br.com.cacodev.tacocloud.model.entity.Ingredient.Type;
import br.com.cacodev.tacocloud.model.entity.Order;
import br.com.cacodev.tacocloud.model.entity.Taco;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController{
    
    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController(
    		IngredientRepository ingredientRepo, 
    		TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo     = designRepo;
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
    	List<Ingredient> ingredients = new ArrayList<>();
    	ingredientRepo.findAll().forEach( i -> ingredients.add(i));
    	
    	Type[] types = Ingredient.Type.values();
    	for(Type type : types) {
    		model.addAttribute(type.toString().toLowerCase(), 
    			filterByType(ingredients, type));
    	}        
        return "design";
    }

    @PostMapping
    public String processDesign(
    	@Valid Taco design, Errors errors, 
        @ModelAttribute Order order){    	
        if(errors.hasErrors()){        	  
            return "design";
        }
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
    		List<Ingredient> ingredients, Type type){
        return ingredients
        	.stream()
            .filter( i -> i.getType().equals(type) )
            .collect(Collectors.toList());
    }
}