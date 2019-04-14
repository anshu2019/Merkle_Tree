/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merkeltree.project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is for creating Merkel tree
 *
 * @author Anshu Anand
 */
public class MerkelTreeProject {

    //string to hold root node value
    private String rootNodeHash = "";
    //this holds list 1 with all the lines of text from file, for each line one node
    private SinglyLinkedList list1;
    //this holds hashed value for each node on list 1
    private SinglyLinkedList list2;
    //this is final linked list tree , which contains all 
    //the linked list from bottom to top layers
    private SinglyLinkedList finalTree;
    //track the tree levels
    private int treeLevel = 0;

    /**
     * This is the constructor for Merkel tree
     */
    public MerkelTreeProject() {
        list1 = new SinglyLinkedList();
        list2 = new SinglyLinkedList();
        finalTree = new SinglyLinkedList();
    }

    /**
     * This method creates hash value for string value passed
     *
     * @param text
     * @return string
     * @throws NoSuchAlgorithmException
     */
    public static String h(String text) throws
            NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash
                = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        //return the hash value
        return sb.toString();
    }

    /**
     * This method check the count in the list , if it is odd , adds the last
     * node again at the end to make the list size even
     * @return SinglyLinkedList
     */
    public SinglyLinkedList finalizeNodes(SinglyLinkedList list) {
        //get the size of list
        int nodeNo = list.CountNodes();
        //if list is bigger than 1
        if (nodeNo > 1) {
            //is teh list even
            if (nodeNo % 2 == 1) {
                //add lst node again at teh end to increse size of list
                list.addAtEndNode(list.getLast());
            }
        }
        //return list
        return list;
    }

    /**
     * This method initiates the Merkel Tree
     *
     * @throws NoSuchAlgorithmException
     */
    public void initiateMerkelTree() throws NoSuchAlgorithmException {
        //create a list
        SinglyLinkedList finalList = new SinglyLinkedList();
        //finalize the list , with adding extra nodes if required
        finalList = finalizeNodes(list2);
        //get the node count
        int nodeNo = finalList.CountNodes();
        //if list got count greater than zero

        if (finalList.CountNodes() > 0) {
            treeLevel = treeLevel + 1;
            finalTree.addAtEndNode(list1);
            System.out.println("no of nodes at level " + treeLevel + " is " + list1.CountNodes());

            //initiate the Merkel Tree..
            createMerkelTree(finalList);

        }
    }

    /**
     *This method converts the lsit into a Merkel tree
     * @param list
     * @throws NoSuchAlgorithmException
     */
    public void createMerkelTree(SinglyLinkedList list) throws NoSuchAlgorithmException {
        SinglyLinkedList s = new SinglyLinkedList();
        //add new list to Main list
        finalTree.addAtEndNode(list);
        //add tree level at this point
        treeLevel = treeLevel + 1;
        System.out.println("no of nodes at level " + treeLevel + " is " + list.CountNodes());
        // iterate for each node on the list
        for (int i = 0; i < list.CountNodes(); i++) {
            if (i != list.CountNodes() - 1) {
                //get i node
                ObjectNode n1 = list.getObjectAt(i);
                String hash1 = (String) n1.getData();
                //get i+1 node
                ObjectNode n2 = list.getObjectAt(i + 1);
                String hash2 = (String) n2.getData();
                //combine the hash value of both nodes
                String combinedHash = hash1 + hash2;
                // generate the new hash value on combined hash value
                String nwHash = h(combinedHash);
                //add the hash to list
                s.addAtEndNode(nwHash);
                // increment the lsit count
                i = i + 1;
            }
        }

        //recursion
        if (s.CountNodes() > 1) {
            //if list got more than one node, means list has not yet reached root level
            // again call the createMerkelTree method to create new list above
            createMerkelTree(finalizeNodes(s));
        } else if (s.CountNodes() == 1) {
            // if list has one node , mean it is root node
            finalTree.addAtEndNode(s);
            // add tree level
            treeLevel = treeLevel + 1;
            System.out.println("no of nodes at level " + treeLevel + " is " + s.CountNodes());
            Object aa = s.getLast();
            // get the value of Merkel root hash
            rootNodeHash = aa.toString();
        } else {

        }

    }

    /**
     *This method exposes hash value for Merkle Root
     * @return String 
     */
    public String getRootHashValue() {
        return rootNodeHash;
    }

    /**
     *Expose tree level in Merkle tree
     * @return integer
     */
    public int getTreeLevel() {
        return treeLevel;
    }

    /**
     *This method reads the file.
     * @param path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readFile(String path) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        // Open the file
        FileInputStream fstream = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        //Read File Line By Line
        while ((strLine = br.readLine()) != null) {
            list1.addAtEndNode(strLine);
            String hash = h(strLine);
            list2.addAtEndNode(hash);
        }
        System.out.println(list1.CountNodes());
        System.out.println(list2.CountNodes());
        //Close the input stream
        br.close();
    }

}
