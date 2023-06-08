package br.eu.trabalhocontabanco.model;


public class Banco {
    private int nmrConta;
    private String nomeTitular;
    private double saldo;
    private double deposito;
    private double saque;

    public Banco(int nmrConta, String nomeTitular, double saldo, double deposito, double saque) {
        this.nmrConta = nmrConta;
        this.nomeTitular = nomeTitular;
        this.saldo = saldo;
        this.deposito = deposito;
        this.saque = saque;
    }

    public int getNmrConta() {
        return nmrConta;
    }

    public void setNmrConta(int nmrConta) {
        this.nmrConta = nmrConta;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getDeposito() {
        return deposito;
    }

    public void setDeposito(double deposito) {
        this.deposito = deposito;
    }

    public double getSaque() {
        return saque;
    }

    public void setSaque(double saque) {
        this.saque = saque;
    }
    
}