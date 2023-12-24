package controller;

import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;



    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    private int score;
    private int step;

    private JLabel statusLabel;
    private JLabel statusLabel1;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;

        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void initialize() {
        model.initPieces();
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
        this.score = 0;
        this.step = 0;
        this.statusLabel.setText("Score:" + score + "   Step:" + step);
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
        // TODO: Init your swap function here.--done!
        System.out.println("Implement your swap here.");
        if (selectedPoint != null && selectedPoint2 != null) {
            if (model.canSwap(selectedPoint, selectedPoint2)) {
                model.swapChessPiece(selectedPoint, selectedPoint2);
                ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint);
                ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint2);
                view.setChessComponentAtGrid(selectedPoint, chess2);
                view.setChessComponentAtGrid(selectedPoint2, chess1);
                chess1.repaint();
                chess2.repaint();
                selectedPoint = null;
                selectedPoint2 = null;
            }

        }
    }

    public void addScore(List<ChessboardPoint> samePoints) {
        for (int i = 0; i < samePoints.size(); i++) {
            Color color = model.getGridAt(samePoints.get(i)).getPiece().getColor();
            if (color.equals(Color.blue)) {
                score += 5;
            } else if (color.equals(Color.white)) {
                score += 3;
            } else if (color.equals(Color.green)) {
                score += 2;
            } else {
                score += 1;
            }
        }
    }

    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here.
        System.out.println("Implement your next step here.");
        List<ChessboardPoint> sameRowPoints = new ArrayList<>();
        List<ChessboardPoint> sameCloPoints = new ArrayList<>();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum() - 2; j++) {
                if (model.getGrid() != null && model.pieceMatchRow3(i, j)) {
                    sameRowPoints.add(new ChessboardPoint(i, j));
                }
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum() - 2; i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (model.pieceMatchClo3(i, j)) {
                    sameCloPoints.add(new ChessboardPoint(i, j));
                }

            }
        }
        addScore(sameRowPoints);
        addScore(sameCloPoints);
        if (sameRowPoints.size() >= 1) {
            for (int i = 0; i < sameRowPoints.size(); i++) {
                for (int j = 0; j < 3; j++) {
                    ChessboardPoint point = new ChessboardPoint(sameRowPoints.get(i).getRow(), sameRowPoints.get(i).getCol() + j);
                    if (model.getChessPieceAt(point) != null) {
                        model.removeChessPiece(point);
                        view.removeChessComponentAtGrid(point);
                    }
                }
            }
        }

        if (sameCloPoints.size() >= 1) {
            for (int i = 0; i < sameCloPoints.size(); i++) {
                for (int j = 0; j < 3; j++) {
                    ChessboardPoint point = new ChessboardPoint(sameCloPoints.get(i).getRow() + j, sameCloPoints.get(i).getCol());
                    if (model.getChessPieceAt(point) != null) {
                        model.removeChessPiece(point);
                        view.removeChessComponentAtGrid(point);
                    }
                }
            }
        }
        for (int k = 0; k < 8; k++) {
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessboardPoint point1 = new ChessboardPoint(i, j);
                    ChessboardPoint point2 = new ChessboardPoint(i + 1, j);
                    if (model.getChessPieceAt(point2) == null && model.getChessPieceAt(point1) != null) {
                        model.swapChessPiece(point1, point2);
                        ChessComponent chess1 = view.removeChessComponentAtGrid(point1);
                        //ChessComponent chess2 =view.removeChessComponentAtGrid(point2);
                        //view.setChessComponentAtGrid(point1,chess2);
                        view.setChessComponentAtGrid(point2, chess1);
                        chess1.repaint();
                        //chess2.repaint();
                    }
                }
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (model.getChessPieceAt(point) == null) {
                    model.setChessPiece(point, new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
                }
            }
        }


        view.initiateChessComponent(model);
        view.repaint();
        step++;
        this.statusLabel.setText("Score:" + score + "   Step:" + step);

    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }


        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }


    }

    public void saveGameFromFile(String path) {
        List<String> saveLines = model.converBoardToList();
        for (String line : saveLines
        ) {
            System.out.println(line);
        }
        try {
            Files.write(Path.of(path), saveLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGameFromFile(String path) {
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            if (lines.size() != Chessboard.grid.length) {
                throw new IllegalArgumentException("Invalid file format");
            }
            // 解析文件内容并更新棋盘状态
            model.updateChessboardFromLines(lines);
            // 更新视图
            score = 0;
            step = 0;
            view.removeAllChessComponentsAtGrids();
            view.initiateChessComponent(model);
            view.repaint();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
