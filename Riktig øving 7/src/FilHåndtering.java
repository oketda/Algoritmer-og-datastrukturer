import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FilHåndtering {

    public static byte[] lesBytesFraFil(String navn) {
        try {
            return Files.readAllBytes(Paths.get(navn));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static void skrivBytestilFil(byte[] output, String navn) {
        try(DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(navn)))){
            dos.write(output);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static byte[] listeTilArray(ArrayList<Byte> out) {
        int length = out.size();
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {result[i] = out.get(i);}
        return result;
    }
}
