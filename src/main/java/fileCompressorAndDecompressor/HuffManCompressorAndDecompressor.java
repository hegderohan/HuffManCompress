package fileCompressorAndDecompressor;

import compressionPackage.Compress;
import compressionPackage.ImplementationClassForCompression;
import decompressionPackage.Decompress;
import decompressionPackage.ImplemenatationClassForDecompression;
import fileCompressorAndDecompressor.FileZipper;
import generalPackage.Node;
import generalPackage.Path;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import generalPackage.*;
public class HuffManCompressorAndDecompressor implements FileZipper
{

    @Override
    public void compress()
    {
        Compress c = new ImplementationClassForCompression();


        FileReader fileReader = c.readFile(Path.inputFilePath);

        Map<Character, Integer> frequencyMap = null;
        try {
            frequencyMap = c.calculateFreq(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Node root = c.addElementIntoQueueAndReturnRoot(frequencyMap);

        Map<Character,String> HuffMan_Map=new HashMap<>();

        c.iterateTreeAndCalculateHuffManCode(root, "",HuffMan_Map);


        StringBuilder coded=new StringBuilder();
        try
        {

            coded = c.getCodes(Path.inputFilePath,HuffMan_Map);

        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        int noOfZerosAppended =c.noofZerosToBeAppended(coded);

        if(noOfZerosAppended !=0)
        {
            coded = c.appendRemainingZeros(coded);
        }
        try
        {
            c.compress(coded,root,noOfZerosAppended);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }


        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress()
    {
        Decompress d = new ImplemenatationClassForDecompression();


          ObjectInputStream in= null;
        try
        {
            in = new ObjectInputStream(new FileInputStream(Path.compressedFilePath));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

       Node root=d.returnRootOfTree(in);

        int no_of_Zeros=0;
        try {
           no_of_Zeros=d.returnNoofZeros(in);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }


       d.getFinal(root,in,no_of_Zeros);

        System.out.println("De-Compression done Successfully");


        GeneralClass.displayStats(Path.inputFilePath,Path.compressedFilePath,Path.decompressedFilePath);

    }
}
