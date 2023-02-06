package decompressionPackage;

import org.junit.Test;

import static org.junit.Assert.*;
import generalPackage.Node;

import java.io.*;

public class ImplemenatationClassForDecompressionTest {

    ImplemenatationClassForDecompression d=new ImplemenatationClassForDecompression();
    @Test
    public void TestreturnRootOfTree()
    {
        Node leftNode=new Node();
        leftNode.setVar('a');
        leftNode.setFrequency(6);

        Node rightNode=new Node();
        rightNode.setVar('b');
        rightNode.setFrequency('7');


     Node newNode=new Node();
     newNode.setVar('-');
     newNode.setFrequency(5);
     newNode.setLeft(leftNode);
     newNode.setRight(rightNode);

        ObjectOutputStream op;
        try
        {
            op = new ObjectOutputStream(new FileOutputStream("src/main/java/output.txt"));
            op.writeObject(newNode);
            op.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectInputStream ip;
        try {
        ip=new ObjectInputStream(new FileInputStream("src/main/java/output.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       Node returnedNode=d.returnRootOfTree(ip);

        assertEquals(newNode.getFrequency(),returnedNode.getFrequency());
        assertEquals(leftNode.getVar(),returnedNode.getLeft().getVar());
        assertEquals(rightNode.getVar(),returnedNode.getRight().getVar());

    }

//    @Test(expected = Exception.class)
//    public void TestretunRootofTree1()
//    {  ObjectInputStream ip=null;
//        try {
//        ip=new ObjectInputStream(new FileInputStream("src/main/java/compressed.txt"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Node returnedRoot=d.returnRootOfTree(ip);
//
//    }
}