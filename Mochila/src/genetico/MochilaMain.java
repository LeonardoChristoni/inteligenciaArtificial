package genetico;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MochilaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Path caminho = Paths.get(System.getProperty("user.home"),"Downloads/item_50.csv");
		Supplier<Stream<String>> lines = () -> {
			try {
				return Files.lines(caminho);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		};
		
		lines.get().forEach(System.out::println);
		
		List<String> linhas = lines.get().skip(1).collect(Collectors.toList());
		
		new Mochila(Double.parseDouble(linhas.get(0).split(",")[1]));
		
		linhas.remove(0);
		
		List<Item> itens = new ArrayList<>();
		
		/* INSTANCIA NOVA ITEM, COM PESO, VALOR E ID */
		for(String linha : linhas) {
			String lin[] = linha.split(",");
			Item item = new Item(Double.parseDouble(lin[1]),Double.parseDouble(lin[2]), lin[0],false);
			itens.add(item);
		}
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(itens);
		List<Item> solucao = ag.gerarSolucao();
		
		Path saida = Paths.get(System.getProperty("user.home"),"Downloads/saida.csv");
		gerarSaidaCSV(saida,solucao);
	}
	
	private static void gerarSaidaCSV(Path caminho,List<Item> solucao) {
		try {
			FileWriter writer = new FileWriter(caminho.toString());
			for(Item sol : solucao) {
				if(sol.isSelecionado()) {
					writer.append("1");
					writer.append("\n");
				}else {
					writer.append("0");
					writer.append("\n");
				}
			}
			
			writer.flush();  
			writer.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
