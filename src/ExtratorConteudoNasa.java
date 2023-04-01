import java.util.List;
import java.util.Map;

public class ExtratorConteudoNasa implements ExtratorConteudo{
    public List<Conteudo> extraiConteudos(String json){
        var parser = new JsonParser();
        List<Map<String, String>> listaAtributos = parser.parse(json); 

        // List<Conteudo> conteudos = new ArrayList<>();

        // for (Map<String, String> atributos : listaAtributos) {
        //     String titulo = atributos.get("title").replace(":","");
        //     String urlImagem = atributos.get("url");
        //     var conteudo = new Conteudo(titulo, urlImagem);

        //     conteudos.add(conteudo);
        // }

        var conteudos = listaAtributos.stream().map(x -> new Conteudo(x.get("title").replace(":",""), x.get("url"))).toList();

        return conteudos;
    }
}
