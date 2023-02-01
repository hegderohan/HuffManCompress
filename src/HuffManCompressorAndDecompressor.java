import Compression_Package.Compress;
import Compression_Package.ImplementationClassForCompression;
import Decompression_Package.Decompress;
import Decompression_Package.ImplemenatationClassForDecompression;
import General_Package.Node;
import General_Package.Path;

import java.io.*;
import java.util.Map;
import General_Package.*;
public class HuffManCompressorAndDecompressor implements FileZipper
{

    @Override
    public void compress()
    {
        Compress c = new ImplementationClassForCompression();

        int startTime=c.measurestartTime();

        FileReader fileReader = c.readFile(Path.inputFilePath);

        Map<Character, Integer> frequencyMap = c.calculateFreq(fileReader);

       Node root = c.addElementIntoQueueAndReturnRoot(frequencyMap);

        c.iterateTreeAndCalculateHuffManCode(root, "");

        Map<Character,String> HuffMan_Map= c.returnHuffmanMap();

        String coded = c.getCodes(Path.inputFilePath,HuffMan_Map);

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

        c.measureEndTime(startTime);

        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress()
    {
        Decompress d = new ImplemenatationClassForDecompression();

        int startTime=d.measurestartTime();

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

       int no_of_Zeros=d.returnNoofZeros(in);

       String compressedString=d.returnString(in);

       //this method will read the compressed file and convert ASCII character -> decimal -> 7 bit code
        String decoded=d.getCodedStringBack(compressedString);

        decoded=d.removeAppendedZeros(decoded,no_of_Zeros);

        d.getFinalAns(Path.decompressedFilePath, decoded, root);

        System.out.println("De-Compression done Successfully");

        GetStats gg= (GetStats) new ImplemenatationClassForDecompression();

        gg.displayStats(Path.inputFilePath,Path.compressedFilePath,Path.decompressedFilePath);

        d.measureEndTime(startTime);
    }
}
