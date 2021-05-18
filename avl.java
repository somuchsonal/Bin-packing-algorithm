import java.util.NoSuchElementException;
import java.util.Objects;

public class avl <Object>{
    public class Node{
        Object el;
        int key;
        Node left;
        Node right;
        Node parent;
        int height;
    }
    public avl(){
        root = new Node();
    }
    public Node root;
    public void leftrotate(Node l){
        Node shi = l.right;
        Node T1 = shi.left;
        shi.left = l;
        l.right = T1;
        T1.parent = l;
        shi.parent = l.parent;
        if(l!=root){ 
            if(Objects.equals(l.parent.left,l)) l.parent.left = shi;
            else l.parent.right = shi;
        }
        else root = shi;
        l.parent = shi;
        l.height = Math.max(l.right.height, l.left.height)+1;
        shi.height = Math.max(shi.right.height, shi.left.height)+1;
    }
    public void rightrotate(Node r) {
        Node sh = r.left;
        Node T2 = sh.right;
        sh.right = r;
        r.left = T2;
        T2.parent = r;
        sh.parent = r.parent;
        if(r!=root){
            if(Objects.equals(r.parent.left,r)) r.parent.left = sh;
            else r.parent.right = sh;
        }
        else root = sh;
        r.parent = sh;
        r.height = Math.max(r.right.height, r.left.height)+1;
        sh.height = Math.max(sh.right.height, sh.left.height)+1;
    }
    public void put(int k, Object e) {
        Node rep = root;
        while(rep.el!=null){
            Node par = rep;
            if(k>=rep.key) rep = rep.right;
            else rep = rep.left;
            rep.parent = par;
        }
        rep.key = k;
        rep.el = e;
        rep.left = new Node();
        rep.right = new Node();
        rep.height = 1;
        while(rep!=root){
            rep = rep.parent;
            int c = rep.left.height - rep.right.height;
            if(c>1&&k<rep.left.key) rightrotate(rep);
            else if(c<-1&&k>=rep.right.key) leftrotate(rep);
            else if(c>1&&k>=rep.left.key){
                leftrotate(rep.left);
                rightrotate(rep);
            }
            else if(c<-1&&k<rep.right.key){
                rightrotate(rep.right);
                leftrotate(rep);
            }
            rep.height = Math.max(rep.left.height,rep.right.height) +1;
        }
    }
    public Object search(int ks) {
        Node pre = root;
        while(pre.el!=null){
            if(pre.key == ks) return pre.el;
            else if(ks>=pre.key) pre = pre.right;
            else pre = pre.left;
        }
        return null;
    }
    private Node get(int ks) {
        Node pres = root;
        while(pres.el!=null){
            if(pres.key == ks) return pres;
            else if(ks>=pres.key) pres = pres.right;
            else pres = pres.left;
        }
        return null;
    }
    public void remove(int kd) throws NoSuchElementException{
        Node del = get(kd);
        if(del==null) throw new NoSuchElementException();
        Node u = del.parent;
        if(del.left.el==null){
            del.right.parent = u;
            if(u!=null) {
                if(Objects.equals(del,u.left)) u.left = del.right;
                else u.right = del.right;
            }
            else root = del.right;
            del.left = null;
            del = null;
        }
        else if(del.right.el == null){
            del.left.parent = u;
            if(u!=null){                                                    //update root if deleting root
                if(Objects.equals(del, u.left)) u.left = del.left;
                else u.right = del.left;
            }
            else root = del.left;
            del.right = null;
            del = null;
        }
        else{
            Node f = del.right;
            while(f.left.el!=null) f = f.left;
            int kt = f.key;
            Object et = f.el;
            remove(kt);
            del.key = kt;
            del.el = et;
        }
        while(u!=null){
            u.height = Math.max(u.left.height, u.right.height) +1;
            int ch = u.left.height - u.right.height;
            if(ch>1 && u.left.left.height>=u.left.right.height) rightrotate(u); 
            else if(ch<-1 && u.right.right.height>=u.right.left.height) leftrotate(u);
            else if(ch>1 && u.left.left.height<u.left.right.height){
                leftrotate(u.left);
                rightrotate(u);
            }
            else if(ch<-1 && u.right.right.height<u.right.left.height){
                rightrotate(u.right);
                leftrotate(u);
            }
            u = u.parent;
        }
    }
    public Object largest(){
        Node la = root;
        while(la.right.el!=null) la = la.right;
        return la.el;
    }
    public static void main(String[] args) {
        
    }
}