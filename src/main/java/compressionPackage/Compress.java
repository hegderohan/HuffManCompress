package compressionPackage;

import generalPackage.FileOperations;
import generalPackage.Node;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public interface Compress
{

    Map<Character,Integer> calculateFreq(FileOperations fileReader) throws IOException;
    Node addElementIntoQueueAndReturnRoot(Map<Character,Integer> frequencyMap);
    void iterateTreeAndCalculateHuffManCode(Node newNode, String s,Map<Character,String> huffmanMap);
    StringBuilder getCodes(String inputFilePath, Map<Character,String> huffmanMap,FileOperations fobj) throws IOException;
    StringBuilder appendRemainingZeros(StringBuilder coded);
    int noofZerosToBeAppended(StringBuilder coded);
    void compress(StringBuilder coded, Node root, int noOfZeros) throws IOException;

}
