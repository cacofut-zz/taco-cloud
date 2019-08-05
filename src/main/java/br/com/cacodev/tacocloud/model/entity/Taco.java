

package br.com.cacodev.tacocloud.model.entity;

import java.util.List;

import lombok.Data;

@Data
public class Taco{

    private String name;
    private List<String> ingredients;

}