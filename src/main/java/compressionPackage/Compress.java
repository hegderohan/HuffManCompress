package compressionPackage;

import generalPackage.IFileReader;
import generalPackage.Node;

import java.io.IOException;
import java.util.Map;

public interface Compress
{
    Map<Character,Integer> calculateFreq(IFileReader fileReader);
    Node addElementIntoQueueAndReturnRoot(Map<Character,Integer> frequencyMap);
    void iterateTreeAndCalculateHuffManCode(Node newNode, String s,Map<Character,String> huffmanMap);
    StringBuilder getCodes(Map<Character,String> huffmanMap, IFileReader fobj);
    StringBuilder appendRemainingZeros(StringBuilder coded);
    int noofZerosToBeAppended(StringBuilder coded);
    byte[] compress(StringBuilder coded);
}
