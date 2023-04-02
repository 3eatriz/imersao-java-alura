import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {
 
    public void cria(InputStream inputStream, String nomeArquivo, String texto /*,InputStream inputStremSobreposicao*/) throws Exception{

        //leitura da imagem
        //InputStream inputStream = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_1.jpg").openStream();
        
        //InputStream inputStream = new FileInputStream(new File("entrada/PrimeiroFilme.jpg"));
        
        var imagemOriginal = ImageIO.read(inputStream);
        
        //criar nova imagem em memoria com transparencia e com tamanho novo
        
        int largura = imagemOriginal.getWidth(); 
        int altura = imagemOriginal.getHeight();
        int aumentar = 200;
        int novaAltura = altura + aumentar;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);
        //copiar a imagem original para nova imagem (em memoria)

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // BufferedImage imagemSobreposicao = ImageIO.read(inputStremSobreposicao);
        // int posicaoImagemSobreposicaoY = novaAltura - imagemSobreposicao.getHeight();
        // graphics.drawImage(imagemSobreposicao, 0, posicaoImagemSobreposicaoY, null);
        
        //configurar a fonte
        
        var fonte = new Font("impact", Font.BOLD, 80);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        //escrever uma frase na nova imagem
        
        FontMetrics fontMetrics = graphics.getFontMetrics();
        var retangulo = fontMetrics.getStringBounds(texto, graphics);
        var alturaTexto = (int) retangulo.getHeight();
        var larguraTexto = (int) retangulo.getWidth();
        var posicaoTextoX = (largura - larguraTexto)/2;
        var posicaoTextoY = novaAltura - (aumentar - alturaTexto)/2;
        graphics.drawString(texto, posicaoTextoX, posicaoTextoY);
        
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(texto, fonte, fontRenderContext);

        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(largura * 0.006f);

        graphics.setStroke(outlineStroke);
        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);
        //escrever a imagem nova em um arquivo
        
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));
    }
}
