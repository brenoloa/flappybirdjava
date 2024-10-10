import java.awt.*;

public class Bird {
    int x, y, largura, altura;
    Image[] imagens;
    int quadroAtual = 0;
    int contadorQuadros = 0;
    int atrasoQuadro = 10;
    int posicaoInicialY;

    public Bird(int x, int y, int largura, int altura, Image[] imagens) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.imagens = imagens;
        this.posicaoInicialY = y;
    }

    public void atualizar() {
        contadorQuadros++;
        if (contadorQuadros >= atrasoQuadro) {
            quadroAtual = (quadroAtual + 1) % imagens.length;
            contadorQuadros = 0;
        }
    }

    public void desenhar(Graphics g) {
        g.drawImage(imagens[quadroAtual], x, y, largura, altura, null);
    }

    public void mover(int gravidade, int velocidadeY) {
        velocidadeY += gravidade;
        y += velocidadeY;
        y = Math.max(y, 0);
    }

    public void resetarPosicao() {
        this.y = posicaoInicialY;
    }
}
