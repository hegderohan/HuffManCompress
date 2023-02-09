package decompressionPackage;

import generalPackage.Node;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public interface Decompress
{

    Node returnRootOfTree(ObjectInputStream ip);
    int returnNoofZeros(ObjectInputStream ip) throws IOException;
    ArrayList<Integer> get8bitcode(int val) throws RuntimeException;
    Node goLeftorRightAndReturnNode(Node root,char val);
    void getFinal(Node root,ObjectInputStream in,int no_of_zeros);

}
