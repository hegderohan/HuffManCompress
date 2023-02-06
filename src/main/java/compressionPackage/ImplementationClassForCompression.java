package compressionPackage;

import generalPackage.Node;
import compressionPackage.*;
import java.io.*;
import java.util.*;
import generalPackage.*;
public class ImplementationClassForCompression implements Compress {

    Map<Character,String> huffmanMap =new HashMap<>();

    @Override
    public int measurestartTime()
    {
        return (int) System.currentTimeMillis();
    }

    @Override
    public FileReader readFile(String path) {
        FileReader fileReader;
        try
        {
            fileReader=new FileReader(path);
        }
        catch (FileNotFoundException e)
        {
            //if the file does not exist it will throw an error
            System.out.println("FILE DOESNT EXIST");
            throw new RuntimeException(e);
        }
        File ipFile=new File(path);
        if(ipFile.length()==0)
        {
            System.out.println("EMPTY FILE CANNOT COMPRESS");
            System.exit(0);
        }
        return fileReader;
    }

    @Override
    public Map<Character, Integer> calculateFreq(FileReader fileReader) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int c =0;
        try
        {
            //c will have be having the ascii value
            c = fileReader.read();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        while (c != -1)
        {
                frequencyMap.put((char)c,frequencyMap.getOrDefault((char)c,0)+1);
            try
            {
                c = fileReader.read();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        try {
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return frequencyMap;
    }

    @Override
    public Node addElementIntoQueueAndReturnRoot(Map<Character, Integer> frequencyMap)
    {

        PriorityQueue<Node> pq = new PriorityQueue<>(frequencyMap.size(), new FrequencyComparator());

        //iterate through the frequencyMap and keep adding the elements to the priority_queue
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet())
        {
            Node nd = new Node();
            nd.setVar((entry.getKey()));
            nd.setFrequency(entry.getValue());
            nd.setLeft(null);
            nd.setRight(null);
           // System.out.println(entry.getKey()+"  "+entry.getValue());
            pq.add(nd);
        }


        Node root = null;

        if(pq.size()==1)
        {
            Node leftSideNode=pq.peek();
            pq.poll();
            Node newNode = new Node();

            newNode.setFrequency(leftSideNode.getFrequency());
            newNode.setVar('-');
            newNode.setLeft(leftSideNode);
            newNode.setRight(null);
            root=newNode;

            return root;
        }


        //I want this step to be repeated till only one element is left
        while (pq.size() > 1)
        {
            //get the top element in the queue
                Node leftSideNode= pq.peek();
                //pop that element
                pq.poll();

            //get the second element in the queue
                Node rightSideNode = pq.peek();
                //pop that element
                pq.poll();


            //make a new General_Package.Node contataing the combined frequency of the last two nodes
            Node newNode = new Node();

                newNode.setFrequency(leftSideNode.getFrequency() + rightSideNode.getFrequency());
                newNode.setVar(' ');
                newNode.setLeft(leftSideNode);
                newNode.setRight(rightSideNode);


            //say that newNode as root,eventually the last element will be root
            root = newNode;


            //push that new General_Package.Node into queue
            pq.add(newNode);
        }
        //after coming out from while loop return root
        return root;
    }



    @Override
    public void iterateTreeAndCalculateHuffManCode(Node newNode, String s)
    {
        if(newNode==null)
        {
            return;
        }
        if(newNode.getLeft()==null && newNode.getRight()==null)
        {
            huffmanMap.put(newNode.getVar(),s);
        }
        iterateTreeAndCalculateHuffManCode(newNode.getLeft(),s+"0");
        iterateTreeAndCalculateHuffManCode(newNode.getRight(),s+"1");
    }

    @Override
    public Map<Character, String> returnHuffmanMap()
    {

        return huffmanMap;
    }

    @Override
    public StringBuilder getCodes(String inputFilePath, Map<Character, String> huffmanMap)
    {
       FileReader fileReader=null;
        StringBuilder ans=new StringBuilder();

        try
        {
            fileReader = new FileReader(inputFilePath);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }


        int c= 0;
        try {
            c = fileReader.read();

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(c!=-1)
        {
            ans.append((huffmanMap.get((char)c)));

            try {
                c= fileReader.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try
        {
            fileReader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }
    public int noofZerosToBeAppended(StringBuilder coded)
    {
         if(coded.length()==0 | coded.length()%8==0)
         {
             return 0;
         }
        return 8-(coded.length()%8);
    }

    @Override
    public StringBuilder appendRemainingZeros(StringBuilder coded)
    {
        int rem = coded.length() % 8;
        if (rem != 0)
        {
            rem = 8 - rem;
            while (rem != 0)
            {
                coded=coded.append("0");
                rem--;
            }
        }
        return coded;
    }

    @Override
    public void compress(StringBuilder coded, Node root, int noOfZeros) throws IOException
    {

        byte[] bytearray = new byte[coded.length() / 8];
        StringBuilder sub =new StringBuilder();
        int bytearrayIndex = 0;
        for (int i = 0; i < coded.length(); i = i + 8) {
            int j = 0;
            while (j < 8) {
                sub = sub.append(coded.charAt(i + j));
                j++;
            }
            bytearray[bytearrayIndex] = (byte) Integer.parseInt(sub.toString(), 2);
            bytearrayIndex++;
            sub.setLength(0);
        }
        ObjectOutputStream obj=null;
        try {
            obj=new ObjectOutputStream(new FileOutputStream(Path.compressedFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {

            obj.writeObject(bytearray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(Path.compressedFilePath));
        out.writeObject(root);
        out.writeInt(noOfZeros);
        out.writeObject(bytearray);
        out.close();
    }
     public void measureEndTime(int startTime)
     {
         int timetaken=(int)System.currentTimeMillis()-startTime;
         System.out.println("Time taken for Compression "+timetaken);
     }
}
