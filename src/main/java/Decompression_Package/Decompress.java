package Decompression_Package;

import General_Package.Node;

import java.io.ObjectInputStream;
import java.util.ArrayList;

public interface Decompress
{
    int measurestartTime();
    Node returnRootOfTree(ObjectInputStream ip);
    int returnNoofZeros(ObjectInputStream ip);
    ArrayList<Integer> get8bitcode(int val);
    Node goLeftorRightAndReturnNode(Node root,char val);

    ArrayList<Integer> getArrayList(ObjectInputStream in);
    void getFinal(Node root,ArrayList<Integer> arr,int no_of_zeros);
    void measureEndTime(int startTime);
}
