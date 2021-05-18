import java.io.*;
import java.util.*;
import javafx.util.Pair;

public class BestFit {
    public static avl<bin> bins  = new avl<bin>();
    public static avl<avl<bin>> bincap  = new avl<avl<bin>>();
    public static avl<obj> objects = new avl<obj>();
    public BestFit(){
        bins = new avl<bin>();
        bincap = new avl<avl<bin>>();
        objects = new avl<obj>();
    }
    public static class bin{
        int id;
        int cap;
        Vector<obj> fill;
        int rc;
        bin(int idd, int c){
            id = idd;
            cap = c;
            rc = c;
            fill = new Vector<obj>();
        }
    }
    public static class obj{
        int ido;
        int size;
        //nodel lit;
        bin inside;
        obj(int iddd, int s){
            ido = iddd;
            size = s;
        }
    }
    public static void add_bin(int bin_id, int capacity){
        if(bins.search(bin_id)!=null){
            System.out.println("Bin already exists");
            System.exit(0);
        }
        bin next = new bin(bin_id,capacity);
        bins.put(bin_id, next);
        avl<bin> newtree = bincap.search(next.rc);
        if(newtree==null) {
            newtree = new avl<bin>();
            bincap.put(next.rc, newtree);
        }
        newtree.put(bin_id,next);
    }
    public static int add_object(int obj_id, int size){
        if(objects.search(obj_id)!=null){
            System.out.println("Object already exists");
            System.exit(0);
        }
        obj fresh = new obj(obj_id, size);
        objects.put(obj_id, fresh);
        avl<bin> inpre = bincap.largest();
        bin in = inpre.largest();
        if(in.rc< size){
            System.out.println("The object is too big");
            System.exit(0);
        }
        inpre.remove(in.id);
        if(inpre.root.el == null) bincap.remove(in.rc);
        in.fill.add(fresh);
        in.rc -= fresh.size;
        fresh.inside = in;
        avl<bin> inpost = bincap.search(in.rc);
        if(inpost==null) {
            inpost= new avl<bin>();
            bincap.put(in.rc, inpost);
        }
        inpost.put(in.id,in);
        System.out.println(in.id);
        return in.id;
    }
    public static int delete_object(int obj_id) {
        obj dis = objects.search(obj_id);
        if(dis ==null){
            System.out.println("this object doesn't exist "+obj_id);
            System.exit(0);
        }
        bin clean = dis.inside;
        avl<bin> rempre = bincap.search(clean.rc);
        rempre.remove(clean.id);
        if(rempre.root.el == null) bincap.remove(clean.rc);
        clean.fill.remove(dis);
        clean.rc += dis.size;
        avl<bin> rempost = bincap.search(clean.rc);
        if(rempost==null) {
            rempost = new avl<bin>();
            bincap.put(clean.rc, rempost);
        }
        rempost.put(clean.id,clean);
        objects.remove(obj_id);
        System.out.println(clean.id);
        return clean.id;
    }
    public static List<Pair<Integer,Integer>> contents(int idp) {
        bin pho = bins.search(idp);
        List<Pair<Integer,Integer>> oblist = new Vector<Pair<Integer,Integer>>();
        if(pho ==null){
            System.out.println("this bin doesn't exist");
            System.exit(0);
        }
        for(int i=0; i<pho.fill.size();i++){
            System.out.println(pho.fill.elementAt(i).ido+" "+pho.fill.elementAt(i).size);
            Pair<Integer,Integer> op = new Pair<Integer,Integer>(pho.fill.elementAt(i).ido,pho.fill.elementAt(i).size);
            oblist.add(op);
        }
        return oblist;
    }
    public static void main(String[] args) {
        try{
            File f = new File(args[0]);
            Scanner s = new Scanner(f);
            try{
                PrintStream ext = new PrintStream(new File("output.txt"));
                System.setOut(ext);
                while(s.hasNext()) {
                    int k = s.nextInt();
                    if(k==1) add_bin(s.nextInt(),s.nextInt());
                    else if(k==2) add_object(s.nextInt(),s.nextInt());
                    else if(k==3) delete_object(s.nextInt());
                    else if(k==4) contents(s.nextInt());
                }
            }catch(FileNotFoundException fn){
                System.out.println("Output file not found");
                System.exit(0);
            }
            s.close();
        }catch(FileNotFoundException e){
            System.out.println("input file not found");
            System.exit(0);
        }
    }
}