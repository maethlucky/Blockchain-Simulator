package org.example;

public class TestBlockChain {
    public static void main(String[] args) {
        // Creating blockchain and blocks
        BlockChain bc = new BlockChain();
        Block genesis = new Block(null, "Genesis block", System.currentTimeMillis(), 4);
        Block b1 = new Block(genesis.getHash(), "block-1", System.currentTimeMillis(), 4);
        Block b2 = new Block(b1.getHash(), "block-2", System.currentTimeMillis(), 8);
        Block b3 = new Block(b2.getHash(), "block-3", System.currentTimeMillis(), 12);

        // Adding blocks to blockchain
        bc.addBlock(genesis);
        bc.addBlock(b1);
        bc.addBlock(b2);
        bc.addBlock(b3);

        // Verifying and printing blockchain data
        if (bc.verify()) {
            System.out.println("Chain verified");
        } else {
            System.out.println("Invalid chain");
        }
        System.out.println(bc);
    }
}
