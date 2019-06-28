package genetico;

import java.util.ArrayList;

public class Mochila implements Comparable<Mochila>{
	private static double capacidadeTotal;
	private double peso=0;
	private double valor=0;
	private ArrayList<Item> itens = new ArrayList<>();
	
	public Mochila(double capacidade) {
		capacidadeTotal = capacidade;
	}

	public Mochila() {
		// TODO Auto-generated constructor stub
	}

	public double getCapacidadeTotal() {
		return capacidadeTotal;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public ArrayList<Item> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Item> itens) {
		this.itens = itens;
		
		for(Item item:itens) {
			peso += item.getPeso();
		}
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public int compareTo(Mochila o) {
		if(this.getValor() > o.getValor()) {
			return -1;
		}
		if(this.getValor() < o.getValor()) {
			return 1;
		}
		return 0;
	}
}
