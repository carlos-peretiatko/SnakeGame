import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class GamePanel extends JPanel implements ActionListener {

    private final int TAMANHO_BLOCO = 20; // Tamanho do bloco da cobrinha
    private final int LARGURA = 780, ALTURA = 550; // Tamanho da tela do jogo
    private final LinkedList<Point> cobrinha = new LinkedList<>();
    private Point comida;
    private boolean gameOver = false;
    private int direcao = KeyEvent.VK_RIGHT; // Direção inicial da cobrinha
    private Timer timer; // Timer para controle da velocidade da cobrinha

    public GamePanel() {
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override //sobrepoe
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    mudarDirecao(e.getKeyCode());
                }
            }
        });
    }

    public void iniciarJogo() {
        cobrinha.clear();
        cobrinha.add(new Point(100, 100)); // Posição inicial da cobrinha
        gerarComida();
        this.requestFocus(); // Adicione esta linha aqui
        timer = new Timer(100, this); // 100 ms = 10 FPS
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) { //diferente de gameover true
            moverCobrinha();
            verificarColisao();
            repaint(); // Atualiza a tela
        }
    }

    public void moverCobrinha() {
        Point cabeca = cobrinha.getFirst();
        Point novaCabeca = new Point(cabeca);
        
        switch (direcao) {
            case KeyEvent.VK_UP:
                novaCabeca.translate(0, -TAMANHO_BLOCO);
                break;
            case KeyEvent.VK_DOWN:
                novaCabeca.translate(0, TAMANHO_BLOCO);
                break;
            case KeyEvent.VK_LEFT:
                novaCabeca.translate(-TAMANHO_BLOCO, 0);
                break;
            case KeyEvent.VK_RIGHT:
                novaCabeca.translate(TAMANHO_BLOCO, 0);
                break;
        }

        cobrinha.addFirst(novaCabeca); // Adiciona a nova cabeça no início
        if (novaCabeca.equals(comida)) {
            gerarComida(); // A cobrinha comeu a comida
        } else {
            cobrinha.removeLast(); // Remove a última parte da cobrinha (movimento)
        }
    }

    public void verificarColisao() {
        Point cabeca = cobrinha.getFirst();
        
        // Colisão com a parede
        if (cabeca.x < 0 || cabeca.x >= LARGURA || cabeca.y < 0 || cabeca.y >= ALTURA) {
            gameOver = true;
        }
        
        // Colisão com o corpo da cobra
        for (int i = 1; i < cobrinha.size(); i++) {
            if (cobrinha.get(i).equals(cabeca)) {
                gameOver = true;
            }
        }
    }

    public void gerarComida() {
        int x = (int) (Math.random() * (LARGURA / TAMANHO_BLOCO)) * TAMANHO_BLOCO; //gera comida em uma posição aleatória pelo calculo
        int y = (int) (Math.random() * (ALTURA / TAMANHO_BLOCO)) * TAMANHO_BLOCO;
        comida = new Point(x, y);
    }

    public void mudarDirecao(int tecla) {
        if (tecla == KeyEvent.VK_UP && direcao != KeyEvent.VK_DOWN) {
            direcao = KeyEvent.VK_UP;
        } else if (tecla == KeyEvent.VK_DOWN && direcao != KeyEvent.VK_UP) {
            direcao = KeyEvent.VK_DOWN;
        } else if (tecla == KeyEvent.VK_LEFT && direcao != KeyEvent.VK_RIGHT) {
            direcao = KeyEvent.VK_LEFT;
        } else if (tecla == KeyEvent.VK_RIGHT && direcao != KeyEvent.VK_LEFT) {
            direcao = KeyEvent.VK_RIGHT;
        }
    }

    @Override //sobrepoe
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Game Over!", LARGURA / 2 - 50, ALTURA / 2 - 40);
            g.drawString("Pontuação: " + (cobrinha.size() - 1), LARGURA / 2 - 50, ALTURA / 2);
            g.drawString("Pressione R para reiniciar", LARGURA / 2 - 100, ALTURA / 2 + 40);
            g.drawString("Pressione ESC para voltar ao menu", LARGURA / 2 - 120, ALTURA / 2 + 80);
        } else {
            // Desenha a pontuação durante o jogo
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Pontuação: " + (cobrinha.size() - 1), 10, 20);

            // Desenha a cobra
            g.setColor(Color.GREEN);
            for (Point p : cobrinha) {
                g.fillRect(p.x, p.y, TAMANHO_BLOCO, TAMANHO_BLOCO);
            }

            // Desenha a comida
            g.setColor(Color.RED);
            g.fillRect(comida.x, comida.y, TAMANHO_BLOCO, TAMANHO_BLOCO);
        }
    }

    // Adicione este método para reiniciar o jogo
    public void reiniciarJogo() {
        gameOver = false;
        iniciarJogo();
    }
}