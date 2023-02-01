import Compression_Package.Compress;
import Compression_Package.ImplementationClassForCompression;
import Decompression_Package.Decompress;
import Decompression_Package.ImplemenatationClassForDecompression;
import General_Package.Node;
import General_Package.Path;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import General_Package.*;
import org.omg.CORBA.Object;

public class HuffManCompressorAndDecompressor implements FileZipper
{
    Node root=null;
    public static Node goLeftorRightAndReturnNode(Node root, char val)
    {
        if(val=='0')
        {
            root=root.left;
            return root;
        }
        else
        {
            root=root.right;
            return root;
        }
    }

    public static ArrayList<Integer> get8bitCode(int val)
    {
        //this method will do the decimal to binary conversion(7bit code)
        ArrayList<Integer> ans=new ArrayList<>();
        while(val!=0)
        {
            ans.add(val%2);
            val=val/2;
        }

        if(ans.size()<8)
        {

            while(ans.size()<8)
            {
                ans.add(0);
            }
        }
        Collections.reverse(ans);
        //System.out.println(ans);
        return ans;
    }


    @Override
    public void compress()
    {
        Compress c = new ImplementationClassForCompression();

        int startTime=c.measurestartTime();

        FileReader fileReader = c.readFile(Path.inputFilePath);

        Map<Character, Integer> frequencyMap = c.calculateFreq(fileReader);

       root= c.addElementIntoQueueAndReturnRoot(frequencyMap);

        c.iterateTreeAndCalculateHuffManCode(root, "");

        Map<Character,String> HuffMan_Map= c.returnHuffmanMap();
//        for (Character name : HuffMan_Map.keySet())
//        {
//            // search  for value
//            String url = HuffMan_Map.get(name);
//            System.out.println("  " + name + "  " + url);
//        }

        String coded = c.getCodes(Path.inputFilePath,HuffMan_Map);

//        int noOfZerosAppended =c.noofZerosToBeAppended(coded);
//
//        if(noOfZerosAppended !=0)
//        {
//            coded = c.appendRemainingZeros(coded);
//        }
//        try
//        {
//            c.compress(coded,root,noOfZerosAppended);
//        }
//        catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
//
//        c.measureEndTime(startTime);
          c.getByteArray(HuffMan_Map);
        System.out.println("Compression done Successfully");
    }

    @Override
    public void decompress()
    {
        Decompress d = new ImplemenatationClassForDecompression();

        int startTime=d.measurestartTime();
        ObjectInputStream ip=null;
        try {
            ip=new ObjectInputStream(new FileInputStream(Path.compressedFilePath));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        System.out.println("decompression start");
//        int val= 0;
//        try {
//            val = ip.read();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        while(val!=-1)
//        {
//            System.out.println(val);
//            try {
//                val=ip.read();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
        ObjectInputStream in= null;
        try
        {
            in = new ObjectInputStream(new FileInputStream(Path.compressedFilePath));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        byte[] a;

            try {
                a = (byte[])in.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//        for(byte x:a)
//        {
//            System.out.println((int)x);
//        }
        ArrayList<Integer> arr=new ArrayList<>();
            for(byte x:a)
            {
                if(x<0)
                {
                    arr.add((int)x+256);
                }
                else
                {
                    arr.add((int)x);
                }
            }
     //   System.out.println(arr);
        StringBuilder decoded=new StringBuilder();
        for(int x:arr)
        {
            int vall=x;
            ArrayList<Integer> newip = get8bitCode(vall);
            for (int m = 0; m < 8; m++)
            {
                decoded.append(newip.get(m));
            }
        }
      //  System.out.println(decoded);
        Node head=root;
     //   System.out.println(head.getFrequency());
        FileWriter fileWriter= null;
        try
        {
            fileWriter = new FileWriter(Path.decompressedFilePath);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        for(int i=0;i<decoded.length();i++)
        {

            char cc=(decoded.charAt(i));
           Node newNode=goLeftorRightAndReturnNode(root,cc);
            if(newNode.left==null && newNode.right==null)
            {

                try
                {
                    fileWriter.write(newNode.var);
                 //   System.out.println(newNode.var);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
                root=head;
            }
            else
            {
                root=newNode;
            }
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//
//
//
//
//       Node root=d.returnRootOfTree(in);
//
//       int no_of_Zeros=d.returnNoofZeros(in);
//
//       String compressedString=d.returnString(in);
//
//       //this method will read the compressed file and convert ASCII character -> decimal -> 7 bit code
//        String decoded=d.getCodedStringBack(compressedString);
//
//        decoded=d.removeAppendedZeros(decoded,no_of_Zeros);
//
//        d.getFinalAns(Path.decompressedFilePath, decoded, root);
//
//        System.out.println("De-Compression done Successfully");
//
//        GetStats gg= (GetStats) new ImplemenatationClassForDecompression();
//
//        gg.displayStats(Path.inputFilePath,Path.compressedFilePath,Path.decompressedFilePath);
//
//        d.measureEndTime(startTime);
//        ObjectInputStream in=null;
//        try {
//            in = new ObjectInputStream(new FileInputStream(Path.compressedFilePath));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        byte[]  bytearray=in.
//        try {
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}
