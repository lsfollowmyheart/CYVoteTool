package util;

/**
 * @Autre beyond
 * @Data 2019/3/17
 */
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

public class RSAUtil {


        public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

        /**
         * RSA最大加密明文大小
         */
        private static final int MAX_ENCRYPT_BLOCK = 117;

        /**
         * RSA最大解密密文大小
         */
        private static final int MAX_DECRYPT_BLOCK = 128;


        public static String sign(String content, String input_charset, Key key)
                throws UnsupportedEncodingException, Exception {
            Cipher cipher;
            try {
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] output = cipher.doFinal(content.getBytes(input_charset));
                return Base64.encode(output);
            } catch (NoSuchAlgorithmException e) {
                throw new Exception("无此加密算法");
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
                return null;
            } catch (InvalidKeyException e) {
                throw new Exception("加密公钥非法,请检查");
            } catch (IllegalBlockSizeException e) {
                throw new Exception("明文长度非法");
            } catch (BadPaddingException e) {
                throw new Exception("明文数据已损坏");
            }
        }

        public static String readFile(String filePath, String charSet) throws Exception {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            try {
                FileChannel fileChannel = fileInputStream.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
                fileChannel.read(byteBuffer);
                byteBuffer.flip();
                return new String(byteBuffer.array(), charSet);
            } finally {
                fileInputStream.close();
            }

        }

        public static String getKey(String string) throws Exception {
            String content = readFile(string, "UTF8");
            return content.replaceAll("\\-{5}[\\w\\s]+\\-{5}[\\r\\n|\\n]", "");
        }

        public static boolean verifyByKeyPath(String content, String sign, String publicKeyPath, String input_charset) {
            try {
                return verifyByPublicKey(content, sign, getKey(publicKeyPath), input_charset);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }


        public static KeyInfo getPFXPrivateKey(String pfxPath, String password)
                throws KeyStoreException, NoSuchAlgorithmException,
                CertificateException, IOException, UnrecoverableKeyException {
            FileInputStream fis = new FileInputStream(pfxPath);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, password.toCharArray());
            fis.close();
            Enumeration<String> enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements())// we are readin just one certificate.
            {
                keyAlias = enumas.nextElement();
            }

            KeyInfo keyInfo = new KeyInfo();

            PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, password.toCharArray());
            Certificate cert = ks.getCertificate(keyAlias);
            PublicKey pubkey = cert.getPublicKey();

            keyInfo.privateKey = prikey;
            keyInfo.publicKey = pubkey;
            return keyInfo;
        }

        public static class KeyInfo {

            PublicKey publicKey;
            PrivateKey privateKey;

            public PublicKey getPublicKey() {
                return publicKey;
            }

            public PrivateKey getPrivateKey() {
                return privateKey;
            }
        }

        // =====================获取公钥私钥===============================//


        //得到公钥
        public static PublicKey getPublicKey(String key) throws Exception {
            if (key == null) {
                throw new Exception("加密公钥为空, 请设置");
            }
            byte[] buffer = Base64.decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        }

        /**
         * 得到私钥
         *
         * @param key 密钥字符串（经过base64编码）
         * @throws Exception
         */
        public static PrivateKey getPrivateKey(String key) throws Exception {
            byte[] keyBytes = buildPKCS8Key(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        }

        /**
         * 获取公钥
         *
         * @param filename
         * @return
         * @throws Exception
         */
        public static PublicKey getPublicKeyFile(String filename) throws Exception {
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            dis.close();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        }

        /**
         * 获取私钥
         *
         * @param filename
         * @return
         * @throws Exception
         */
        public static PrivateKey getPrivateKeyFile(String filename) throws Exception {
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            dis.close();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        }

        private static byte[] buildPKCS8Key(String privateKey) throws IOException {
            if (privateKey.contains("-----BEGIN PRIVATE KEY-----")) {
                return Base64.decode(privateKey.replaceAll("-----\\w+ PRIVATE KEY-----", ""));
            } else if (privateKey.contains("-----BEGIN RSA PRIVATE KEY-----")) {
                final byte[] innerKey = Base64.decode(privateKey.replaceAll("-----\\w+ RSA PRIVATE KEY-----", ""));
                final byte[] result = new byte[innerKey.length + 26];
                System.arraycopy(Base64.decode("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKY="), 0, result, 0, 26);
                System.arraycopy(BigInteger.valueOf(result.length - 4).toByteArray(), 0, result, 2, 2);
                System.arraycopy(BigInteger.valueOf(innerKey.length).toByteArray(), 0, result, 24, 2);
                System.arraycopy(innerKey, 0, result, 26, innerKey.length);
                return result;
            } else {
                return Base64.decode(privateKey);
            }
        }


        // ==========================秘钥加解密================================//

        //私钥加密 调用方法
        public static String signByPrivate(String content, String privateKey,
                                           String input_charset) throws Exception {
            if (privateKey == null) {
                throw new Exception("加密私钥为空, 请设置");
            }
            PrivateKey privateKeyInfo = getPrivateKey(privateKey);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKeyInfo);
            signature.update(content.getBytes(input_charset));
            return Base64.encode(signature.sign());
        }

        /**
         * RSA验签名检查
         *
         * @param content       待签名数据
         * @param sign          签名值
         * @param publicKey     支付宝公钥
         * @param input_charset 编码格式
         * @return 布尔值
         *///使用私钥加密 公钥解密验证 调用方法
        public static boolean verifyByPublicKey(String content, String sign, String publicKey, String input_charset) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                byte[] encodedKey = Base64.decode(publicKey);
                PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
                java.security.Signature signature = java.security.Signature
                        .getInstance(SIGN_ALGORITHMS);
                signature.initVerify(pubKey);
                signature.update(content.getBytes(input_charset));
                boolean bverify = signature.verify(Base64.decode(sign));
                return bverify;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;

        }

        /**
         * <P>
         * 私钥解密
         * </p>
         *
         * @param encryptedData 已加密数据
         * @param privateKey    私钥(BASE64编码)
         * @return
         * @throws Exception
         */
        public static String decryptByPrivateKey(String encryptedData, String privateKey) throws Exception {

            byte[] encrypt = Base64.decode(encryptedData);//转换加密数据类型转换
            PrivateKey privateK = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            int inputLen = encrypt.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encrypt, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encrypt, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData);
        }

        /**
         * <p>
         * 公钥加密
         * </p>
         *
         * @param data      源数据
         * @param publicKey 公钥(BASE64编码)
         * @return
         * @throws Exception
         */
        public static String encryptByPublicKey(String data, String publicKey)  {
            byte[] encryptDate = new byte[0];
            Cipher cipher = null;
            try {
                encryptDate = data.getBytes("UTF-8");
                cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
                int inputLen = encryptDate.length;
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int offSet = 0;
                byte[] cache;
                int i = 0;
                // 对数据分段加密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                        cache = cipher.doFinal(encryptDate, offSet, MAX_ENCRYPT_BLOCK);
                    } else {
                        cache = cipher.doFinal(encryptDate, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * MAX_ENCRYPT_BLOCK;
                }
                byte[] encryptedData = out.toByteArray();
                out.close();
                return Base64.encode(encryptedData);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
            catch (NoSuchAlgorithmException e) {
                return null;
            } catch (NoSuchPaddingException e) {
                return null;
            }
            catch (Exception e) {
                return null;
            }


        }

        /**
         * 公钥加密，私钥验证
         * @param content       未加密的数据
         * @param sign          加密后的数据
         * @param privateKey    私钥
         * @return
         * @throws Exception
         */
        public static boolean verifyByPrivateKey(String content, String sign, String privateKey) throws Exception {
            String data = decryptByPrivateKey(sign, privateKey);
            if (content.equals(data)) {
                return true;
            }
            return false;
        }
    }

    class Base64 {
        private static char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

        private static byte[] base64DecodeChars = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55,
                56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
                47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

        private Base64() {
        }

        public static synchronized String encode(byte[] data) {
            int len = data.length;
            int i = 0;
            int b1, b2, b3;
            StringBuilder sb = new StringBuilder(len);

            while (i < len) {
                b1 = data[i++] & 0xff;
                if (i == len) {
                    sb.append(base64EncodeChars[b1 >>> 2]);
                    sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                    sb.append("==");
                    break;
                }
                b2 = data[i++] & 0xff;
                if (i == len) {
                    sb.append(base64EncodeChars[b1 >>> 2]);
                    sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                    sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                    sb.append("=");
                    break;
                }
                b3 = data[i++] & 0xff;
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
                sb.append(base64EncodeChars[b3 & 0x3f]);
            }
            return sb.toString();
        }

        public static synchronized byte[] decode(String str) {
            byte[] data = str.getBytes();
            int len = data.length;
            ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
            int i = 0;
            int b1, b2, b3, b4;

            while (i < len) {

                /* b1 */
                do {
                    b1 = base64DecodeChars[data[i++]];
                }
                while (i < len && b1 == -1);
                if (b1 == -1) {
                    break;
                }

                /* b2 */
                do {
                    b2 = base64DecodeChars[data[i++]];
                }
                while (i < len && b2 == -1);
                if (b2 == -1) {
                    break;
                }
                buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

                /* b3 */
                do {
                    b3 = data[i++];
                    if (b3 == 61) {
                        return buf.toByteArray();
                    }
                    b3 = base64DecodeChars[b3];
                }
                while (i < len && b3 == -1);
                if (b3 == -1) {
                    break;
                }
                buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

                /* b4 */
                do {
                    b4 = data[i++];
                    if (b4 == 61) {
                        return buf.toByteArray();
                    }
                    b4 = base64DecodeChars[b4];
                }
                while (i < len && b4 == -1);
                if (b4 == -1) {
                    break;
                }
                buf.write((int) (((b3 & 0x03) << 6) | b4));
            }
            return buf.toByteArray();
        }


    }

