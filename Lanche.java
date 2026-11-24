public class Lanche extends Produto {

    public Lanche(String nome, double preco) {
        super(nome, preco);
    }

    @Override
    public String getTipo() {
        return "LANCHE";
    }
}
