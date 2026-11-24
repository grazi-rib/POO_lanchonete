public abstract class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    // Cada subclasse informa o seu tipo (polimorfismo)
    public abstract String getTipo();

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f", getTipo(), nome, preco);
    }
}
