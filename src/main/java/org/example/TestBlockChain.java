package org.example;

import java.util.Scanner;

public class TestBlockChain {
    public static void main(String[] args) {
//        // Creating blockchain and blocks
//        BlockChain bc = new BlockChain();
//        Block genesis = new Block(null, "Genesis block", System.currentTimeMillis(), 4);
//        Block b1 = new Block(genesis.getHash(), "block-1", System.currentTimeMillis(), 4);
//        Block b2 = new Block(b1.getHash(), "block-2", System.currentTimeMillis(), 8);
//        Block b3 = new Block(b2.getHash(), "block-3", System.currentTimeMillis(), 12);
//
//        // Adding blocks to blockchain
//        bc.addBlock(genesis);
//        bc.addBlock(b1);
//        bc.addBlock(b2);
//        bc.addBlock(b3);
//
//        // Verifying and printing blockchain data
//        if (bc.verify()) {
//            System.out.println("Chain verified");
//        } else {
//            System.out.println("Invalid chain");
//        }
//        System.out.println(bc);

        // Creating base blockchain
        BlockChain bc = new BlockChain();

        System.out.println("""
                =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                Welcome to the Blockchain Simulator!
                =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                """);

        // Variables to store user input and block information
        String input;
        String prevHash = null;
        String data;
        int prefix  = 0;
        boolean prefixAssigned;
        Scanner in = new Scanner(System.in);
        Block block;

        // Main program loop
        do {

            System.out.println("""
                    --------------------------------------------
                    'add' - Adds a block to the chain
                    'verify' - Verifies the hashes in the chain
                    'print' - Prints chain and block information
                    'reset' - Resets the chain to be empty
                    'exit' - Exits the program
                    --------------------------------------------""");
            System.out.print("Enter one of the options from the menu: ");
            input = in.nextLine().trim().toLowerCase();

            switch (input) {
                case "add":
                    System.out.print("Enter the data to be contained in the block: ");
                    data = in.nextLine().trim();

                    // Checking to make sure user enters a valid prefix
                    prefixAssigned = false;
                    do {
                        System.out.print("Enter the prefix for the hash (# of leading zeroes): ");
                        if (in.hasNextInt()) {
                            prefix = in.nextInt();
                            if (prefix < 0) {
                                System.out.println("Prefix cannot be a negative number, try again");
                            } else {
                                prefixAssigned = true;
                            }
                        } else {
                            System.out.println("Prefix must be an integer, try again");
                        }
                        in.nextLine(); // Clears Scanner buffer
                    } while(!prefixAssigned);

                    // Creating and adding block
                    System.out.println("Adding block...");
                    block = new Block(prevHash, data, System.currentTimeMillis(), prefix);
                    bc.addBlock(block);

                    // Storing hash value for the next block to be added
                    prevHash = block.getHash();

                    System.out.println("Block added successfully!");
                    System.out.print("Press enter to continue\n");
                    in.nextLine();
                    break;

                case "verify":
                    if (bc.verify()) {
                        System.out.println("Chain verified!");
                    } else {
                        System.out.println("Verification failed, chain is invalid");
                    }
                    System.out.print("Press enter to continue\n");
                    in.nextLine();
                    break;

                case "print":
                    System.out.println("\n" + bc);
                    System.out.print("Press enter to continue\n");
                    in.nextLine();
                    break;

                case "reset":
                    bc = new BlockChain();
                    System.out.println("Blockchain reset!");
                    System.out.print("Press enter to continue\n");
                    in.nextLine();
                    break;

                case "exit":
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid command, please use a command from the menu");
                    System.out.print("Press enter to continue\n");
                    in.nextLine();
                    break;
            }

        } while (!input.equals("exit"));

        System.exit(0);
    }
}
