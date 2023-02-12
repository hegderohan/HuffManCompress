package generalPackage;

public class ImplementationClassForFileDecompresson implements IFileReader {
    StringBuilder testingCotent;

    ImplementationClassForFileDecompresson(String source)
    {

        testingCotent=new StringBuilder(source);
    }
    @Override
    public String readFile() {
       return testingCotent.toString();
    }
}
