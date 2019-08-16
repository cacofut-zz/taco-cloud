
package br.com.cacodev.tacocloud.model.data;

import br.com.cacodev.tacocloud.model.entity.Ingredient;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cristianoca
 */
@Repository
public class JdbcIngredientRepository implements IngredientRepository{
    
    private JdbcTemplate jdbc;
    
    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("select id, name, type from Ingredient", 
            this::mapRowToIngredient);        
    }

    /**
     * 
     * @param id
     * @return 
     */
    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject("select id, name, type from Ingredient where id=?", 
            new RowMapper<Ingredient>(){
                public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException{
                    return new Ingredient(
                        rs.getString("id"),
                        rs.getString("name"), 
                        Ingredient.Type.valueOf(rs.getString("type")));
                }                           
            }, id);
    }

    /**
     * 
     * @param ingredient
     * @return 
     */
    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("insert into Ingredient(id, name, type)values(?, ?, ?)", 
            ingredient.getId(), 
            ingredient.getName(), 
            ingredient.getType().toString());
        return ingredient;
    }
    
    /**
     * 
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException 
     */
    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) 
        throws SQLException{
        return new Ingredient(
            rs.getString("id"), 
            rs.getString("name"), 
            Ingredient.Type.valueOf(rs.getString("type")));
    }   
    
    
}
