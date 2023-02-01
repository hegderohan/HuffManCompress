package Compression_Package;

import General_Package.Node;

import java.io.*;
import java.util.*;
import General_Package.*;
public class ImplementationClassForCompression implements Compress {

    Map<Character,String> HuffMan_Map=new HashMap<>();

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
        while (pq.size() > 1) {
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
            HuffMan_Map.put(newNode.getVar(),s);
        }
        iterateTreeAndCalculateHuffManCode(newNode.getLeft(),s+"0");
        iterateTreeAndCalculateHuffManCode(newNode.getRight(),s+"1");
    }

    @Override
    public Map<Character, String> returnHuffmanMap()
    {
        return HuffMan_Map;
    }

    @Override
    public String getCodes(String inputFilePath, Map<Character, String> HuffMan_Map)
    {
       FileReader fileReader=null;
        StringBuilder ans=new StringBuilder();

        try {
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
            ans.append((HuffMan_Map.get((char)c)));

            try {
                c= fileReader.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return ans.toString();
    }
    public int noofZerosToBeAppended(String coded)
    {

        return 7-coded.length()%7;
    }

    @Override
    public String appendRemainingZeros(String coded)
    {
        int rem = coded.length() % 7;
        if (rem != 0)
        {
            rem = 7 - rem;
            while (rem != 0)
            {
                coded = coded + "0";
                rem--;
            }
        }
        return coded;
    }

    @Override
    public void compress(String coded, Node root, int noOfZeros) throws IOException
    {
       StringBuilder compressedString =new StringBuilder();
        int val = 0;
        int pow ;

        for (int i = 0; i < coded.length(); i = i + 7) {
            int j = 0;
            pow = 64;
            while (j < 7)
            {
                val = val + (coded.charAt(i + j)-'0') * pow;
                pow = pow >> 1;
                j++;
            }
            compressedString.append((char)val);
            val = 0;
        }
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(Path.compressedFilePath));
        out.writeObject(root);
        out.writeInt(noOfZeros);
        out.writeObject(compressedString.toString());
        out.close();
    }
     public void measureEndTime(int startTime)
     {
         int timetaken=(int)System.currentTimeMillis()-startTime;
         System.out.println("Time taken for Compression "+timetaken);
     }
}
