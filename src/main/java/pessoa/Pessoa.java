package pessoa;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import banheiro.Banheiro;

import java.util.concurrent.TimeUnit;

public class Pessoa implements Runnable {

    private final String nome;
    private final Sexo sexo;
    private final Banheiro banheiro;
    private boolean vazaDaqui;
    private boolean precisaBanheiro;

    public Pessoa(String nome, Sexo sexo, Banheiro banheiro) {
        this.nome = nome;
        this.sexo = sexo;
        this.banheiro = banheiro;
        this.vazaDaqui = false;
        this.precisaBanheiro = true;
    }

    public void useBathroom() {
        this.banheiro.addUser(this);
        if (this.banheiro.isInTheBathroom(this)) {
            try {
                TimeUnit.SECONDS.sleep((new Random()).nextInt(1) + 1);
                this.vazaDaqui = true;
            } catch (InterruptedException ex) {
                Logger.getLogger(Pessoa.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    public void leaveBathroom() {
        this.banheiro.removeUser(this);
        this.vazaDaqui = false;
        this.precisaBanheiro = false;
    }

    public String getName() {
        return this.nome;
    }

    public Sexo getSex() {
        return this.sexo;
    }

    @Override
    public void run() {
        System.out.println(this.getName());
        while (this.precisaBanheiro) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pessoa.class.getName()).log(Level.SEVERE, null, ex);
            }
            if ((this.banheiro.getCurrentSex().equals(this.getSex())
                    || this.banheiro.getCurrentSex().equals(Sexo.NENHUM))
                    && !this.banheiro.isFull()
                    && !this.banheiro.isInTheBathroom(this)) {
                this.useBathroom();
            }
            if (this.vazaDaqui) {
                this.leaveBathroom();
            }
        }
    }

    @Override
    public String toString() {
        return "Pessoa{" + "nome = " + this.nome + ", sexo = " + this.sexo + '}';
    }
}
