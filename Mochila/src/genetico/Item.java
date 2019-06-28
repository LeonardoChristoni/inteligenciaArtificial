package genetico;

public class Item {
	
	private double valor;
	private double peso;
	private String idItem;
	
	public Item(double valor, double peso, String idItem) {
		this.valor = valor;
		this.peso = peso;
		this.idItem = idItem;
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

}
