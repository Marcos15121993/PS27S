
package banheiro;

import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import pessoa.Pessoa;
import pessoa.Sexo;

import java.util.concurrent.Semaphore;


public class Banheiro {

    private static final int CAPACIDADE = 5;
    private static Banheiro instance = new Banheiro(CAPACIDADE);
    
    private Semaphore semaforo;
    private Semaphore mutex_entrada;
    private Semaphore mutex_saida;



    private Sexo sexo_atual;
    private final int capacidade;
    private LinkedHashSet<Pessoa> pessoas;
    
    public Banheiro(int capacidade) {
    	
        this.capacidade = capacidade;
        this.sexo_atual = Sexo.NENHUM;
        this.pessoas = new LinkedHashSet<>();
        this.semaforo = new Semaphore(this.capacidade, true);
        this.mutex_saida = new Semaphore(1, true);
        this.mutex_entrada = new Semaphore(1, true);
    }


    public static Banheiro getInstance() {
        return instance;
    }

    public void addUser(Pessoa person) {
        try {
            this.semaforo.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Banheiro.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        if (this.isEmpty()) {
            this.sexo_atual = person.getSex();
        }

        if (!this.isFull() && !this.pessoas.contains(person)
                && getCurrentSex().equals(person.getSex())) {
            try {
                this.mutex_entrada.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Banheiro.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
    
            if (this.pessoas.add(person)) {
                System.out.println(person.getName() + " entrou no banheiro");
            }
            this.mutex_entrada.release();

            if (this.isFull()) {
                System.out.println("O banheiro estã cheio");
            }
        }
    }


    public void removeUser(Pessoa person) {
        this.semaforo.release();

        if (!this.isEmpty()) {
            try {
                this.mutex_saida.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Banheiro.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            if (this.pessoas.remove(person)) {
                System.out.println(person.getName() + " saio do banheiro");
            }
            this.mutex_saida.release();

            if (this.isEmpty()) {
                System.out.println("O banheiro ta vazio");
                this.sexo_atual = Sexo.NENHUM;
            }
        }
    }


    public boolean isInTheBathroom(Pessoa person) {
        return this.pessoas.contains(person);
    }

    public boolean isFull() {
        return this.capacidade == this.pessoas.size();
    }

    public boolean isEmpty() {
        return this.pessoas.isEmpty();
    }

    public Sexo getCurrentSex() {
        return this.sexo_atual;
    }

    @Override
    public String toString() {
        return "Banheiro{" + "sexo_atual = " + this.sexo_atual
                + ", capacidade = " + this.capacidade
                + ", numberOfUsers = " + this.pessoas.size() + '}';
    }

}