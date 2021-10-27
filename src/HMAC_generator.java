import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMAC_generator {
    private static final String HMAC_ALG = "HmacSHA3-256";
    public byte[] getHmac(String move, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, HMAC_ALG);
        Mac mac = Mac.getInstance(HMAC_ALG);
        mac.init(secretKeySpec);
        return mac.doFinal(move.getBytes());
    }
}
