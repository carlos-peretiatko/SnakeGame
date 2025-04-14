import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Snake Game");
        janela.setResizable(false);
        janela.setLocation(250, 50);

        CardLayout layout = new CardLayout();
        JPanel container = new JPanel(layout);

        // Ícone corrigido
        try {
            ImageIcon icon = new ImageIcon("project snake/assents/iconSnake.png");
            janela.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("Ícone não encontrado.");
        }

        // Painel de menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titleLabel = new JLabel("Snake Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.GREEN);
        gbc.insets = new Insets(0, 0, 50, 0);
        menuPanel.add(titleLabel, gbc);

        JButton startButton = new JButton("Start Game");
        JButton helpButton = new JButton("Help");
        JButton exitButton = new JButton("Exit");

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
        GamePanel gamePanel = new GamePanel(layout, container);

        container.add(menuPanel, "Menu");
        container.add(gamePanel, "Jogo");

        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(800, 600);
        janela.add(container);
        janela.setVisible(true);

        // Ações dos botões
        startButton.addActionListener(e -> {
            layout.show(container, "Jogo");
            SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
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
    }
}
