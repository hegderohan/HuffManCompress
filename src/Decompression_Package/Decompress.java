package Decompression_Package;

import General_Package.Node;

import java.io.ObjectInputStream;
import java.util.ArrayList;

public interface Decompress
{
    int measurestartTime();
    Node returnRootOfTree(ObjectInputStream ip);
    int returnNoofZeros(ObjectInputStream ip);
    String returnString(ObjectInputStream ip);
    String getCodedStringBack(String compressedString);
    ArrayList<Integer> get7bitCode(int val);
    Node goLeftorRightAndReturnNode(Node root,char val);
    void getFinalAns(String decompressedFilePath, String decoded, Node root);
    String removeAppendedZeros(String decoded, int noOfZerosAppended);
    void measureEndTime(int startTime);
}
