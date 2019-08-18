package br.com.cacodev.tacocloud.model.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.cacodev.tacocloud.model.data.IngredientRepository;
import br.com.cacodev.tacocloud.model.entity.Ingredient;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

	private IngredientRepository ingredientRepo;
		
	@Autowired
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {	
		this.ingredientRepo = ingredientRepo;
	}

	@Override
	public Ingredient convert(String id) {		
		return ingredientRepo.findById(id);
	}

	
}
