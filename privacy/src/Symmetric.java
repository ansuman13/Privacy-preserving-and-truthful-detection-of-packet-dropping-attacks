import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Symmetric {
    private String AlphaNum = "A1B2C%D3EF)GH5IJ4K6L9MN7OP0QRS8TUVWXYZa!bc@de$fg#hij&k*l(m-n_op+q{r][st}u:v;w,x.?";
    
    /**
     * Encrypt the text using `SecretKey`
     * @param originalText
     * @param SecretKey
     * @return String
     */
    public String symmetricEncryption(String originalText, String SecretKey){       
        byte[] raw;
        String encryptedString = null;
        BASE64Decoder decoder = new BASE64Decoder();
        BASE64Encoder bASE64Encoder = new BASE64Encoder();
        SecretKeySpec skeySpec;
        Cipher cipher;

        if (originalText != null && SecretKey != null) {
            try {
                byte[] encryptText = originalText.getBytes();
                raw = decoder.decodeBuffer(SecretKey);
                skeySpec = new SecretKeySpec(raw, "AES");
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
                encryptedString = bASE64Encoder.encode(cipher.doFinal(encryptText));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        return encryptedString;
    }
    
    /**
     * Decrypt the encrypted text using same `SecretKey`
     * @param Encryptedtext
     * @param SecretKey
     * @return String
     */
    public String symmetricDecryption(String Encryptedtext, String SecretKey){
        String decryptedString = null;
        byte[] encryptText;
        byte[] raw;
        BASE64Decoder decoder = new BASE64Decoder();
        BASE64Decoder base64Decoder = new BASE64Decoder();
        Cipher cipher;
        SecretKeySpec skeySpec;

        if (Encryptedtext != null && SecretKey != null) {
            try {
                raw = decoder.decodeBuffer(SecretKey);
                skeySpec = new SecretKeySpec(raw, "AES");
                encryptText = base64Decoder.decodeBuffer(Encryptedtext);
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
                decryptedString = new String(cipher.doFinal(encryptText));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return decryptedString;
    }
    
    /**
     * Generate random text/string of 200 characters
     * @return String
     */
    public String generateData() {
        String Randomline = new String();
        Random rand = new Random();
        char tokenChar = '\000';
        for (int i = 0; i < 200; i++) {
            tokenChar = AlphaNum.charAt(rand.nextInt(AlphaNum.length()));
            Randomline = Randomline + tokenChar;
        }
        return Randomline;
    }
    
     /**
     * Generate Secret Key using javax.crypto.KeyGenerator class
     * @return String
     */
     public String getSecretKey() {
        KeyGenerator keyGen;
        String strSecretkey = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretkey = keyGen.generateKey();
            BASE64Encoder encode = new BASE64Encoder();
            strSecretkey = encode.encode(secretkey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strSecretkey;
    }
    
    public static void main(String[] args) {
        Symmetric sTest = new Symmetric();
        String SecretKey = sTest.getSecretKey();
        String OriginalText = sTest.generateData();
        
        /**
         * You are adviced to check/validate null values before 
         * using this piece of code for real time application 
         */
        System.out.println("Secret Key:"+SecretKey);
        System.out.println("Original Data:"+OriginalText);
        
        String encrypt = sTest.symmetricEncryption(OriginalText,SecretKey);
        System.out.println("Encrypted text:"+encrypt);
        
        String decrypt = sTest.symmetricDecryption(encrypt,SecretKey);
        System.out.println("Decrypted text:"+decrypt);
    }
}