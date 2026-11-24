import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivo {
    private static final String ARQ_CARDAPIO = "cardapio.txt";
    private static final String ARQ_PEDIDOS = "pedidos.txt";

    // Formato do cardapio.txt: TIPO;nome;preco
    public void salvarCardapio(List<Produto> cardapio) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ_CARDAPIO))) {
            for (Produto p : cardapio) {
                pw.println(p.getTipo() + ";" + p.getNome() + ";" + p.getPreco());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar cardapio: " + e.getMessage());
        }
    }

    public List<Produto> carregarCardapio() {
        List<Produto> cardapio = new ArrayList<>();
        File arquivo = new File(ARQ_CARDAPIO);
        if (!arquivo.exists()) return cardapio;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Produto p = lerProduto(linha);
                if (p != null) cardapio.add(p);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler cardapio: " + e.getMessage());
        }
        return cardapio;
    }

    // Formato do pedidos.txt: id#TIPO;nome;preco|TIPO;nome;preco
    public void salvarPedidos(List<Pedido> pedidos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ_PEDIDOS))) {
            for (Pedido ped : pedidos) {
                StringBuilder sb = new StringBuilder();
                sb.append(ped.getId()).append("#");
                for (int i = 0; i < ped.getItens().size(); i++) {
                    Produto p = ped.getItens().get(i);
                    if (i > 0) sb.append("|");
                    sb.append(p.getTipo()).append(";").append(p.getNome()).append(";").append(p.getPreco());
                }
                pw.println(sb);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar pedidos: " + e.getMessage());
        }
    }

    public List<Pedido> carregarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        File arquivo = new File(ARQ_PEDIDOS);
        if (!arquivo.exists()) return pedidos;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("#", 2);
                Pedido ped = new Pedido(Integer.parseInt(partes[0]));
                if (partes.length > 1 && !partes[1].isEmpty()) {
                    for (String item : partes[1].split("\\|")) {
                        Produto p = lerProduto(item);
                        if (p != null) ped.adicionarItem(p);
                    }
                }
                pedidos.add(ped);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao ler pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    private Produto lerProduto(String linha) {
        String[] c = linha.split(";");
        if (c.length < 3) return null;
        double preco = Double.parseDouble(c[2]);
        if (c[0].equals("LANCHE")) return new Lanche(c[1], preco);
        if (c[0].equals("BEBIDA")) return new Bebida(c[1], preco);
        return null;
    }
}
