package aol_codere;

/**
 * Handles all display/rendering for a Board (divergent-change fix).
 * Owns printBoard() and getCell() so that UI changes are isolated here.
 */
public class BoardRenderer {
    private final Board board;

    BoardRenderer(Board board) {
        this.board = board;
    }

    void printBoard() {
        for (int i = board.side - 1; i >= 0; i--) {
            System.out.print(i + " ");
            for (int j = 0; j < board.side; j++) {
                System.out.print(getCell(new Point(j, i)));
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int i = 0; i < board.side; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    String getCell(Point p) {
        for (Ship s : board.enemyShips) {
            if (s.isDeadAtPoint(p)) return "X ";
            if (s.isLiveAtPoint(p)) return "~ ";
        }
        if (board.isMiss(p)) return ". ";
        return "~ ";
    }
}
