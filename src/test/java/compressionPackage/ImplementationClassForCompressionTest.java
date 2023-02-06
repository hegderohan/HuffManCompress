package compressionPackage;

import generalPackage.Node;
import org.junit.Test;
//import static org.junit.Assert.assertThrows;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class ImplementationClassForCompressionTest {
    ImplementationClassForCompression c=new ImplementationClassForCompression();
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
        FileReader f1;
        try
        {
            f1 = new FileReader("src/test/java/compressionPackage/test1.txt");
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        Map<Character,Integer> actualMap=c.calculateFreq(f1);
        Map<Character,Integer> expectedMap=new HashMap<>();
        expectedMap.put('a',2);
        expectedMap.put('b',3);
        expectedMap.put('c',1);
        assertEquals(expectedMap,actualMap);
    }

    @Test
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
        Map<Character,Integer> actualMap=new HashMap<>();
        Map<Character,Integer> expectedMap=c.calculateFreq(fileReader);
        assertEquals(actualMap,expectedMap);
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
        Map<Character,Integer> expectedMap=c.calculateFreq(fileReader);

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

    @Test
    public void TestgetCodes()
    {
      String path="src/test/java/compressionPackage/inputFileforgetCodes.txt";
      Map<Character,String> HuffManMap=new HashMap<>();
      HuffManMap.put('c',"100");
      HuffManMap.put('d',"101");
      HuffManMap.put('a',"11");
      HuffManMap.put('b',"0");
     StringBuilder returnedString= c.getCodes(path,HuffManMap);
     assertEquals(returnedString.toString(),"11110000100101");
    }



}