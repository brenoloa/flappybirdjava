import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int larguraTela = 360;
    int alturaTela = 640;

    Image imagemFundo;
    Image imagemPipeTopo;
    Image imagemPipeBaixo;
    Image imagemChao;

    Bird passaro;
    int posicaoPassaroX = larguraTela / 8;
    int posicaoPassaroY = larguraTela / 2;
    int larguraPassaro = 34;
    int alturaPassaro = 24;

    int velocidadeX = -4;
    int velocidadeY = 0;
    int gravidade = 1;
    ArrayList<Pipe> canos;
    Random random = new Random();

    Timer loopJogo;
    Timer timerGerarCano;
    boolean fimDeJogo = false;
    double pontuacao = 0;

    Ground chao;
    int posicaoChaoY = 550;
    int alturaChao = 90;

    Image[] imagensPassaro;

    public FlappyBird() {
        setPreferredSize(new Dimension(larguraTela, alturaTela));
        setFocusable(true);
        addKeyListener(this);

        imagemFundo = new ImageIcon("C:/Users/Pichau/Downloads/flappybirdjava/src/images/background.png").getImage();
        imagemPipeTopo = new ImageIcon("C:/Users/Pichau/Downloads/flappybirdjava/src/images/pipetopo.png").getImage();
        imagemPipeBaixo = new ImageIcon("C:/Users/Pichau/Downloads/flappybirdjava/src/images/pipebaixo.png").getImage();
        imagemChao = new ImageIcon("C:/Users/Pichau/Downloads/flappybirdjava/src/images/ground.png").getImage();


        imagensPassaro = new Image[]{
                new ImageIcon("C:/Users/Pichau/Downloads/flappybirdjava/src/images/bluebird-upflap.png").getImage(),
                new ImageIcon("C:/Users/Pichau/Downloads/flappybirdjava/src/images/bluebird-midflap.png").getImage(),
                new ImageIcon("C:/Users/Pichau/Downloads/flappybirdjava/src/images/bluebird-downflap.png").getImage()
        };

        passaro = new Bird(posicaoPassaroX, posicaoPassaroY, larguraPassaro, alturaPassaro, imagensPassaro);
        canos = new ArrayList<>();

        chao = new Ground(0, posicaoChaoY, larguraTela, alturaChao, imagemChao);

        timerGerarCano = new Timer(1500, e -> gerarCanos());
        timerGerarCano.start();

        loopJogo = new Timer(1000 / 60, this);
        loopJogo.start();
    }

    void gerarCanos() {
        int minY = 100;
        int maxY = alturaTela - alturaChao - 100;

        int posicaoAleatoriaCano = random.nextInt(maxY - minY + 1) + minY;

        int espacoAbertura = alturaTela / 4;

        Pipe canoTopo = new Pipe(larguraTela, posicaoAleatoriaCano - 512, 64, 512, imagemPipeTopo);
        Pipe canoBaixo = new Pipe(larguraTela, posicaoAleatoriaCano + espacoAbertura, 64, 512, imagemPipeBaixo);

        canos.add(canoTopo);
        canos.add(canoBaixo);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenhar(g);
    }

    public void desenhar(Graphics g) {
        g.drawImage(imagemFundo, 0, 0, this.larguraTela, this.alturaTela, null);
        passaro.desenhar(g);

        for (Pipe cano : canos) {
            cano.desenhar(g);
        }

        chao.desenhar(g);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (fimDeJogo) {
            g.drawString("Game Over: " + (int) pontuacao, 10, 35);
        } else {
            g.drawString(String.valueOf((int) pontuacao), 10, 35);
        }
    }

    public void mover() {
        velocidadeY += gravidade;
        passaro.y += velocidadeY;
        passaro.y = Math.max(passaro.y, 0);

        for (Pipe cano : canos) {
            cano.x += velocidadeX;

            if (!cano.passou && passaro.x > cano.x + cano.largura) {
                pontuacao += 0.5;
                cano.passou = true;
            }

            if (colisao(passaro, cano)) {
                fimDeJogo = true;
            }
        }

        if (passaro.y > alturaTela - alturaChao) {
            fimDeJogo = true;
        }
    }

    boolean colisao(Bird passaro, Pipe cano) {
        return passaro.x < cano.x + cano.largura && passaro.x + passaro.largura > cano.x &&
                passaro.y < cano.y + cano.altura && passaro.y + passaro.altura > cano.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mover();
        passaro.atualizar();
        repaint();
        if (fimDeJogo) {
            timerGerarCano.stop();
            loopJogo.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocidadeY = -9;
            if (fimDeJogo) {
                passaro.y = posicaoPassaroY;
                velocidadeY = 0;
                canos.clear();
                fimDeJogo = false;
                pontuacao = 0;
                loopJogo.start();
                timerGerarCano.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
