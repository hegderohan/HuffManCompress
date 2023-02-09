package compressionPackage;
import generalPackage.Node;
import generalPackage.Path;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;


public class ImplementationClassForCompressionTest {
    ImplementationClassForCompression c=new ImplementationClassForCompression();

    StringBuilder coded=new StringBuilder("1010101110111100");
    Node root=null;

    int no_of_zeros=1;

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
    @Test
    public void TestZerosToBeappended()
    {
        StringBuilder str=new StringBuilder("00010");
        assertEquals(3,c.noofZerosToBeAppended(str));
    }
    @Test
    public void TestZerosToBeappended2()
    {
        StringBuilder str=new StringBuilder("");
        assertEquals(0,c.noofZerosToBeAppended(str));
    }
    @Test
    public void TestZerosToBeappended3()
    {
        StringBuilder str=new StringBuilder("0001");
        assertEquals(4,c.noofZerosToBeAppended(str));
    }
    @Test
    public void TestZerosToBeappended4()
    {
        StringBuilder str=new StringBuilder("0001000");
        assertEquals(1,c.noofZerosToBeAppended(str));
    }
    @Test
    public void TestZerosToBeappended5()
    {
        StringBuilder str=new StringBuilder("00010000");
        assertEquals(0,c.noofZerosToBeAppended(str));
    }

