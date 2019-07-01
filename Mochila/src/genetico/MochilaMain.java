package genetico;

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
		
		Path caminho = Paths.get(System.getProperty("user.home"),"Downloads/teste.csv");
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
			Item item = new Item(Double.parseDouble(lin[1]),Double.parseDouble(lin[2]), lin[0]);
			itens.add(item);
		}
		
		new AlgoritmoGenetico(itens);
	}

}
