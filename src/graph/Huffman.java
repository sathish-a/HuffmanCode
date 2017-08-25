package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Huffman {

    class Node{

        char id;
        int f;
        String code;
        Node left;
        Node right;

        public Node(char id, int f) {
            this.id = id;
            this.f = f;
            this.left =  this.right = null;
        }

        public Node(char id, int f, Node left, Node right) {
            this.id = id;
            this.f = f;
            this.left = left;
            this.right = right;
        }

        public Node() {
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", f=" + f +
                    '}';
        }
    }

    Node head;

    Map<Integer,Node> map;
    Map<Character,String> encodedMap;

    public Huffman() {
        map = new TreeMap<>();
        encodedMap = new HashMap<>();
        head = null;
    }

    public void addDatas(char id,int f)
    {
        map.put(f,new Node(id,f));
        encodedMap.put(id,"");
    }

    public void showData()
    {
        for (Map.Entry<Integer,Node> set : map.entrySet())
            System.out.println(set);
    }

    public boolean addTwoElements()
    {
        boolean isDone = false;
        if(map.size()>1)
        {

            int c = 0;
            Node newNode = new Node();
            int cha = 0;
            int addup = 0;
            Node delA ,delB;
            delA = delB = null;
            for (Map.Entry<Integer,Node> set : map.entrySet())
            {
                if(c<2)
                {
                    addup+=set.getKey();
                    cha+=set.getValue().id;
                    if(c==0) {
                        delA = set.getValue();
                        newNode.left = set.getValue();
                    }
                    else {
                        delB = set.getValue();
                        newNode.right = set.getValue();
                    }
                }
                c++;
            }

            newNode.id = (char) (cha);
            newNode.f = addup;
            if(delA != null && delB != null)
            {
                map.remove(delA.f,delA);
                map.remove(delB.f,delB);
                map.put(addup,newNode);
            }

            isDone = true;
        }
        return isDone;
    }

    public void compute()
    {
        while (map.size()>1)
            addTwoElements();

        for (Map.Entry<Integer,Node> set : map.entrySet())
        head = set.getValue();

        codeData(head,"");

    }

    public void codeData(Node x,String y)
    {
        if(x == null) return;
        else {
                x.setCode(y);
                if(encodedMap.containsKey(x.id)) encodedMap.put(x.id,x.code);
                codeData(x.left,y+"0");
                codeData(x.right,y+"1");
        }
    }


    public void showEncodedData()
    {
        for (Map.Entry<Character,String > set : encodedMap.entrySet())
        {
            System.out.println(set.getKey()+"->"+set.getValue());
        }
    }

    public static void main(String[] a)
    {
        Huffman huffman = new Huffman();
        huffman.addDatas('a',5);
        huffman.addDatas('d',13);
        huffman.addDatas('b',9);
        huffman.addDatas('f',45);
        huffman.addDatas('c',12);
        huffman.addDatas('e',16);
        huffman.compute();
        huffman.showEncodedData();
    }


}
