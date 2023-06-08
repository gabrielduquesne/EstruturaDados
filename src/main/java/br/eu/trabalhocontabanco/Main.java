package br.eu.trabalhocontabanco;

import br.eu.trabalhocontabanco.model.Banco;
import java.util.Arrays;
import java.util.Scanner;



public class Main {

    private static Banco[] contas;
    private static int numContas;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        contas = new Banco[100];
        numContas = 0;

        int opcao;
        do {
            System.out.println("-------- Menu --------");
            System.out.println("1. Cadastro de contas bancárias");
            System.out.println("2. Ordenação das contas bancárias");
            System.out.println("3. Depósito");
            System.out.println("4. Saque");
            System.out.println("5. Calcular saldo total do banco");
            System.out.println("6. Verificação de saldo negativo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastrarContaBancaria(scanner);
                    break;
                case 2:
                    ordenarContasBancarias(scanner);
                    break;
                case 3:
                    realizarDeposito(scanner);
                    break;
                case 4:
                    realizarSaque(scanner);
                    break;
                case 5:
                    calcularSaldoTotal();
                    break;
                case 6:
                    verificarSaldoNegativo();
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            System.out.println();
        } while (opcao != 0);
    }

    private static void cadastrarContaBancaria(Scanner scanner) {
        System.out.println("-------- Cadastro de Contas Bancárias --------");
        System.out.print("Número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do teclado
        System.out.print("Nome do titular: ");
        String nomeTitular = scanner.nextLine();
        System.out.print("Saldo inicial: ");
        double saldo = scanner.nextDouble();

        Banco conta = new ContasBancarias ( numeroConta, nomeTitular, saldo);
        contas[numContas] = conta;
        numContas++;

        System.out.println("Conta bancária cadastrada com sucesso!");
    }

    private static void ordenarContasBancarias(Scanner scanner) {
        System.out.println("-------- Ordenação das Contas Bancárias --------");
        System.out.println("1. Ordenar por número da conta");
        System.out.println("2. Ordenar por saldo");
        System.out.print("Escolha um critério de ordenação: ");
        int criterio = scanner.nextInt();

        if (criterio == 1) {
            Arrays.sort(contas, 0, numContas, (c1, c2) -> Integer.compare(c1.getNmrConta(), c2.getNmrConta()));
        } else if (criterio == 2) {
            Arrays.sort(contas, 0, numContas, (c1, c2) -> Double.compare(c1.getSaldo(), c2.getSaldo()));
        } else {
            System.out.println("Critério inválido.");
            return;
        }

        System.out.println("Contas bancárias ordenadas com sucesso!");
        exibirContasBancarias();
    }

    private static void exibirContasBancarias() {
        System.out.println("-------- Contas Bancárias --------");
        for (int i = 0; i < numContas; i++) {
            Banco conta = contas[i];
            System.out.println("Número da conta: " + conta.getNmrConta());
            System.out.println("Nome do titular: " + conta.getNomeTitular());
            System.out.println("Saldo: " + conta.getSaldo());
            System.out.println();
        }
    }

    private static void realizarDeposito(Scanner scanner) {
        System.out.println("-------- Depósito --------");
        System.out.println("1. Pesquisar conta por número");
        System.out.println("2. Pesquisar conta por nome do titular");
        System.out.print("Escolha um método de pesquisa: ");
        int metodoPesquisa = scanner.nextInt();

        scanner.nextLine(); // Limpar o buffer do teclado

        Banco contaEncontrada = null;

        if (metodoPesquisa == 1) {
            System.out.print("Número da conta: ");
            int numeroConta = scanner.nextInt();
            contaEncontrada = pesquisarContaPorNumero(numeroConta);
        } else if (metodoPesquisa == 2) {
            System.out.print("Nome do titular: ");
            String nomeTitular = scanner.nextLine();
            contaEncontrada = pesquisarContaPorTitular(nomeTitular);
        } else {
            System.out.println("Método de pesquisa inválido.");
            return;
        }

        if (contaEncontrada != null) {
            System.out.print("Valor do depósito: ");
            double valorDeposito = scanner.nextDouble();
            contaEncontrada.setDeposito(valorDeposito);
            contaEncontrada.setSaldo(contaEncontrada.getSaldo() + valorDeposito);
            System.out.println("Depósito realizado com sucesso!");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static Banco pesquisarContaPorNumero(int numeroConta) {
        for (int i = 0; i < numContas; i++) {
            if (contas[i].getNmrConta() == numeroConta) {
                return contas[i];
            }
        }
        return null;
    }

    private static Banco pesquisarContaPorTitular(String nomeTitular) {
        for (int i = 0; i < numContas; i++) {
            if (contas[i].getNomeTitular().equalsIgnoreCase(nomeTitular)) {
                return contas[i];
            }
        }
        return null;
    }

    private static void realizarSaque(Scanner scanner) {
        System.out.println("-------- Saque --------");
        ordenarContasBancarias(scanner); // Ordenar as contas por número antes de realizar a pesquisa binária
        System.out.print("Número da conta: ");
        int numeroConta = scanner.nextInt();
        Banco contaEncontrada = pesquisarContaPorNumeroBinario(numeroConta, 0, numContas - 1);

        if (contaEncontrada != null) {
            System.out.print("Valor do saque: ");
            double valorSaque = scanner.nextDouble();
            if (valorSaque <= contaEncontrada.getSaldo()) {
                contaEncontrada.setSaque(valorSaque);
                contaEncontrada.setSaldo(contaEncontrada.getSaldo() - valorSaque);
                System.out.println("Saque realizado com sucesso!");
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static Banco pesquisarContaPorNumeroBinario(int numeroConta, int inicio, int fim) {
        if (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            if (contas[meio].getNmrConta() == numeroConta) {
                return contas[meio];
            } else if (contas[meio].getNmrConta() > numeroConta) {
                return pesquisarContaPorNumeroBinario(numeroConta, inicio, meio - 1);
            } else {
                return pesquisarContaPorNumeroBinario(numeroConta, meio + 1, fim);
            }
        }
        return null;
    }

    private static void calcularSaldoTotal() {
        double saldoTotal = calcularSaldoTotalRecursivo(0, numContas - 1);
        System.out.println("Saldo total do banco: " + saldoTotal);
    }

    private static double calcularSaldoTotalRecursivo(int inicio, int fim) {
        if (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            double saldoEsquerda = calcularSaldoTotalRecursivo(inicio, meio - 1);
            double saldoDireita = calcularSaldoTotalRecursivo(meio + 1, fim);
            double saldoMeio = contas[meio].getSaldo() + contas[meio].getDeposito() - contas[meio].getSaque();
            return saldoEsquerda + saldoDireita + saldoMeio;
        }
        return 0;
    }

    private static void verificarSaldoNegativo() {
        System.out.println("-------- Verificação de Saldo Negativo --------");
        verificarSaldoNegativoRecursivo(0, numContas - 1);
    }

    private static void verificarSaldoNegativoRecursivo(int inicio, int fim) {
        if (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            double saldoMeio = contas[meio].getSaldo() + contas[meio].getDeposito() - contas[meio].getSaque();
            if (saldoMeio < 0) {
                System.out.println("Conta com saldo negativo:");
                System.out.println("Número da conta: " + contas[meio].getNmrConta());
                System.out.println("Saldo: " + saldoMeio);
            }
            verificarSaldoNegativoRecursivo(inicio, meio - 1);
            verificarSaldoNegativoRecursivo(meio + 1, fim);
        }
    }
}