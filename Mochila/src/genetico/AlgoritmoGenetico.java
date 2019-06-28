package genetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlgoritmoGenetico {
	List<Mochila> bestMochilas = new ArrayList<Mochila>();
	List<Mochila> mochilas = new ArrayList<Mochila>();
	List<Item> itens = new ArrayList<>();
	
	
	public AlgoritmoGenetico(List<Item> itens){
		this.itens = itens;
		iniciaPopulacao();
		
		int numeroGeracoes=15;
		
		for(int i=0;i<numeroGeracoes;i++){
			avaliaCromossomo();
			if(selecaoEletista()) {
				crossover();
			}
			mutacao();

			System.out.println("Geração "+i);
			
		}
	}
	
	private void crossover() {
		this.mochilas = new ArrayList<Mochila>();
		
		int maiorSize;
		Mochila mochilaA = new Mochila();
		Mochila mochilaB = new Mochila();
		
		List<Item> itensPaiA = bestMochilas.get(0).getItens();
		List<Item> itensPaiB = bestMochilas.get(1).getItens();
		
		if(itensPaiA.size() > itensPaiB.size()) {
			maiorSize = itensPaiA.size();
		}else {
			maiorSize = itensPaiB.size();
		}
		
		for(int i=0;i<maiorSize;i++) {
			if(i%2==0) {
				if(i<itensPaiA.size()) {
					mochilaA.getItens().add(itensPaiA.get(i));
					mochilaB.getItens().add(itensPaiB.get(i));
					
					
				}else{
					mochilaA.getItens().add(itensPaiB.get(i));
					mochilaB.getItens().add(itensPaiB.get(i));
				}
			}else {
				if(i<itensPaiB.size()) {
					mochilaB.getItens().add(itensPaiA.get(i));
					mochilaA.getItens().add(itensPaiB.get(i));
				}else {
					mochilaB.getItens().add(itensPaiA.get(i));
					mochilaA.getItens().add(itensPaiA.get(i));
				}
			}
		}
		this.bestMochilas = new ArrayList<Mochila>();
		this.bestMochilas.add(mochilaA);
		this.bestMochilas.add(mochilaB);
	}
	
	private void mutacao() {
		Random rand = new Random(System.currentTimeMillis());
		int itemEscolhido;
		
		//MUTACAO MOCHILA A
		itemEscolhido = rand.nextInt(bestMochilas.get(0).getItens().size()-1);
		bestMochilas.get(0).getItens().remove(itemEscolhido);
		atualizaValores(bestMochilas.get(0), bestMochilas.get(0).getItens().get(itemEscolhido), 1);
		
		//MUTACAO MOCHILA B
		itemEscolhido = rand.nextInt(bestMochilas.get(1).getItens().size()-1);
		bestMochilas.get(1).getItens().remove(itemEscolhido);
		atualizaValores(bestMochilas.get(1), bestMochilas.get(1).getItens().get(itemEscolhido), 1);
	}
	
	private boolean selecaoEletista() {
		Collections.sort(mochilas);
		if(mochilas.size() > 1) {
			this.bestMochilas = new ArrayList<Mochila>();
			this.bestMochilas.add(mochilas.get(0));
			this.bestMochilas.add(mochilas.get(1));
			return true;
		}
		return false;
	}
	
	private void avaliaCromossomo() {
		
		for(int i = 0 ; i < this.mochilas.size() ; i++) {
			if(this.mochilas.get(i).getPeso() > this.mochilas.get(i).getCapacidadeTotal()) {
				this.mochilas.remove(i);
				i--;
			}
		}
	}
	
	private void iniciaPopulacao() {
		Random rand = new Random(System.currentTimeMillis());
		
		int tamPopulacao = 2;
		
		for(int i=0;i<tamPopulacao;i++) {
			//gera cromossomo
			Mochila mochila = new Mochila();
			for(int j=0;j<itens.size();j++) {
				if(rand.nextInt(2) == 1) {
					mochila.getItens().add(itens.get(j));
					atualizaValores(mochila, itens.get(j), 0);
				}
			}
			this.mochilas.add(mochila);
		}
	}
	
	private void atualizaValores(Mochila mochila,Item item,int verificador) {
		//if verificador = 0 ? soma : subtrai
		
		if(verificador == 0) {
			mochila.setPeso(mochila.getPeso() + item.getPeso());
			mochila.setValor(mochila.getValor() + item.getValor());
		}else {
			mochila.setPeso(mochila.getPeso() - item.getPeso());
			mochila.setValor(mochila.getValor() - item.getValor());
		}
	}
}
