import java.util.List;
import java.util.Map;

public class ExtratorConteudoLinguagens implements ExtratorConteudo{
    public List<Conteudo> extraiConteudos(String json){
        var parser = new JsonParser();
        List<Map<String, String>> listaAtributos = parser.parse(json); 

        var conteudos = listaAtributos.stream().map(x -> new Conteudo(x.get("title"), x.get("image"))).toList();

        return conteudos;
    }
}
