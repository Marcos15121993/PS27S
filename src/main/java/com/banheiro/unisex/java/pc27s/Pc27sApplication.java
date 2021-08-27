package com.banheiro.unisex.java.pc27s;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import banheiro.Banheiro;
import pessoa.Pessoa;
import pessoa.Sexo;

public class Pc27sApplication {

    public static void main(String[] args) {
    	
        Banheiro banheiro = Banheiro.getInstance();
        List<Pessoa> pessoas = new ArrayList<>();
        
        for (int i = 0; i < ((new Random()).nextInt(10) + 15); i++) {
            if (new Random().nextInt(2) == 0) {
                pessoas.add(new Pessoa(("João homem - " + i), Sexo.HOMEM, banheiro));
            } else {
                pessoas.add(new Pessoa(("Maria mulher - " + i), Sexo.MULHER, banheiro));
            }
        }
        
        pessoas.stream().map((Person) -> new Thread(Person)).forEach((t) -> {
            t.start();
        });
        
        pessoas.stream().map((Person) -> new Thread(Person)).forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                
            }
        });
    }

}
