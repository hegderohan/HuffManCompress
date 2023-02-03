package decompressionPackage;

import generalPackage.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


import generalPackage.*;
public class ImplemenatationClassForDecompression implements Decompress, GetStats {


    @Override
    public int measurestartTime()
    {
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
    public ArrayList<Integer> get8bitcode(int val)
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
    public void getFinal(Node root,ObjectInputStream in,int no_of_zeros)
    {
        byte[] byteArray;

        try
        {
            byteArray= (byte[]) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        try
        {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder decoded=new StringBuilder();
        System.out.println(decoded);
        for(byte x:byteArray)
        {
            int val=x;
            ArrayList<Integer> newip = get8bitcode(val<0?val+245:val);
            for (int m = 0; m < 8; m++)
            {
                decoded.append(newip.get(m));
            }
        }

        Node head=root;
        FileWriter fileWriter= null;
        try
        {
            fileWriter = new FileWriter(Path.decompressedFilePath);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        for(int i=0;i<decoded.length()-no_of_zeros;i++)
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
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void measureEndTime(int startTime)
    {
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
