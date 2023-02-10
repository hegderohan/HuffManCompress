package compressionPackage;

import generalPackage.Node;
import compressionPackage.*;
import java.io.*;
import java.util.*;
import generalPackage.*;
public class ImplementationClassForCompression implements Compress
{
    @Override
    public Map<Character, Integer> calculateFreq(FileOperations fileReader) throws IOException
    {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int c =0;

        String ans=fileReader.readFile();

        for(char x:ans.toCharArray())
        {
            frequencyMap.put(x,frequencyMap.getOrDefault(x,0)+1);
        }
        return  frequencyMap;
    }
    @Override
    public Node addElementIntoQueueAndReturnRoot(Map<Character, Integer> frequencyMap)
    {
        PriorityQueue<Node> pq = new PriorityQueue<>(frequencyMap.size(), new FrequencyComparator());
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            Node nd = new Node();
            nd.setVar((entry.getKey()));
            nd.setFrequency(entry.getValue());
            nd.setLeft(null);
            nd.setRight(null);
            pq.add(nd);
        }
        Node root = null;
        if(pq.size()==1) {
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
        while (pq.size() > 1) {
                Node leftSideNode= pq.peek();
                pq.poll();
                Node rightSideNode = pq.peek();
                pq.poll();
            Node newNode = new Node();
                newNode.setFrequency(leftSideNode.getFrequency() + rightSideNode.getFrequency());
                newNode.setVar('-');
                newNode.setLeft(leftSideNode);
                newNode.setRight(rightSideNode);
            root = newNode;
            pq.add(newNode);
        }
        return root;
    }
    @Override
    public void iterateTreeAndCalculateHuffManCode(Node newNode, String s,Map<Character,String> huffmanMap)
    {
        if(newNode==null) {
            return;
        }
        if(newNode.getLeft()==null && newNode.getRight()==null) {
            huffmanMap.put(newNode.getVar(),s);}
        iterateTreeAndCalculateHuffManCode(newNode.getLeft(),s+"0",huffmanMap);
        iterateTreeAndCalculateHuffManCode(newNode.getRight(),s+"1",huffmanMap);
    }
//
    @Override
    public StringBuilder getCodes(String inputFilePath, Map<Character, String> huffmanMap,FileOperations fobj) throws IOException{
       FileReader fileReader=null;
        StringBuilder ans=new StringBuilder();
        String curr=fobj.readFile();
        for(char x:curr.toCharArray())
        {
            ans.append(huffmanMap.get(x));
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
    public StringBuilder appendRemainingZeros(StringBuilder coded) {
        int rem = coded.length() % 8;
        if (rem != 0) {
            rem = 8 - rem;
            while (rem != 0) {
                coded=coded.append("0");
                rem--;
            }
        }
        //System.out.println(coded);
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

        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(Path.compressedFilePath));
        out.writeObject(root);
       out.writeInt(noOfZeros);
       out.writeObject(bytearray);
        out.close();
    }
}
