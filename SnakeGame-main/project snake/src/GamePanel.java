import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class GamePanel extends JPanel implements ActionListener {

    private final int TAMANHO_BLOCO = 20;
    private final int LARGURA = 780, ALTURA = 550;
    private final LinkedList<Point> cobrinha = new LinkedList<>();
    private Point comida;
    private boolean gameOver = false;
    private int direcao = KeyEvent.VK_RIGHT;
    private Timer timer;

    public GamePanel(CardLayout layout, JPanel container) {
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    mudarDirecao(e.getKeyCode());
                }

                if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
                    reiniciarJogo();
                }

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    layout.show(container, "Menu");
                    pararJogo();
                }
            }
        });
    }

    public void iniciarJogo() {
        cobrinha.clear();
        cobrinha.add(new Point(100, 100));
        direcao = KeyEvent.VK_RIGHT;
        gerarComida();
        gameOver = false;
        this.requestFocusInWindow();
        timer = new Timer(100, this);
        timer.start();
    }

    public void pararJogo() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void reiniciarJogo() {
        iniciarJogo();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moverCobrinha();
            verificarColisao();
            repaint();
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

        cobrinha.addFirst(novaCabeca);

        if (novaCabeca.equals(comida)) {
            gerarComida();
        } else {
            cobrinha.removeLast();
        }
    }

    public void verificarColisao() {
        Point cabeca = cobrinha.getFirst();

        // Parede
        if (cabeca.x < 0 || cabeca.x >= LARGURA || cabeca.y < 0 || cabeca.y >= ALTURA) {
            gameOver = true;
            timer.stop();
        }

        // Corpo
        for (int i = 1; i < cobrinha.size(); i++) {
            if (cobrinha.get(i).equals(cabeca)) {
                gameOver = true;
                timer.stop();
            }
        }
    }

    public void gerarComida() {
        int x = (int) (Math.random() * (LARGURA / TAMANHO_BLOCO)) * TAMANHO_BLOCO;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Game Over!", LARGURA / 2 - 50, ALTURA / 2 - 40);
            g.drawString("Pontuação: " + (cobrinha.size() - 1), LARGURA / 2 - 50, ALTURA / 2);
            g.drawString("Pressione R para reiniciar", LARGURA / 2 - 100, ALTURA / 2 + 40);
            g.drawString("Pressione ESC para voltar ao menu", LARGURA / 2 - 130, ALTURA / 2 + 80);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Pontuação: " + (cobrinha.size() - 1), 10, 20);

            g.setColor(Color.GREEN);
            for (Point p : cobrinha) {
                g.fillRect(p.x, p.y, TAMANHO_BLOCO, TAMANHO_BLOCO);
            }

            g.setColor(Color.RED);
            g.fillRect(comida.x, comida.y, TAMANHO_BLOCO, TAMANHO_BLOCO);
        }
    }
}