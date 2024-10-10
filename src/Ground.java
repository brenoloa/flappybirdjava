import java.awt.*;

public class Ground {
    int x, y, largura, altura;
    Image imagem;

    public Ground(int x, int y, int largura, int altura, Image imagem) {
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
