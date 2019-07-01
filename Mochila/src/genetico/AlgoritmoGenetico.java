package genetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class AlgoritmoGenetico {
	// PARAMETROS A DEFINIR, NUMERO DE GERACOES E TAMANHO DA POPULACAO
	private final int NUMEROGERACOES = 100, TAMPOPULACAO = 50; 
	
	//LISTA AUXILIAR QUE CONTEM AS REPRODUÇÕES
	List<Mochila> bestMochilas = new ArrayList<Mochila>();
	
	//LISTA DA POPULAÇÃO
	List<Mochila> mochilas = new ArrayList<Mochila>();
	
	//LISTA DE ITENS , COM BASE NA ENTRADA
	List<Item> itens = new ArrayList<>();
	
	public AlgoritmoGenetico(List<Item> itens){
		this.itens = itens;
		
		//GERA A POPULAÇÃO INICIAL, ATE OBTER DOIS CROMOSSOMOS
		while(true) {
			iniciaPopulacao();
			avaliaCromossomo();
			if(this.mochilas.size()>1) {
				break;
			}
		}
		
		//AVANÇA CONFORME NUMERO DE GERACOES DEFINIDO
		for(int i=0;i<NUMEROGERACOES;i++){
			selecaoEletista();
			crossover();
			mutacao();
			avaliaCromossomo();
			
			System.out.println("=============================================================================================");
			System.out.println("Geração: "+(i+1)+" Populacao: "+this.mochilas.size());
			
//			this.mochilas.forEach(x->System.out.println("v "+x.getValor()+" p "+x.getPeso()));
			
			if(bestMochilas.size()>0) {
				System.out.print("Itens : [");
				bestMochilas.get(0).getItens().forEach(x->System.out.print(x.getIdItem()+","));
				System.out.println("]");
				System.out.println("Num Itens: "+this.mochilas.get(0).getItens().size()+" Valor: "+this.mochilas.get(0).getValor()+" Peso: "+this.mochilas.get(0).getPeso());	
			}
		}
	}
	
	/* FUNÇÃO RESPONSAVEL POR FAZER O CROSSOVER , FORAM USADOS DOIS CROSSOVER */
	private void crossover() {
		int maiorSize,menorSize;
		
		Mochila mochilaA = new Mochila();
		Mochila mochilaB = new Mochila();
		
		List<Item> itensPaiA = bestMochilas.get(0).getItens();
		List<Item> itensPaiB = bestMochilas.get(1).getItens();
		
		if(itensPaiA.size() > itensPaiB.size()) {
			maiorSize = itensPaiA.size();
			menorSize = itensPaiB.size();
		}else {
			maiorSize = itensPaiB.size();
			menorSize = itensPaiA.size();
		}
		
		/* CROSSOVER 1
		 * CADA FILHO RECEBERA UM GENE DE CADA PAI A CADA ITERACAO 
		 * */
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
		
		//INICIALIZA LISTA DE FILHOS E ADICIONA 2 FILHOS, REPRODUZIDOS A PARTIR DO PRIMEIRO METODO DE CROSSOVER
		this.bestMochilas = new ArrayList<Mochila>();
		this.bestMochilas.add(mochilaA);
		this.bestMochilas.add(mochilaB);
		
		Random rand = new Random(System.currentTimeMillis());

		List<Integer> conjuntoPtsAleatorios = new ArrayList<Integer>();
		
		//ADICIONA NA LISTA OS POSSIVEIS PONTOS DE CORTE
		for(int x=0; x < menorSize;x++) {
			conjuntoPtsAleatorios.add(x);
		}
		
		for(int i = 0 ; i < menorSize ; i++) {
			Mochila m = new Mochila();
			
			//RAND EM UM PONTO DE CORTE, A PARTIR DA LISTA DE PONTOS DE CORTE
			int pontoCorte = rand.nextInt(conjuntoPtsAleatorios.size());
			
			//REMOVE O PONTO DE CORTE DA LISTA DE POSSIVEIS
			conjuntoPtsAleatorios.remove(pontoCorte);
			
			/* CROSSOVER 2
			 * UM FILHO RECEBERA OS GENES DO PAI A, ATE O PONTO DE CORTE 
			 * E OS GENES DO PAI B A PARTIR DO PONTO DE CORTE 
			 * */
			for(int j = 0; j < pontoCorte ; j++) {
				m.getItens().add(itensPaiA.get(j));
				atualizaValores(m, itensPaiA.get(j), 0);
			}
			
			int k;
			for(k = pontoCorte; k < itensPaiB.size() ; k++) {
				m.getItens().add(itensPaiB.get(k));
				atualizaValores(m, itensPaiB.get(k), 0);
			}
			
			this.bestMochilas.add(m);
		}
	}
	
	/* FAZ A MUTACAO DO GENE SORTEADO */
	private void mutacao() {
		Random rand = new Random(System.currentTimeMillis());
		
		if(bestMochilas.size()>0) {
			//ESCOLHE UM ITEM PARA SOFRER MUTACAO
			Item itemEscolhido = itens.get(rand.nextInt(itens.size()-1));
			
			//MUTAÇÃO: TEM = REMOVE, NÃO TEM = ADICIONA
			for(Mochila m: bestMochilas) {
				if(m.getItens().contains(itemEscolhido)) {
					atualizaValores(m, itemEscolhido, 1);
					m.getItens().remove(itemEscolhido);
				}else {
					atualizaValores(m, itemEscolhido, 0);
					m.getItens().add(itemEscolhido);
				}
			}
		}
		
		//ADICIONA OS FILHOS A POPULAÇÃO, QUE JA CONTEM OS PAIS
		for(Mochila m : this.bestMochilas) {
			this.mochilas.add(m);
		}
	}
	
	/* FAZ UMA SELEÇÃO MANTENDO OS VALORES ELITE */
	private void selecaoEletista() {
		this.bestMochilas = new ArrayList<Mochila>();

		//MANTEM SÓ OS DOIS MELHORES CROMOSSOMOS, QUE SERÃO PAIS NA PROXIMA GERAÇÃO
		this.bestMochilas.add(mochilas.get(0));
		this.bestMochilas.add(mochilas.get(1));
		this.mochilas = new ArrayList<Mochila>(this.bestMochilas); 
	}
	
	/* FAZ A AVALIAÇÃO DA POPULAÇÃO, E SE NECESSARIO SEU CORTE*/
	private void avaliaCromossomo() {
		
		//ORDENA POPULAÇÃO, MOCHILA
		Collections.sort(mochilas);
		
		//VERIFICA SE PESO ESTA DENTRO DA CAPACIDADE
		for(int i = 0 ; i < this.mochilas.size() ; i++) {
			if(this.mochilas.get(i).getPeso() > this.mochilas.get(i).getCapacidadeTotal()) {
				this.mochilas.remove(i);
				i--;
			}
		}
	}
	
	/* GERA POPULAÇÃO INICIAL */
	private void iniciaPopulacao() {
		Random rand = new Random(System.currentTimeMillis());
		
		for(int i=0;i<TAMPOPULACAO;i++) {
			//GERA CROMOSSOMO, MOCHILA
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
	
	/* FAZ A SOMATÓRIA DO VALOR E PESO DE CADA MOCHILA */
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
