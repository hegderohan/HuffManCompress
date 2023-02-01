package Decompression_Package;

import Decompression_Package.Decompress;
import General_Package.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import General_Package.*;
public class ImplemenatationClassForDecompression implements Decompress, GetStats {

    @Override
    public int measurestartTime() {
        return (int)System.currentTimeMillis();
    }

    @Override
    public Node returnRootOfTree(ObjectInputStream in)
    {
        Node root=null;
    try
    {
        root=(Node)in.readObject();
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
        return root;
    }

    @Override
    public int returnNoofZeros(ObjectInputStream ip) {
        int no_of_Zeros=0;
        try {
            no_of_Zeros=ip.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return no_of_Zeros;
    }

    @Override
    public String returnString(ObjectInputStream ip)
    {
        String compressedString="";
        try
        {
          compressedString=(String) ip.readObject();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return compressedString;
    }
    @Override
    public String getCodedStringBack(String compressedString)
    {

        StringBuilder decoded = new StringBuilder();

        int vall=0;
        for(char ch:compressedString.toCharArray())
        {
            vall = (int) ch;

                ArrayList<Integer> newip = get7bitCode(vall);
                for (int m = 0; m < 7; m++)
                {
                    decoded.append(newip.get(m));
                }

        }

        return decoded.toString();
    }

    @Override
    public ArrayList<Integer> get7bitCode(int val)
    {
        //this method will do the decimal to binary conversion(7bit code)
        ArrayList<Integer> ans=new ArrayList<>();
        while(val!=0)
        {
            ans.add(val%2);
            val=val/2;
        }

        if(ans.size()<7)
        {

            while(ans.size()<7)
            {
                ans.add(0);
            }
        }
        Collections.reverse(ans);
        //System.out.println(ans);
        return ans;
    }

    @Override
    public Node goLeftorRightAndReturnNode(Node root, char val)
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



    @Override
    public void getFinalAns(String decompressedFilePath, String decoded,Node root)
    {
        FileWriter fileWriter=null;
        try {
            fileWriter =new FileWriter(decompressedFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Node head=root;

        Node newNode=null;
        for(int i=0;i<decoded.length();i++)
        {

            char cc=(decoded.charAt(i));
            newNode=goLeftorRightAndReturnNode(root,cc);
            if(newNode.left==null && newNode.right==null)
            {

                try {
                    fileWriter.write(newNode.var);
                } catch (IOException e) {
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

    }

    @Override
    public String removeAppendedZeros(String decoded, int noOfZerosAppended)
    {
       return decoded.substring(0,decoded.length()-noOfZerosAppended);
    }

    @Override
    public void measureEndTime(int startTime) {
     int timetaken=(int)System.currentTimeMillis()-startTime;
        System.out.println("Time taken for decompression "+timetaken);
    }

    @Override
    public void displayStats(String inputFilePath, String compressedFilePath, String decompressedFilePath)
    {
        File ipFile=new File(inputFilePath);
        File compressedFile=new File(compressedFilePath);
        File decompressedFiled=new File(decompressedFilePath);

        long ipFilesize=ipFile.length();
        long compressedFilesize=compressedFile.length();
        long decompressedFilesize=decompressedFiled.length();


        final String BYTES= "bytes ->";
        System.out.println("Input File size is "+ipFilesize+" "+BYTES+getFileSizeMegaBytes(ipFile)+" MB");
        System.out.println("Compressed File size is "+compressedFilesize+" "+BYTES+getFileSizeMegaBytes(compressedFile)+" MB");
        System.out.println("Decompressed File size is "+decompressedFilesize+" "+BYTES+getFileSizeMegaBytes(decompressedFiled)+" MB");


        double ans= (ipFilesize-compressedFilesize);
        ans=((ans)/(ipFilesize));
        System.out.println("       ");
        System.out.println("        ");
        System.out.println("Compression Percentage "+ans*100+" %");
    }

    @Override
    public double getFileSizeMegaBytes(File file)
    {
        return (double) file.length() / (1024 * 1024) ;
    }

}
