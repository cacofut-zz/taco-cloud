/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cacodev.tacocloud.model.data;

import br.com.cacodev.tacocloud.model.entity.Ingredient;
import br.com.cacodev.tacocloud.model.entity.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cristianoca
 */
@Repository
public class JdbcTacoRepository implements TacoRepository{
    
    private JdbcTemplate jdbc;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }   
    
    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return null;
    }
    
    /**
     * 
     * @param taco
     * @return 
     */
    private long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
        	"insert into Taco (name, createdAt) values (?, ?)", 
        	Types.VARCHAR, Types.TIMESTAMP
        ).newPreparedStatementCreator(
        	Arrays.asList(
        		taco.getName(),
        		new Timestamp(taco.getCreatedAt().getTime())));
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);        
        return keyHolder.getKey().longValue();
    }
    
    /**
     * 
     * @param ingredient
     * @param tacoId 
     */
    private void saveIngredientToTaco(Ingredient ingredient, long tacoId){    	       
        jdbc.update("insert into Taco_ingredients (taco, ingredient) values(?, ?)",
        	tacoId, ingredient.getId());
    }
}