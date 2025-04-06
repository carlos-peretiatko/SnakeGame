import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Snake Game");
        janela.setResizable(false);
        janela.setLocation(250, 50); // Centraliza a janela
        CardLayout layout = new CardLayout();
        JPanel container = new JPanel(layout);
        ImageIcon icon = new ImageIcon("assents/iconSnake.png");// nao esta funcionando -> ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/assets/iconSnake.png");
        janela.setIconImage(icon.getImage());

        // Painel do menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(Color.BLACK);

        // Configuração do layout dos botões
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Título do jogo
        JLabel titleLabel = new JLabel("Snake Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.GREEN);
        gbc.insets = new Insets(0, 0, 50, 0);
        menuPanel.add(titleLabel, gbc);

        // Botões do menu
        JButton startButton = new JButton("Start Game");
        JButton helpButton = new JButton("Help");
        JButton exitButton = new JButton("Exit");

        // Configuração dos botões
        for (JButton button : new JButton[]{startButton, helpButton, exitButton}) {
            button.setPreferredSize(new Dimension(200, 50));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            gbc.insets = new Insets(10, 0, 10, 0);
            menuPanel.add(button, gbc);
        }

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

        // Ações dos botões
        startButton.addActionListener(e -> {
            layout.show(container, "Jogo");
            gamePanel.requestFocus();
            gamePanel.iniciarJogo();
        });

        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(janela,
            "Use as setas para controlar a cobra.\n" +
            "Coma as maçãs vermelhas para crescer.\n" +
            "Evite as paredes e seu próprio corpo!\n" +
            "Pressione ESC para voltar ao menu durante o jogo.",
            "Como Jogar",
            JOptionPane.INFORMATION_MESSAGE));

        exitButton.addActionListener(e -> System.exit(0));

        // Adicionar KeyListener para voltar ao menu
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override //sobrepoe 
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    layout.show(container, "Menu");
                }
            }
        });
    }
}