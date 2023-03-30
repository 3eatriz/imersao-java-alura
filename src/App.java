import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        var geradora = new GeradoraDeFigurinhas();
        // fazer conexão HTTP e buscar os top 250 filmes
        
        /* 
        IMPORTANTE = CONFIGURAR A VARIAVEL DE AMBIENTE COM A KEY
        {export IMDB_API_KEY="minha chave"}
        String imdbKey = System.getenv("IMDB_API_KEY");
        String url = "https://imdb-api.com/en/API/Top250Movies/" + imdbKey;
        List<Map<String, String>> listaFilmes = mostrar(url); 
        for (Map<String,String> filme : listaFilmes) {
            System.out.println("\u001b[44mTÍTULO: \u001b[1m" + filme.get("title") + "\u001b[m");
            System.out.println("\u001b[44mPOSTER: \u001b[1m" + filme.get("image") + "\u001b[m");
            System.out.println("\u001b[45mCLASSIFICAÇÃO: " + filme.get("imDbRating") + "\u001b[m");

            var nota = filme.get("imDbRating").substring(0,1);
            int estrelas = Integer.parseInt(nota);
            for(int i = 0; i < estrelas; i++){
                System.out.print("\u2B50");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println(); 
        */

        //MAIORES NOTAS
        String url1 = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        List<Map<String, String>> listaFilmes1 = mostrar(url1); 

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        for (Map<String,String> filme : listaFilmes1) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title").replace(":","");
             
            String nomeArquivo = "figurinhas/" + titulo + ".png";

            InputStream inputStream = new URL(urlImagem).openStream();
            
            System.out.println("\u001b[44mTÍTULO: \u001b[1m" + titulo + "\u001b[m");
            System.out.println("\u001b[44mPOSTER: \u001b[1m" + urlImagem + "\u001b[m");
            System.out.println("\u001b[45mCLASSIFICAÇÃO: " + filme.get("imDbRating") + "\u001b[m");

            var nota = filme.get("imDbRating").substring(0,1);
            int estrelas = Integer.parseInt(nota);
            for(int i = 0; i < estrelas; i++){
                System.out.print("\u2B50");
            }
            System.out.println();

            String texto;
            InputStream imagemBia;
            if(estrelas > 8){
                texto = "TOPZERA";
                imagemBia = new FileInputStream(new File("alura-stickers/sobreposicao/bia-sorrindo.jpg"));
            }
            else if(estrelas > 7){
                texto = "BOM";
                imagemBia = new FileInputStream(new File("alura-stickers/sobreposicao/bia-seria.png"));
            }
            else{
                texto = "MÉDIO";
                imagemBia = new FileInputStream(new File("alura-stickers/sobreposicao/bia-seria.png"));
            }

            geradora.cria(inputStream, nomeArquivo, texto, imagemBia);
        }

        System.out.println();
        System.out.println();

        //MAIS POPULARES
        String url2 = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        List<Map<String, String>> listaFilmes2 = mostrar(url2);

        for (Map<String,String> filme : listaFilmes2) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title").replace(":","");
             
            String nomeArquivo = "figurinhas/" + titulo + ".png";

            InputStream inputStream = new URL(urlImagem).openStream();
            
            System.out.println("\u001b[44mTÍTULO: \u001b[1m" + titulo + "\u001b[m");
            System.out.println("\u001b[44mPOSTER: \u001b[1m" + urlImagem + "\u001b[m");
            System.out.println("\u001b[45mCLASSIFICAÇÃO: " + filme.get("imDbRating") + "\u001b[m");

            var nota = filme.get("imDbRating").substring(0,1);
            int estrelas = Integer.parseInt(nota);
            for(int i = 0; i < estrelas; i++){
                System.out.print("\u2B50");
            }
            System.out.println();
            
            String texto;
            InputStream imagemBia;
            if(estrelas > 8){
                texto = "TOPZERA";
                imagemBia = new FileInputStream(new File("alura-stickers/sobreposicao/bia-sorrindo.jpg"));
            }
            else if(estrelas > 7){
                texto = "BOM";
                imagemBia = new FileInputStream(new File("alura-stickers/sobreposicao/bia-seria.png"));
            }
            else{
                texto = "MÉDIO";
                imagemBia = new FileInputStream(new File("alura-stickers/sobreposicao/bia-seria.png"));
            }

            geradora.cria(inputStream, nomeArquivo, texto, imagemBia);
        }
    }

    public static List<Map<String, String>> mostrar(String url) throws IOException, InterruptedException{
        var endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        return listaDeFilmes;
    }
}
