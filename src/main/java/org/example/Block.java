package org.example;

import java.security.MessageDigest;

public class Block {

    // ===== ATTRIBUTES =====
    // The hash of the current block
    private String hash;
    // The hash of the previous block
    private String prevHash;
    // Data recorded in the block
    private String data;
    // The time when the block was created
    private long timeStamp;
    // The proof of work of the block
    private int pow;
    // Number of required leading zeroes
    private int prefix;


    // ==== CONSTRUCTOR ====
    /**
     * Initializes prevHash, data, timeStamp, and prefix, then runs
     * mineBlock to generate a proof of work and a hash value
     */
    public Block(String prevHash, String data, long timeStamp, int prefix) {
        this.prevHash = prevHash;
        this.data = data;
        this.timeStamp = timeStamp;
        this.prefix = prefix;
        hash = mineBlock();
    }


    // ==== GETTERS AND SETTERS ====
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getPow() {
        return pow;
    }

    public void setPow(int pow) {
        this.pow = pow;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }


    // ==== METHODS ====
    /**
     * Combines all data in the block, then
     * generates the hash value for that data
     * @return the created hash value
     */
    public String generateBlockHash() {
        String concatenatedData = prevHash + data + timeStamp + pow + prefix;
        String hash = null;
        try {

            // --------------------------------------------------------
            // I GOT THE FOLLOWING CODE FROM CHATGPT BECAUSE I COULDN'T
            // FIGURE OUT HOW TO MAKE THE HASH BINARY
            // --------------------------------------------------------
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Compute the hash and get the byte array
            byte[] hashBytes = md.digest(concatenatedData.getBytes());

            // Convert each byte to an 8-bit binary string
            StringBuilder binaryString = new StringBuilder();
            for (byte b : hashBytes) {
                binaryString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }

            return binaryString.toString();
            // --------------------------------------------------------
            // END OF CHATGPT CODE
            // --------------------------------------------------------

        } catch (Exception e) {
            System.out.println("Could not generate hash");
            System.exit(1);
        }

        System.out.println(hash);
        return hash;
    }

    /**
     * Increases the proof of work and runs
     * generateBlockHash repeatedly until a
     * valid proof of work is found
     */
    public String mineBlock() {
        pow = 0;
        String hash;
        char[] hashArray;
        boolean flag;
        while (true) {
            hash = generateBlockHash();
            hashArray = hash.toCharArray();
            flag = true;

            // Checks if hash has the correct amount of leading zeroes
            for (int i = 0; i < prefix; i++) {
                if (hashArray[i] == '1') {
                    flag = false;
                    break;
                }
            }

            // If the hash has the correct amount of leading zeros,
            // return the hash, otherwise increase pow
            if (flag) {
                return hash;
            } else {
                pow++;
            }
        }
    }

    /**
     * Returns a string containing information about the
     * block (data, prefix, prevHash, and hash)
     */
    @Override
    public String toString() {
        return "Data: " + data + "\n"
                + "Prefix: " + prefix + "\n"
                + "Previous block's hash: " + prevHash + "\n"
                + "Current block's hash: " + hash + "\n";
    }
}
