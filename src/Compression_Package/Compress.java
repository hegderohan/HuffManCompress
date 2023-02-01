package Compression_Package;

import General_Package.Node;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public interface Compress
{
    int measurestartTime();
    FileReader readFile(String path);
    Map<Character,Integer> calculateFreq(FileReader fileReader);
    Node addElementIntoQueueAndReturnRoot(Map<Character,Integer> frequencyMap);
    void iterateTreeAndCalculateHuffManCode(Node newNode, String s);
    Map<Character, String> returnHuffmanMap();
    String getCodes(String inputFilePath, Map<Character,String> huffmanMap);
//    String appendRemainingZeros(String coded);
//    int noofZerosToBeAppended(String coded);
//    void compress(String coded, Node root, int noOfZeros) throws IOException;
//    void measureEndTime(int startTime);
    void getByteArray(Map<Character,String> huffmanMap);
}
