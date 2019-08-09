/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cacodev.tacocloud.model.data;

import br.com.cacodev.tacocloud.model.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 *
 * @author cristianoca
 */
public class JdbcOrderRepository implements OrderRepository{

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;
    
    public JdbcOrderRepository(JdbcTemplate jdbc){
        this.orderInserter = new SimpleJdbcInsert(jdbc)
            .withSchemaName("Taco_Order")
            .usingGeneratedKeyColumns("id");
        
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
            .withTableName("Taco_Order_Tacos");
        
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public Order save(Order order) {
        return null;
    }
    
    
    
}
