/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cacodev.tacocloud.model.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import br.com.cacodev.tacocloud.model.entity.Ingredient;
import br.com.cacodev.tacocloud.model.entity.Taco;

/**
 *
 * @author cristianoca
 */
@Repository
public class JdbcTacoRepository implements TacoRepository{
   
   
    private SimpleJdbcInsert tacoInserter;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.tacoInserter = new SimpleJdbcInsert(jdbc)
    		.withTableName("Taco")
    		.usingGeneratedKeyColumns("id");
    }   
    
    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }
    
    /**
     * 
     * @param taco
     * @return 
     */
    private long saveTacoInfo(Taco taco){
    	Map<String, Object> values = new HashMap<>();
        taco.setCreatedAt(new Date());
        values.put("name", taco.getName());
        values.put("createdAt", taco.getCreatedAt());

        long orderId = 
    		tacoInserter
                .executeAndReturnKey(values)
                .longValue();
        return orderId;
    }
    
    /**
     * 
     * @param ingredient
     * @param tacoId 
     */
    private void saveIngredientToTaco(Ingredient ingredient, long tacoId){
    	Map<String, Object> values = new HashMap<>();
        values.put("taco", tacoId);
        values.put("ingredient", ingredient.getId());
        tacoInserter.execute(values);
    }
}
