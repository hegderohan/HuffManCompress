package compressionPackage;

import General_Package.Node;
import javafx.beans.binding.StringBinding;

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
    StringBuilder getCodes(String inputFilePath, Map<Character,String> huffmanMap);
    StringBuilder appendRemainingZeros(StringBuilder coded);
    int noofZerosToBeAppended(StringBuilder coded);
    void compress(StringBuilder coded, Node root, int noOfZeros) throws IOException;
    void measureEndTime(int startTime);
}
