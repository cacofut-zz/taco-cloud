/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cacodev.tacocloud.model.data;

import br.com.cacodev.tacocloud.model.entity.Order;

/**
 *
 * @author cristianoca
 */
public interface OrderRepository {

    Order save(Order order);
}
