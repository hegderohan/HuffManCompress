
import General_Package.FileZipper;
import General_Package.Path;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scr = new Scanner(System.in);
        int choice = 0;

        int atleastOnce = 0;
        boolean flag = true;


       FileZipper zipper = new HuffManCompressorAndDecompressor();

      /*

      if I want some new Zipping Algorithm to be implemented

        General_Package.FileZipper zipper=new SomeNewAlgorithm();

        zipper.compress()  -> it will call that corresponding Algorithms Compression_Package.Compress Method

        zipper.decompress()  -> it will call that corresponding Algorithms Decompression Method
        */


        while (flag) {
            System.out.println("enter the option 1 for compression"+System.lineSeparator()+"2 for decompression"+System.lineSeparator()+"3 check validity of the decompressed file"+System.lineSeparator()+" and press 4 to exit"+System.lineSeparator());
            choice = scr.nextInt();

            switch (choice)
            {
                case 1:
                    atleastOnce = 1;
                    zipper.compress();
                    break;

                case 2:
                    if (atleastOnce != 1) {
                        System.out.println("You have to perform compression at least once");
                        break;
                    }
                    zipper.decompress();
                    break;

                case 3:
                    FileReader f1 = new FileReader(Path.inputFilePath);
                    FileReader f2 = new FileReader(Path.decompressedFilePath);
                    int val1 = f1.read();
                    int val2 = f2.read();
                    while (val1 != 1 && val2 != -1)
                    {
                        if(val1!=val2)
                        {
                            System.out.println("There is a mis-match");
                        }
                        val1=f1.read();
                        val2=f2.read();
                    }
                    System.out.println("Files Match");
                    break;
                case 4:flag = false;
                    break;

                default:
                    System.out.println("Enter a valid choice");
            }
        }
    }
}