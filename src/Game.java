import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Objects;
import java.util.Scanner;

public class Game {
    private final String[] moves;
    private final int numOfMoves;
    private int programMoveIndex;
    private String Hmac;
    private String Hmac_key;

    public String getWinner(int playerMoveIndex) {
        if (playerMoveIndex > programMoveIndex) {
            if ((playerMoveIndex - programMoveIndex) > numOfMoves / 2) {
                return "You lose(";
            }
            else {
                return "You win!";
            }
        }
        else if (playerMoveIndex < programMoveIndex) {
            if ((programMoveIndex - playerMoveIndex) > numOfMoves / 2) {
                return "You win!";
            }
            else {
                return "You lose(";
            }
        }
        else {
            return "Draw";
        }
    }

    public void runMenu() throws NoSuchAlgorithmException, InvalidKeyException {
        while (true) {
            runKeyGeneration();
            System.out.println("HMAC: " + getHmac());
            String ch = menu();
            int moveNum;
            switch (ch) {
                case "0":
                    return;
                case "?":
                    System.out.println("\nRules:");
                    TableGenerator tableGenerator = new TableGenerator();
                    tableGenerator.showTable(getMoves());
                    System.out.println("");
                    continue;
                default:
                    try {
                        moveNum = Integer.parseInt(ch);
                        if (moveNum < 1 || moveNum > numOfMoves) {
                            throw new NumberFormatException();
                        }
                        System.out.println("Your move: " + getMoveByIndex(moveNum - 1));
                        System.out.println("Computer move: " + getMoveByIndex(programMoveIndex));
                        System.out.println(getWinner(moveNum - 1));
                        System.out.println("HMAC-key: " + getHmac_key());
                        System.out.println("\n\tNew game");
                    }
                    catch (NumberFormatException ignored) {}
            }
        }
    }

    public String menu() {
        String ch;
        for (int i = 0; i < moves.length; i++) {
            System.out.println(i + 1 + " - " + moves[i]);
        }
        System.out.println("0 - exit");
        System.out.println("? - help");
        System.out.print("Enter your move: ");
        Scanner scanner = new Scanner(System.in);
        ch = scanner.nextLine();
        return ch;
    }

    public String getHmac_key() {
        return Hmac_key;
    }

    public void setHmac_key(String hmac_key) {
        Hmac_key = hmac_key;
    }

    public String getHmac() {
        return Hmac;
    }

    public void setHmac(String hmac) {
        Hmac = hmac;
    }

    public int getProgramMoveIndex() {
        return programMoveIndex;
    }

    public void setProgramMoveIndex(int programMoveIndex) {
        this.programMoveIndex = programMoveIndex;
    }

    public int getNumOfMoves() {
        return numOfMoves;
    }

    public String getMoveByIndex(int num) {
        return moves[num];
    }

    public String[] getMoves() {
        return moves;
    }

    Game(String[] moves) {
        this.moves = moves;
        numOfMoves = moves.length;
    }

    public void runKeyGeneration() throws NoSuchAlgorithmException, InvalidKeyException {
        KeyGenerator keyGenerator = new KeyGenerator();
        this.setProgramMoveIndex(keyGenerator.generateMove(this.getNumOfMoves()));
        HMAC_generator hmac_generator = new HMAC_generator();
        byte[] key = keyGenerator.generateKey();
        this.setHmac_key(this.bytesToHex(key));
        this.setHmac(this.bytesToHex(hmac_generator.getHmac(this.getMoveByIndex(this.getProgramMoveIndex()), key)));
    }

    public String bytesToHex(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public boolean isValuesUnique(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (Objects.equals(arr[i], arr[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        if (args.length < 3 || args.length % 2 != 1) {
            System.out.println("Error! Number of arguments must be odd and greater than two.");
            System.out.println("Example:\njava -jar Task3_game.jar rock paper scissors");
            return;
        }
        Game game = new Game(args);
        if (!game.isValuesUnique(args)) {
            System.out.println("Error! Arguments are not unique. All arguments must have different names.");
            System.out.println("Example:\njava -jar Task3_game.jar rock paper scissors");
            return;
        }
        game.runMenu();
    }
}
