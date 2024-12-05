package io.github.yuizho.security;

import java.security.*;
import java.util.HexFormat;
import java.util.Scanner;

public class DigitalSignatureExample {
    public static final String Ed25519 = "Ed25519";

    public static byte[] generateDigitalSignature(byte[] plainText, PrivateKey privateKey) throws Exception  {
        Signature signature = Signature.getInstance(Ed25519);
        signature.initSign(privateKey);
        signature.update(plainText);
        return signature.sign();

    }

    public static boolean verifyDigitalSignature(byte[] plainText, byte[] digitalSignature, PublicKey publicKey)
            throws Exception {
        Signature signature = Signature.getInstance(Ed25519);
        signature.initVerify(publicKey);
        signature.update(plainText);
        return signature.verify(digitalSignature);
    }

    public static void main(String[] args) throws Exception {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("Ed25519");
        KeyPair keypair = keyPairGenerator.generateKeyPair();

        Scanner message = new Scanner(System.in);
        System.out.print("Enter the message you want to encrypt using Ed25519: ");
        String plainText = message.nextLine();
        byte[] bytes = plainText.getBytes();
        message.close();

        byte[] digitalSignature = generateDigitalSignature(bytes, keypair.getPrivate());

        System.out.println("Signature Value:\n " + HexFormat.of().formatHex(digitalSignature));
        System.out.println("Verification: " + verifyDigitalSignature(bytes, digitalSignature, keypair.getPublic()));

    }
}

