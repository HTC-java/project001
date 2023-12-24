package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    public static Cell[][] grid;
    StringBuilder sb;

    public Chessboard() {
        sb = new StringBuilder();
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces();
    }

    public void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public boolean pieceMatchRow3(int i, int j) {
        if (grid[i][j].getPiece().getColor().equals(grid[i][j + 1].getPiece().getColor()) && grid[i][j].getPiece().getColor().equals(grid[i][j + 2].getPiece().getColor())) {
            return true;
        } else return false;
    }

    public boolean pieceMatchClo3(int i, int j) {
        if (grid[i][j].getPiece().getColor().equals(grid[i + 1][j].getPiece().getColor()) && grid[i][j].getPiece().getColor().equals(grid[i + 2][j].getPiece().getColor())) {
            return true;
        } else return false;
    }

    public void initPieces() {

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum() - 2; j++) {
                if (pieceMatchRow3(i, j)) {
                    initPieces();
                }
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum() - 2; i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (pieceMatchClo3(i, j)) {
                    initPieces();
                }

            }
        }

    }
    public void updateChessboardFromLines(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String[] tokens = lines.get(i).split(" ");
            for (int j = 0; j < tokens.length; j++) {
                String piece = tokens[j];
                if (piece.equals("💎")) {
                    grid[i][j].setPiece(new ChessPiece("💎"));
                } else if (piece.equals("⚪")) {
                    grid[i][j].setPiece(new ChessPiece("⚪"));
                } else if (piece.equals("▲")) {
                    grid[i][j].setPiece(new ChessPiece("▲"));
                } else if (piece.equals("🔶")) {
                    grid[i][j].setPiece(new ChessPiece("🔶"));
                }
            }
        }
    }


    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }
    public void removeAllChessPiece(){
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                this.removeChessPiece(new ChessboardPoint(i,j));
            }
        }
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        var p1 = getChessPieceAt(point1);
        var p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }

    public boolean canSwap(ChessboardPoint point1, ChessboardPoint point2) {

        int prow1 = point1.getRow();
        int pclo1 = point1.getCol();
        int prow2 = point2.getRow();
        int pclo2 = point2.getCol();
        if (grid[prow1][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
            System.out.println("They are the same, can't swap!");
            return false;
        }
        if (prow2 == prow1 && pclo1 < pclo2) {
            if (prow1 - 2 >= 0 && grid[prow1 - 2][pclo1].getPiece().getColor().equals(grid[prow1 - 1][pclo1].getPiece().getColor()) && grid[prow1 - 2][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (prow1 - 1 >= 0 && prow1 + 1 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow1 - 1][pclo1].getPiece().getColor().equals(grid[prow1 + 1][pclo1].getPiece().getColor()) && grid[prow1 - 1][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (prow1 + 2 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow1 + 2][pclo1].getPiece().getColor().equals(grid[prow1 + 1][pclo1].getPiece().getColor()) && grid[prow1 + 2][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (pclo1 - 2 >= 0 && grid[prow1][pclo1 - 2].getPiece().getColor().equals(grid[prow1][pclo1 - 1].getPiece().getColor()) && grid[prow1][pclo1 - 2].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (prow2 - 2 >= 0 && grid[prow2 - 2][pclo2].getPiece().getColor().equals(grid[prow2 - 1][pclo2].getPiece().getColor()) && grid[prow2 - 2][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (prow2 - 1 >= 0 && prow2 + 1 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow2 - 1][pclo2].getPiece().getColor().equals(grid[prow2 + 1][pclo2].getPiece().getColor()) && grid[prow2 - 1][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (prow2 + 2 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow2 + 2][pclo2].getPiece().getColor().equals(grid[prow2 + 1][pclo2].getPiece().getColor()) && grid[prow2 + 2][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (pclo2 + 2 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow2][pclo2 + 2].getPiece().getColor().equals(grid[prow2][pclo2 + 1].getPiece().getColor()) && grid[prow2][pclo2 + 2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else {
                System.out.println("Can't swap!");
                return false;
            }
        } else if (prow2 == prow1 && pclo1 > pclo2) {
            if (prow1 - 2 >= 0 && grid[prow1 - 2][pclo1].getPiece().getColor().equals(grid[prow1 - 1][pclo1].getPiece().getColor()) && grid[prow1 - 2][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (prow1 - 1 >= 0 && prow1 + 1 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow1 - 1][pclo1].getPiece().getColor().equals(grid[prow1 + 1][pclo1].getPiece().getColor()) && grid[prow1 - 1][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (prow1 + 2 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow1 + 2][pclo1].getPiece().getColor().equals(grid[prow1 + 1][pclo1].getPiece().getColor()) && grid[prow1 + 2][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (pclo1 + 2 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow1][pclo1 + 2].getPiece().getColor().equals(grid[prow1][pclo1 + 1].getPiece().getColor()) && grid[prow1][pclo1 + 2].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (prow2 - 2 >= 0 && grid[prow2 - 2][pclo2].getPiece().getColor().equals(grid[prow2 - 1][pclo2].getPiece().getColor()) && grid[prow2 - 2][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (prow2 - 1 >= 0 && prow2 + 1 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow2 - 1][pclo2].getPiece().getColor().equals(grid[prow2 + 1][pclo2].getPiece().getColor()) && grid[prow2 - 1][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (prow2 + 2 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow2 + 2][pclo2].getPiece().getColor().equals(grid[prow2 + 1][pclo2].getPiece().getColor()) && grid[prow2 + 2][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (pclo2 - 2 >= 0 && grid[prow2][pclo2 - 2].getPiece().getColor().equals(grid[prow2][pclo2 - 1].getPiece().getColor()) && grid[prow2][pclo2 - 2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else {
                System.out.println("Can't swap!");
                return false;
            }
        } else if (pclo2 == pclo1 && prow1 < prow2) {
            if (prow1 - 2 >= 0 && grid[prow1 - 2][pclo1].getPiece().getColor().equals(grid[prow1 - 1][pclo1].getPiece().getColor()) && grid[prow1 - 2][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (pclo1 - 2 >= 0 && grid[prow1][pclo1 - 2].getPiece().getColor().equals(grid[prow1][pclo1 - 1].getPiece().getColor()) && grid[prow1][pclo1 - 2].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (pclo1 - 1 >= 0 && pclo1 + 1 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow1][pclo1 - 1].getPiece().getColor().equals(grid[prow1][pclo1 + 1].getPiece().getColor()) && grid[prow1][pclo1 - 1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (pclo1 + 2 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow1][pclo1 + 2].getPiece().getColor().equals(grid[prow1][pclo1 + 1].getPiece().getColor()) && grid[prow1][pclo1 + 2].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (prow2 + 2 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow2 + 2][pclo2].getPiece().getColor().equals(grid[prow2 + 1][pclo2].getPiece().getColor()) && grid[prow2 + 2][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (pclo2 - 2 >= 0 && grid[prow2][pclo2 - 2].getPiece().getColor().equals(grid[prow2][pclo2 - 1].getPiece().getColor()) && grid[prow2][pclo2 - 2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (pclo2 - 1 >= 0 && pclo2 + 1 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow2][pclo2 - 1].getPiece().getColor().equals(grid[prow2][pclo2 + 1].getPiece().getColor()) && grid[prow2][pclo2 - 1].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (pclo2 + 2 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow2][pclo2 + 2].getPiece().getColor().equals(grid[prow2][pclo2 + 1].getPiece().getColor()) && grid[prow2][pclo2 + 2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else {
                System.out.println("Can't swap!");
                return false;
            }
        } else if (pclo2 == pclo1 && prow1 > prow2) {
            if (prow1 + 2 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow1 + 2][pclo1].getPiece().getColor().equals(grid[prow1 + 1][pclo1].getPiece().getColor()) && grid[prow1 + 2][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (pclo1 - 2 >= 0 && grid[prow1][pclo1 - 2].getPiece().getColor().equals(grid[prow1][pclo1 - 1].getPiece().getColor()) && grid[prow1][pclo1 - 2].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (pclo1 - 1 >= 0 && pclo1 + 1 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow1][pclo1 - 1].getPiece().getColor().equals(grid[prow1][pclo1 + 1].getPiece().getColor()) && grid[prow1][pclo1 - 1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (pclo1 + 2 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow1][pclo1 + 2].getPiece().getColor().equals(grid[prow1][pclo1 + 1].getPiece().getColor()) && grid[prow1][pclo1 + 2].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
                return true;
            } else if (prow2 - 2 >= 0 && grid[prow2 - 2][pclo2].getPiece().getColor().equals(grid[prow2 - 1][pclo2].getPiece().getColor()) && grid[prow2 - 2][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (pclo2 - 2 >= 0 && grid[prow2][pclo2 - 2].getPiece().getColor().equals(grid[prow2][pclo2 - 1].getPiece().getColor()) && grid[prow2][pclo2 - 2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (pclo2 - 1 >= 0 && pclo2 + 1 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow2][pclo2 - 1].getPiece().getColor().equals(grid[prow2][pclo2 + 1].getPiece().getColor()) && grid[prow2][pclo2 - 1].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else if (pclo2 + 2 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow2][pclo2 + 2].getPiece().getColor().equals(grid[prow2][pclo2 + 1].getPiece().getColor()) && grid[prow2][pclo2 + 2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
                return true;
            } else {
                System.out.println("Can't swap!");
                return false;
            }
        } else {
            System.out.println("Can't swap!");
            return false;
        }
    }
        /*
        if (prow1 - 2 >= 0 && grid[prow1 - 2][pclo1].getPiece().getColor().equals(grid[prow1 - 1][pclo1].getPiece().getColor()) && grid[prow1 - 2][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
            return true;
        } else if (prow1 - 1 >= 0 && prow1 + 1 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow1 - 1][pclo1].getPiece().getColor().equals(grid[prow1 + 1][pclo1].getPiece().getColor()) && grid[prow1 - 1][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
            return true;
        } else if (prow1 + 2 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow1 + 2][pclo1].getPiece().getColor().equals(grid[prow1 + 1][pclo1].getPiece().getColor()) && grid[prow1 + 2][pclo1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
            return true;
        } else if (pclo1 - 2 >= 0 && grid[prow1][pclo1 - 2].getPiece().getColor().equals(grid[prow1][pclo1 - 1].getPiece().getColor()) && grid[prow1][pclo1 - 2].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
            return true;
        } else if (pclo1 - 1 >= 0 && pclo1 + 1 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow1][pclo1 - 1].getPiece().getColor().equals(grid[prow1][pclo1 + 1].getPiece().getColor()) && grid[prow1][pclo1 - 1].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
            return true;
        } else if (pclo1 + 2 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow1][pclo1 + 2].getPiece().getColor().equals(grid[prow1][pclo1 + 1].getPiece().getColor()) && grid[prow1][pclo1 + 2].getPiece().getColor().equals(grid[prow2][pclo2].getPiece().getColor())) {
            return true;
        } else if (prow2 - 2 >= 0 && grid[prow2 - 2][pclo2].getPiece().getColor().equals(grid[prow2 - 1][pclo2].getPiece().getColor()) && grid[prow2 - 2][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
            return true;
        } else if (prow2 - 1 >= 0 && prow2 + 1 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow2 - 1][pclo2].getPiece().getColor().equals(grid[prow2 + 1][pclo2].getPiece().getColor()) && grid[prow2 - 1][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
            return true;
        } else if (prow2 + 2 < Constant.CHESSBOARD_ROW_SIZE.getNum() && grid[prow2 + 2][pclo2].getPiece().getColor().equals(grid[prow2 + 1][pclo2].getPiece().getColor()) && grid[prow2 + 2][pclo2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
            return true;
        } else if (pclo2 - 2 >= 0 && grid[prow2][pclo2 - 2].getPiece().getColor().equals(grid[prow2][pclo2 - 1].getPiece().getColor()) && grid[prow2][pclo2 - 2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
            return true;
        } else if (pclo2 - 1 >= 0 && pclo2 + 1 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow2][pclo2 - 1].getPiece().getColor().equals(grid[prow2][pclo2 + 1].getPiece().getColor()) && grid[prow2][pclo2 - 1].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
            return true;
        } else if (pclo2 + 2 < Constant.CHESSBOARD_COL_SIZE.getNum() && grid[prow2][pclo2 + 2].getPiece().getColor().equals(grid[prow2][pclo2 + 1].getPiece().getColor()) && grid[prow2][pclo2 + 2].getPiece().getColor().equals(grid[prow1][pclo1].getPiece().getColor())) {
            return true;
        }
         */


    public Cell[][] getGrid() {
        return grid;
    }


    public List<String> converBoardToList() {
        List<String> saveLines = new ArrayList<>();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            sb.setLength(0);
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessPiece piece = grid[i][j].getPiece();
                if (piece != null) {
                    sb.append(piece.getName()).append(" ");
                } else {
                    sb.append("0").append(" ");
                }
            }
            saveLines.add(sb.toString());
        }
        sb.setLength(0);
        return saveLines;
    }
}
