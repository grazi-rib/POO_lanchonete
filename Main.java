Simport java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static GerenciadorArquivo arquivo = new GerenciadorArquivo();
    private static List<Produto> cardapio = arquivo.carregarCardapio();
    private static List<Pedido> pedidos = arquivo.carregarPedidos();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n===== LANCHONETE =====");
            System.out.println("1 - Cadastrar item no cardapio");
            System.out.println("2 - Listar cardapio");
            System.out.println("3 - Novo pedido");
            System.out.println("4 - Listar pedidos");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1: cadastrarItem(); break;
                case 2: listarCardapio(); break;
                case 3: novoPedido(); break;
                case 4: listarPedidos(); break;
                case 0: System.out.println("Ate logo!"); break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarItem() {
        System.out.print("Tipo (1 - Lanche, 2 - Bebida): ");
        int tipo = lerInteiro();
        System.out.print("Nome: ");
        String nome = sc.nextLine().replace(";", "").replace("|", "").replace("#", "");
        System.out.print("Preco: ");
        double preco = lerDouble();

        if (tipo == 1) {
            cardapio.add(new Lanche(nome, preco));
        } else if (tipo == 2) {
            cardapio.add(new Bebida(nome, preco));
        } else {
            System.out.println("Tipo invalido.");
            return;
        }
        arquivo.salvarCardapio(cardapio);
        System.out.println("Item cadastrado!");
    }

    private static void listarCardapio() {
        if (cardapio.isEmpty()) {
            System.out.println("Cardapio vazio.");
            return;
        }
        System.out.println("\n--- CARDAPIO ---");
        for (int i = 0; i < cardapio.size(); i++) {
            System.out.println((i + 1) + " - " + cardapio.get(i));
        }
    }

    private static void novoPedido() {
        if (cardapio.isEmpty()) {
            System.out.println("Cadastre itens no cardapio primeiro.");
            return;
        }
        Pedido pedido = new Pedido(pedidos.size() + 1);
        int escolha;
        do {
            listarCardapio();
            System.out.print("Numero do item (0 para finalizar): ");
            escolha = lerInteiro();
            if (escolha >= 1 && escolha <= cardapio.size()) {
                pedido.adicionarItem(cardapio.get(escolha - 1));
                System.out.println("Item adicionado!");
            } else if (escolha != 0) {
                System.out.println("Item invalido.");
            }
        } while (escolha != 0);

        if (pedido.getItens().isEmpty()) {
            System.out.println("Pedido vazio, cancelado.");
            return;
        }
        pedidos.add(pedido);
        arquivo.salvarPedidos(pedidos);
        System.out.println("\nPedido salvo!\n" + pedido);
    }

    private static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println("\n" + p);
        }
    }

    private static int lerInteiro() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double lerDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
