package decompressionPackage;

import generalPackage.GeneralClass;
import generalPackage.Path;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import generalPackage.Node;

import java.io.*;
import java.util.ArrayList;

public class ImplemenatationClassForDecompressionTest {
    Node root=null;
    @Before
    public void beforeTest()
    {
        Node leftNode=new Node();
        leftNode.setVar('a');
        leftNode.setFrequency(6);
        leftNode.setLeft(null);
        leftNode.setRight(null);

        Node rightNode=new Node();
        rightNode.setVar('b');
        rightNode.setFrequency(7);
        rightNode.setRight(null);
        rightNode.setLeft(null);

        Node rootNode=new Node();
        rootNode.setFrequency(leftNode.getFrequency()+ rightNode.getFrequency());
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);

        root=rootNode;
    }



    ImplemenatationClassForDecompression d=new ImplemenatationClassForDecompression();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public  void WriteZeros()
    {
        File f=new File("src/test/java/decompressionPackage/Zeros.txt");
        ObjectOutputStream ut=null;
        try {
            ut=new ObjectOutputStream(new FileOutputStream(f));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        try {
            ut.writeInt(2);
            ut.flush();
            ut.close();
            ObjectInputStream in=new ObjectInputStream(new FileInputStream("src/test/java/decompressionPackage/Zeros.txt"));
            int y= d.returnNoofZeros(in);
            assertEquals(2,y);

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        new File("src/test/java/decompressionPackage/Zeros.txt").delete();
    }
    @Test
   public void TestToreturnNoofZeros() throws IOException {
        ObjectOutputStream op=null;

            op =new ObjectOutputStream(new FileOutputStream("src/test/java/decompressionPackage/inpu1.txt"));

            op.writeInt(2);
        op.flush();
            op.close();

            ObjectInputStream ip=new ObjectInputStream(new FileInputStream("src/test/java/decompressionPackage/inpu1.txt"));
            int x=ip.readInt();
            assertEquals(x,2);
            ip.close();

            new File("src/test/java/decompressionPackage/inpu1.txt").delete();
    }


    @Test
    public void TestreturnRoot()
    {
        Node leftNode=new Node();
        leftNode.setVar('a');
        leftNode.setFrequency(6);
        leftNode.setLeft(null);
        leftNode.setRight(null);

        Node rightNode=new Node();
        rightNode.setVar('b');
        rightNode.setFrequency(7);
        rightNode.setRight(null);
        rightNode.setLeft(null);

        Node rootNode=new Node();
        rootNode.setFrequency(leftNode.getFrequency()+ rightNode.getFrequency());
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);

        root=rootNode;


        ObjectOutputStream op=null;
        try {
           op=new ObjectOutputStream(new FileOutputStream("src/test/java/decompressionPackage/input2.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            op.writeObject(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            op.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            op.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        ObjectInputStream ip=null;
        try {
           ip=new ObjectInputStream(new FileInputStream("src/test/java/decompressionPackage/input2.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Node root=(Node)ip.readObject();
            assertEquals(root.getLeft().getVar(),'a');
            assertEquals(root.getRight().getVar(),'b');
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        new File("src/test/java/decompressionPackage/input2.txt").delete();

    }
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
            op = new ObjectOutputStream(new FileOutputStream("src/test/java/decompressionPackage/output.txt"));
            op.writeObject(newNode);
            op.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectInputStream ip;
        try {
        ip=new ObjectInputStream(new FileInputStream("src/test/java/decompressionPackage/output.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       Node returnedNode=d.returnRootOfTree(ip);

        assertEquals(newNode.getFrequency(),returnedNode.getFrequency());
        assertEquals(leftNode.getVar(),returnedNode.getLeft().getVar());
        assertEquals(rightNode.getVar(),returnedNode.getRight().getVar());
        new File("src/test/java/decompressionPackage/output.txt").delete();
    }

  @Test
    public void Testget8BItCode()
  {

      ArrayList<Integer> expectedArray=new ArrayList<>();
      for(int i=0;i<8;i++)
      {
          expectedArray.add(0);
      }
      ArrayList<Integer> returnedArray= null;
      try {
          returnedArray = d.get8bitcode(0);
      } catch (RuntimeException e) {
          throw new RuntimeException(e);
      }
      assertTrue(expectedArray.equals(returnedArray));
  }


    @Test
    public void Testget8BitCode1()
    {
        ArrayList<Integer> expectedArray=new ArrayList<>();
        for(int i=0;i<6;i++)
        {
            expectedArray.add(0);
        }
        expectedArray.add(1);
        expectedArray.add(0);
        ArrayList<Integer> returnedArray= null;
        try {
            returnedArray = d.get8bitcode(2);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        assertTrue(expectedArray.equals(returnedArray));

    }

    @Test(expected = RuntimeException.class)
    public void Test8BitCode2()
    {
        ArrayList<Integer> expectedArray=new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            expectedArray.add(0);
        }
        try {
            ArrayList<Integer> returnedArray=d.get8bitcode(-2);
        } catch(RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Test(expected = RuntimeException.class)
    public void TestreturnNoOfZerosWhenFileIsEmpty()
    {
        File f=new File("src/test/java/decompressionPackage/inpu1.txt");
        try {
            ObjectInputStream in= new ObjectInputStream(new FileInputStream(f));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Test
//    public void TestreturnNoOfZeros()
//    {
//        ObjectInputStream in= null;
//        try {
//            in = new ObjectInputStream(new FileInputStream("src/test/java/decompressionPackage/input2.txt"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        int noOfZeros=0;
//        try
//        {
//           noOfZeros=d.returnNoofZeros(in);
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }
//        assertEquals(noOfZeros,2);
//    }

    @Test
    public void TestgoLeftOrRight()
    {
        Node leftNode=new Node();
        leftNode.setVar('a');
        leftNode.setFrequency(7);
        leftNode.setLeft(null);
        leftNode.setRight(null);

        Node rightNode=new Node();
        rightNode.setVar('b');
        rightNode.setFrequency(5);
        rightNode.setLeft(null);
        rightNode.setRight(null);

        Node parentNode=new Node();
        parentNode.setVar('-');
        parentNode.setFrequency(leftNode.getFrequency()+rightNode.getFrequency());
        parentNode.setLeft(leftNode);
        parentNode.setRight(rightNode);

       assertEquals(leftNode.getVar(),d.goLeftorRightAndReturnNode(parentNode,'0').getVar());
       assertEquals(rightNode.getVar(),d.goLeftorRightAndReturnNode(parentNode,'1').getVar());

    }

    @Test
    public void TestgetFinalAns()
    {
        ObjectInputStream in=null;
        try {
           in=new ObjectInputStream(new FileInputStream("src/test/java/decompressionPackage/sampleOutput.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Node root=null;
        int noOfZeros=0;
        try {
            root=(Node)in.readObject();
           noOfZeros=in.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        d.getFinal(root,in,noOfZeros);

        try {
            assertTrue(GeneralClass.check("src/test/java/decompressionPackage/sample.txt",Path.decompressedFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void TestreturnNoOfRoots()
    {
        ObjectOutputStream op;
        try {
          op=new ObjectOutputStream(new FileOutputStream("src/test/java/decompressionPackage/nodeText.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String ans="rohan";
        try
        {
            op.writeObject(ans);
            op.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        try
        {
            ObjectInputStream in=new ObjectInputStream(new FileInputStream("src/test/java/decompressionPackage/nodeText.txt"));
            d.returnRootOfTree(in);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}