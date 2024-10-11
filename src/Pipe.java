import java.awt.*;

public class Pipe {
    int x, y, largura, altura;
    Image imagem;
    boolean passou = false;

    //set posicao tamanho do pip a logica de movimento ta no flappy msm
    public Pipe(int x, int y, int largura, int altura, Image imagem) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.imagem = imagem;
    }

    public void desenhar(Graphics g) {
        g.drawImage(imagem, x, y, largura, altura, null);
    }
}
