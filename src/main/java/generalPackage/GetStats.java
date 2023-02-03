package generalPackage;

import java.io.File;

public interface GetStats
{
    void displayStats(String inputFilePath,String compressedFilePath,String decompressedFilePath);

    double getFileSizeMegaBytes(File file);
}
