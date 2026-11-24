import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private List<Produto> itens;

    public Pedido(int id) {
        this.id = id;
        this.itens = new ArrayList<>();
    }

    public int getId() { return id; }
    public List<Produto> getItens() { return itens; }

    public void adicionarItem(Produto produto) {
        itens.add(produto);
    }

    public double calcularTotal() {
        double total = 0;
        for (Produto p : itens) {
            total += p.getPreco();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id).append("\n");
        for (Produto p : itens) {
            sb.append("   ").append(p).append("\n");
        }
        sb.append(String.format("   TOTAL: R$ %.2f", calcularTotal()));
        return sb.toString();
    }
}
