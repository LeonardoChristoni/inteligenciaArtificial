package genetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlgoritmoGenetico {
	List<Mochila> bestMochilas = new ArrayList<Mochila>();
	List<Mochila> mochilas = new ArrayList<Mochila>();
	List<Item> itens = new ArrayList<>();
	Mochila[] teste = mochilas.stream().toArray(Mochila[]::new);
	Item[] teste2 = itens.stream().toArray(Item[]::new);
	
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
			System.out.println("===============================");
			System.out.println("Geração "+i);
			
			if(bestMochilas.size()>0) {
				System.out.print("Itens : [");
				bestMochilas.get(0).getItens().forEach(x->System.out.print(x.getIdItem()+","));
				System.out.println("]");
				System.out.println("Num Itens: "+bestMochilas.get(0).getItens().size()+" Valor: "+bestMochilas.get(0).getValor()+" Peso: "+bestMochilas.get(0).getPeso());	
			}
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

			boolean paiA = i<itensPaiA.size(), paiB= i<itensPaiB.size();
			
			if(i%2==0) {
				mochilaA.getItens().add(paiA ? itensPaiA.get(i) : itensPaiB.get(i));
				atualizaValores(mochilaA, paiA ? itensPaiA.get(i) : itensPaiB.get(i), 0);
				
				mochilaB.getItens().add(paiB ? itensPaiB.get(i) : itensPaiA.get(i));
				atualizaValores(mochilaB, paiB ? itensPaiB.get(i) : itensPaiA.get(i), 0);
			
			}else {
				mochilaA.getItens().add(paiB ? itensPaiB.get(i) : itensPaiA.get(i));
				atualizaValores(mochilaA, paiB ? itensPaiB.get(i) : itensPaiA.get(i), 0);
				
				mochilaB.getItens().add(paiA ? itensPaiA.get(i) : itensPaiB.get(i));
				atualizaValores(mochilaB, paiA ? itensPaiA.get(i) : itensPaiB.get(i), 0);
			}
		}
		this.bestMochilas = new ArrayList<Mochila>();
		this.bestMochilas.add(mochilaA);
		this.bestMochilas.add(mochilaB);
	}
	
	private void mutacao() {
		Random rand = new Random(System.currentTimeMillis());
//		int itemEscolhido;
		
		if(bestMochilas.size()>0) {
			//MUTACAO MOCHILA A
//			itemEscolhido = rand.nextInt(itens.size()-1);
			Item itemEscolhido = itens.get(rand.nextInt(itens.size()-1));
			if(bestMochilas.get(0).getItens().contains(itemEscolhido)) {
				atualizaValores(bestMochilas.get(0), itemEscolhido, 1);
				bestMochilas.get(0).getItens().remove(itemEscolhido);
			}else {
				atualizaValores(bestMochilas.get(0), itemEscolhido, 0);
				bestMochilas.get(0).getItens().add(itemEscolhido);
			}
			
			//MUTACAO MOCHILA B
//			itemEscolhido = rand.nextInt(bestMochilas.get(1).getItens().size()-1);
			
			if(bestMochilas.get(1).getItens().contains(itemEscolhido)) {
				atualizaValores(bestMochilas.get(1), itemEscolhido, 1);
				bestMochilas.get(1).getItens().remove(itemEscolhido);
			}else {
				atualizaValores(bestMochilas.get(1), itemEscolhido, 0);
				bestMochilas.get(1).getItens().add(itemEscolhido);
			}
		}
		
		this.mochilas = new ArrayList<Mochila>(this.bestMochilas);
		
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
		teste = mochilas.stream().toArray(Mochila[]::new);
	}
	
	private void iniciaPopulacao() {
		Random rand = new Random(System.currentTimeMillis());
		
		int tamPopulacao = 5;
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
		teste = mochilas.stream().toArray(Mochila[]::new);
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
