import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        API api = API.LINGUAGENS;
        String url = api.getUrl();

        ExtratorConteudo  extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);
        
        List<Conteudo> conteudos = extrator.extraiConteudos(json);
        
        var geradora = new GeradoraDeFigurinhas();

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        for (Conteudo conteudo : conteudos) {
             
            InputStream inputStream = new URL(conteudo.urlImagem()).openStream();
            String nomeArquivo = "figurinhas/" + conteudo.titulo() + ".png";

            //InputStream imagemBia = new FileInputStream(new File("alura-stickers/sobreposicao/bia-sorrindo.jpg"));

            geradora.cria(inputStream, nomeArquivo, conteudo.titulo()/*, imagemBia*/);

            System.out.println("\u001b[44mTÍTULO: \u001b[1m" + conteudo.titulo() + "\u001b[m");
            System.out.println("\u001b[44mPOSTER: \u001b[1m" + conteudo.urlImagem() + "\u001b[m");
      
        }
    }
}
