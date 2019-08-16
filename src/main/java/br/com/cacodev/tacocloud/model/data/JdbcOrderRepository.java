/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cacodev.tacocloud.model.data;

import br.com.cacodev.tacocloud.model.entity.Order;
import br.com.cacodev.tacocloud.model.entity.Taco;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.Map;
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
            .withTableName("Taco_Order")
            .usingGeneratedKeyColumns("id");
        
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
            .withTableName("Taco_Order_Tacos");
        
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        
        return null;
    }
    
    /**
     * 
     * @param order
     * @return 
     */
    private long saveOrderDetails(Order order){
        @SuppressWarnings("unchecked")
        Map<String, Object> values = 
            objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());
        
        long orderId = 
            orderInserter
                .executeAndReturnKey(values)
                .longValue();
        return orderId;
        
    }
    
    private void saveTacoToOrder(Taco taco, long orderId){
        
    }
    
    
}
