package org.example;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    // ==== ATTRIBUTES ====
    private List<Block> blocks;
    private int chainSize;

    // ==== CONSTRUCTOR ====
    /**
     * Initializes an empty list for blocks and sets chainSize to 0
     */
    public BlockChain() {
        blocks = new ArrayList<>();
        chainSize = 0;
    }

    // ==== GETTERS AND SETTERS ====
    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public int getChainSize() {
        return chainSize;
    }

    public void setChainSize(int chainSize) {
        this.chainSize = chainSize;
    }

    // ==== METHODS ====
    /**
     * Adds a new block to the chain and updates chainSize
     */
    public void addBlock(Block newBlock) {
        blocks.add(newBlock);
        chainSize++;
    }

    /**
     * Goes through every block in the chain and verifies
     * their hash values and their previous hash values
     */
    public boolean verify() {
        boolean isGenesisBlock = true;
        String prevHash = "";
        String currentHash;
        boolean flag;
        for (Block block : blocks) {
            // Generating current block's hash value
            currentHash = block.getPrevHash() + block.getData()
                    + block.getTimeStamp() + block.getPow() + block.getPrefix();
            try {

                // --------------------------------------------------------
                // I GOT THE FOLLOWING CODE FROM CHATGPT BECAUSE I COULDN'T
                // FIGURE OUT HOW TO MAKE THE HASH BINARY
                // --------------------------------------------------------
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                // Compute the hash and get the byte array
                byte[] hashBytes = md.digest(currentHash.getBytes());

                // Convert each byte to an 8-bit binary string
                StringBuilder binaryString = new StringBuilder();
                for (byte b : hashBytes) {
                    binaryString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
                }

                currentHash =  binaryString.toString();
                // --------------------------------------------------------
                // END OF CHATGPT CODE
                // --------------------------------------------------------

            } catch (Exception e) {
                System.out.println("Could not generate hash");
                System.exit(1);
            }

            // Checking generated hash vs. block's stored hash
            if (!currentHash.equals(block.getHash())) {
                return false;
            }

            // Checks previous hash if block is not the genesis block
            if (isGenesisBlock) {
                isGenesisBlock = false;
            } else {
                if (!prevHash.equals(block.getPrevHash())) {
                    return false;
                }
            }
            prevHash = currentHash;

            // Checking to see if hash was generated properly
            flag = true;
            for (int i = 0; i < block.getPrefix(); i++) {
                if (block.getHash().charAt(i) == '1') {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a string containing the size of the chain and
     * information about every block in the chain.
     */
    @Override
    public String toString() {
        String output = "Chain Size: " + chainSize + "\n";
        int count = 1;
        for (Block block : blocks) {
            output += "\nBlock " + count + ":\n" + block.toString();
            count++;
        }
        return output;
    }
}
