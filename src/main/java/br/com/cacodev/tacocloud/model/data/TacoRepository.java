/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cacodev.tacocloud.model.data;

import br.com.cacodev.tacocloud.model.entity.Taco;

/**
 *
 * @author cristianoca
 */
public interface TacoRepository {

    Taco save(Taco design);
    
}
