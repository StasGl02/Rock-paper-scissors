import java.security.SecureRandom;

public class KeyGenerator {
    public byte[] generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return bytes;
    }

    public int generateMove(int numOfArgs) {
        SecureRandom random = new SecureRandom();
        return (random.nextInt(numOfArgs));
    }
}
