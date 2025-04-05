import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Snake Game");
        janela.setTitle("Snake Game");
        janela.setResizable(false); // Janela não redimensionável
        janela.setLocation(250, 50); // Posição inicial da janela
        CardLayout layout = new CardLayout(); 
        JPanel container = new JPanel(layout);

        // Painel do menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(Color.BLACK);
        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(200, 50)); // Tamanho do botão
        menuPanel.add(startButton);
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.BLACK);

        // Painel do jogo
        GamePanel gamePanel = new GamePanel();

        // Adiciona os painéis ao container
        container.add(menuPanel, "Menu");
        container.add(gamePanel, "Jogo");

        // Configurações da janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(800, 600);
        janela.add(container);
        janela.setVisible(true);

        // Ação do botão para iniciar o jogo
        startButton.addActionListener(e -> {
            layout.show(container, "Jogo");
            gamePanel.iniciarJogo();
        });
    }
}