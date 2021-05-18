import java.io.*;
import java.util.*;

public class same {

    public static void main(String[] args) {
        try{ 
            File f1 = new File(args[0]);
            Scanner s1 = new Scanner(f1);
            File f2 = new File(args[1]);
            Scanner s2 = new Scanner(f2);
            boolean x = true;
            while(s1.hasNext()&&s2.hasNext()){
                if(s1.nextLine().equals(s2.nextLine())) x = true;
                else {
                    x = false;
                    System.out.println(x);
                    System.exit(0);;
                }
            }
            if(s1.hasNext()||s2.hasNext()) x = false;
            System.out.println(x);
            s1.close();
            s2.close();
        }catch(FileNotFoundException y){
            System.out.println("Input file not found");
            System.exit(0);
        }catch(ArrayIndexOutOfBoundsException h){
            System.out.println("Invalid Input");
        }
    }
}