/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merkeltree.project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 *This class handles the routine for creation and testing of Merkel Tree specifications.
 * @author Anshu Anand
 */
public class Main {

    private static final String targetHash ="A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58";
    
    
    /**
     * This is a static void main method , which is used to test the Merkel Tree for various files.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
        
        //NOTE :- File name with CrimeLatLonXY.csv has a Merkel root with hash value ,       
        // which is equal to A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58 .
        
        //get the file nam einput from user
        System.out.println("NOTE :- CrimeLatLonXY.csv has Merkel root equal to Traget hash of A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58");
        System.out.println("Enter the File Name :");

        Scanner userInput = new Scanner(System.in);
        String inputLine = userInput.nextLine();

        //instantiate the Merkel tree process for given file name
        MerkelTreeProject mk1 = new MerkelTreeProject();
        try {
            //read file line by line
            mk1.readFile("Data/" + inputLine);
            //initiate the process of creation
            mk1.initiateMerkelTree();
            //get the Merkel Root level hash value.
            String MerkelRoot = mk1.getRootHashValue();
            //print the output
            System.out.println("Hash value of Root Node: - " + MerkelRoot);
            
            //Match the output for traget hash value.
            if(MerkelRoot.equals(targetHash)){
              System.out.println("Selected file "+inputLine +" has Merkel root equal to Traget hash of A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58");  
            }
        } 
        catch (Exception e) {
            //Handle exceptions.
            System.out.println("Exception occured during operation");
        }

              
    }
}
