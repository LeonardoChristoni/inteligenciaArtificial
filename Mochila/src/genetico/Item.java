package genetico;

public class Item {
	
	private double valor;
	private double peso;
	private String idItem;
	private boolean selecionado;
	
	public Item(double valor, double peso, String idItem, boolean selecionado) {
		this.valor = valor;
		this.peso = peso;
		this.idItem = idItem;
		this.selecionado = selecionado;
	}
	
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getIdItem() {
		return idItem;
	}

	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

}
