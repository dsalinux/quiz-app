/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.professordanilo.quizapp.constant;

/**
 *
 * @author danilo
 */
public enum PlayerType {
    
    MALE ("Homem"),
    FEMALE("Mulher"),
    GROUP("Grupo");
    
    private String description;
    
    PlayerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
   
}