    @Test
    public void TestForFrequencyMap()
    {
        FileReader fileReaderFortheSameFile;
        FileWriter fileWriterForInputFile=null;
        try
        {
            fileWriterForInputFile=new FileWriter("src/test/java/compressionPackage/test1.txt");
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fileWriterForInputFile.write("aabbbc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fileWriterForInputFile.flush();
            fileWriterForInputFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try
        {
            fileReaderFortheSameFile = new FileReader("src/test/java/compressionPackage/test1.txt");
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        Map<Character,Integer> actualMap= null;
        try {
            actualMap = c.calculateFreq(fileReaderFortheSameFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Character,Integer> expectedMap=new HashMap<>();
        expectedMap.put('a',2);
        expectedMap.put('b',3);
        expectedMap.put('c',1);
        assertEquals(expectedMap,actualMap);
        new File("src/test/java/compressionPackage/test1.txt").delete();
    }

    @Test(expected = RuntimeException.class)
    public void TestFrequencyMap1()
    {
        FileReader fileReader;
        try
        {
            fileReader = new FileReader("src/test/java/compressionPackage/empty.txt");
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
//
//        Map<Character,Integer> actualMap=new HashMap<>();
//        Map<Character,Integer> expectedMap= null;
//        try {
//            expectedMap = c.calculateFreq(fileReader);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        assertEquals(actualMap,expectedMap);
      new File("src/test/java/compressionPackage/empty.txt").delete();
    }


    @Test(expected = RuntimeException.class)
    public void TestFrequencyMap2()
    {
        FileReader fileReader = null;
        try
        {
         fileReader=new FileReader("src/test/java/compressionPackage/invalid.txt");
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
        try
        {
            Map<Character,Integer> expectedMap=c.calculateFreq(fileReader);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void TestAddElementsintoQueueAndReturnRoot()
    {
        Map<Character,Integer> InputMap=new HashMap<>();

        InputMap.put('a',7);
        InputMap.put('b',4);
        InputMap.put('c',7);
        InputMap.put('d',3);
        InputMap.put('e',8);
        InputMap.put('f',5);

        Node returnedNode=c.addElementIntoQueueAndReturnRoot(InputMap);

       assertEquals(returnedNode.getFrequency(),34);
       assertEquals(returnedNode.getLeft().getFrequency(),14);
       assertEquals(returnedNode.getRight().getFrequency(),20);
    }

    @Test
    public void TestAddElementsintoQueueAndReturnRoot1()
    {
        Map<Character,Integer> InputMap=new HashMap<>();
        InputMap.put('a',7);
        Node returnedNode=c.addElementIntoQueueAndReturnRoot(InputMap);
        assertEquals(returnedNode.getLeft().getVar(),'a');
        assertEquals(returnedNode.getLeft().getFrequency(),7);

    }



    @Test
    public void TestappendREianingZeros2()
    {
       StringBuilder inputString=new StringBuilder("00001");
       StringBuilder returnedString=c.appendRemainingZeros(inputString);
       assertEquals("00001000",returnedString.toString());
    }


    @Test
    public void TestappendREianingZeros1()
    {
        StringBuilder inputString=new StringBuilder("10101100");
        StringBuilder returnedString=c.appendRemainingZeros(inputString);
        assertEquals("10101100",returnedString.toString());
    }

    @Test
    public void TestappendREianingZeros3()
    {
        StringBuilder inputString=new StringBuilder("");
        StringBuilder returnedString=c.appendRemainingZeros(inputString);
        assertEquals("",returnedString.toString());
    }




    @Test(expected = RuntimeException.class)
    public void testReadFile()
    {
        String path="invalidpath.txt";
       FileReader returnedFileReader= c.readFile(path);
    }

     @Test(expected = RuntimeException.class)
     public void TestFrequencymap() {
         FileReader f1;
         try {
             f1 = new FileReader("invalid.txt");
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
         try {
             Map<Character, Integer> frequecyMap = c.calculateFreq(f1);
         } catch (IOException e) {
             throw new RuntimeException(e);
         }

     }

    @Test(expected = RuntimeException.class)
    public void testReadFile1()
    {
        String path="src/test/java/compressionPackage/empty.txt";
        FileReader returnedFileReader=c.readFile(path);
    }


   @Test
   public void TestCreateHuffManMap()
   {
       Map<Character,String> paraMap=new HashMap<>();
       c.iterateTreeAndCalculateHuffManCode(root,"",paraMap);
       boolean flag=true;
       Map<Character,String> expectedMap=new HashMap<>();
       expectedMap.put('a',"0");
       expectedMap.put('b',"1");
      assertTrue(paraMap.equals(expectedMap));
   }

    @Test
    public void TestCompressMethod()
    {
        try {
            c.compress(coded,root,no_of_zeros);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectInputStream in=null;
        try {
           in =new ObjectInputStream(new FileInputStream(Path.compressedFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Node root=(Node)in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(root.getLeft().getFrequency());

    }

   @Test
    public void testgetCodes()
   {
       FileWriter f2;
       try {
           f2=new FileWriter("src/test/java/compressionPackage/input.txt");
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       try {
           f2.write("ROHAN");
           f2.flush();
           f2.close();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       Map<Character,String> huffmanMap=new HashMap<>();

       huffmanMap.put('R',"10");
       huffmanMap.put('O',"111");
       huffmanMap.put('H',"01");
       huffmanMap.put('A',"00");
       huffmanMap.put('N',"110");

       StringBuilder coded=new StringBuilder();
       try {
          coded=c.getCodes("src/test/java/compressionPackage/input.txt",huffmanMap);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       System.out.println(coded);
       String c=coded.toString();
       assertTrue(c.equals("101110100110"));
       new File("src/test/java/compressionPackage/input.txt").delete();
   }


   @Test
    public void TestCompress()
   {
       StringBuilder coded=new StringBuilder("0101001001111000");
       int noofZeros=3;
       try {
           c.compress(coded,root,noofZeros);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

       ObjectInputStream ip=null;
       try {
           ip=new ObjectInputStream(new FileInputStream(Path.compressedFilePath));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       try {
           Node root=(Node)ip.readObject();
           int x=ip.readInt();
           byte[] arr=(byte[])ip.readObject();
//          for(byte b:arr)
//          {
//              System.out.println(b);
//          }
          boolean flag=true;

           byte[] arr1={82,120};
          assertTrue("length must be same",arr.length==arr1.length);
          for(int i=0;i<arr.length;i++)
          {
              if(arr1[i]!=arr[i])
              {
                  flag=false;
                  break;
              }
          }
          assertTrue(flag);
       } catch (IOException e) {
           throw new RuntimeException(e);
       } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       }

   }

}