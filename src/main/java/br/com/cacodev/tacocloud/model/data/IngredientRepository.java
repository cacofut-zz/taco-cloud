/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cacodev.tacocloud.model.data;

import br.com.cacodev.tacocloud.model.entity.Ingredient;

/**
 *
 * @author cristianoca
 */
public interface IngredientRepository {
    
    Iterable<Ingredient> findAll();
    
    Ingredient findOne(String id);
    
    Ingredient save(Ingredient ingredient);
}
