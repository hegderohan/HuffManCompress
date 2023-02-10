package generalPackage;

public class ImplementationClassForFileDecompresson implements FileOperations{
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
