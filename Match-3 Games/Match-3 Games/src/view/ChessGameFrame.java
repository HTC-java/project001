package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    private JComboBox<String> levelComboBox;

    private final int ONE_CHESS_SIZE;

    private GameController gameController;

    private ChessboardComponent chessboardComponent;

    private JLabel statusLabel;

    public ChessGameFrame(int width, int height) {
        setTitle("蓝靖淇&邱志煜荣耀出品"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        levelComboBox = new JComboBox<>();
        levelComboBox.setLocation(HEIGTH, HEIGTH / 10 + 600);
        levelComboBox.setSize(200, 60);
        levelComboBox.setFont(new Font("Rockwell", Font.BOLD, 20));
        String[] levels = {"Level 1", "Level 2", "Level 3"};
        setLevelOptions(levels);
        add(levelComboBox);



        addChessboard();
        addLabel();
        addHelloButton();
        addSwapConfirmButton();
        addNextStepButton();
        addLoadButton();
        addRestartButton();
        addSaveButton();
    }
    public void setLevelOptions(String[] levels) {
        levelComboBox.setModel(new DefaultComboBoxModel<>(levels));
    }





    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        this.statusLabel = new JLabel(" ");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Hello");
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Show hello world");
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> chessboardComponent.swapChess());
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> chessboardComponent.nextStep());
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            System.out.println(path);
            gameController.loadGameFromFile(path);
            //addChessboard();
        });
    }


    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 440);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            System.out.println(path);
            gameController.saveGameFromFile(path);
        });
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "The game has been restart!");
            gameController.initialize();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 520);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


}
