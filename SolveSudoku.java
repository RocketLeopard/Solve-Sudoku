public class SolveSudoku {

    public static void main(String[] args) {

        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        print(board);

        Solution solution = new Solution();
        solution.solveSudoku(board);

        print(board);
    }

    static void print(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + ",");
            }

            System.out.println();
        }

        System.out.println("\n");
    }
}

class Solution {
    int n = 3;
    int N = n * n;

    boolean[][] rows = new boolean[N][N];
    boolean[][] cols = new boolean[N][N];
    boolean[][] boxes = new boolean[N][N];

    char[][] board;

    boolean sudokuSolved = false;

    private boolean canPlace(int num, int row, int col) {
        int box = (row / n) * n + col / n;
        int index = num - 1;
        return !rows[row][index] && !cols[col][index] && !boxes[box][index];
    }

    private void placeNum(int num, int row, int col) {
        int box = (row / n) * n + col / n;
        int index = num - 1;
        rows[row][index] = true;
        cols[col][index] = true;
        boxes[box][index] = true;

        board[row][col] = (char) (num + '0');
    }

    private void placeNextNum(int row, int col) {
        if (row == N - 1 && col == N - 1) {
            sudokuSolved = true;
        } else if (col == N - 1) {
            backtrack(row + 1, 0);
        } else {
            backtrack(row, col + 1);
        }
    }

    private void removeNum(int num, int row, int col) {
        int box = (row / n) * n + col / n;
        int index = num - 1;
        rows[row][index] = false;
        cols[col][index] = false;
        boxes[box][index] = false;

        board[row][col] = '.';
    }

    private void backtrack(int row, int col) {
        if (row >= N || col >= N) {
            return;
        }

        if (board[row][col] == '.') {
            for (int d = 1; d <= 9; d++) {
                if (canPlace(d, row, col)) {
                    placeNum(d, row, col);
                    placeNextNum(row, col);
                    if (!sudokuSolved) {
                        removeNum(d, row, col);
                    }
                }
            }
        } else {
            placeNextNum(row, col);
        }
    }

    public void solveSudoku(char[][] board) {
        this.board = board;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                char num = board[i][j];
                if (num != '.') {
                    int d = Character.getNumericValue(num);
                    placeNum(d, i, j);
                }
            }
        }

        backtrack(0, 0);
    }
}
