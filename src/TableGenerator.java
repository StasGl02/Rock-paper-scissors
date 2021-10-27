import com.bethecoder.ascii_table.ASCIITable;

public class TableGenerator {
    public void showTable(String[] moves) {
        String[] header = new String[moves.length + 1];
        header[0] = "User/PC";
        System.arraycopy(moves, 0, header, 1, moves.length);
        String[][] data = new String[moves.length][moves.length + 1];
        data[0][0] = header[1];
        for (int j = 1; j <= moves.length; j++) {
            if (j == 1) {
                data[0][j] = "Draw";
            }
            else if (j <= moves.length / 2 + 1) {
                data[0][j] = "Lose";
            }
            else {
                data[0][j] = "Win";
            }
        }
        for (int i = 1; i < moves.length; i++) {
            data[i][0] = header[i + 1];
            data[i][1] = data[i - 1][moves.length];
            System.arraycopy(data[i - 1], 1, data[i], 2, moves.length - 1);
        }
        ASCIITable.getInstance().printTable(header, data);
    }
}