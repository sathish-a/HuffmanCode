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
    Map<String,Character> decodedMap;

    public Huffman() {
        map = new TreeMap<>();
        encodedMap = new HashMap<>();
        decodedMap = new HashMap<>();
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
            System.out.println(set.getValue());
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

            for (Map.Entry<Integer,Node> set : map.entrySet())
            {
                if(c<2)
                {
                    addup+=set.getKey();
                    cha+=set.getValue().id;
                    if(c==0) newNode.left = set.getValue();
                    else newNode.right = set.getValue();
                }else break;
                c++;
            }

            newNode.id = (char) (cha);
            newNode.f = addup;
            if(newNode.left != null && newNode.right != null)
            {
                map.remove(newNode.left.f);
                map.remove(newNode.right.f);
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
        obtainDecodeMap();
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

    public void obtainDecodeMap()
    {
        for (Map.Entry<Character,String > set : encodedMap.entrySet())
            decodedMap.put(set.getValue(),set.getKey());
    }


    public void showEncodedData()
    {
        for (Map.Entry<Character,String > set : encodedMap.entrySet())
        {
            System.out.println(set.getKey()+"->"+set.getValue());
        }
    }

    public String compress(String x)
    {
        char[] chars = x.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char a: chars)
            sb.append(encodedMap.get(a));

        return sb.toString();
    }

    public String extract(String x)
    {
        StringBuilder sb = new StringBuilder();
        StringBuilder check = new StringBuilder();
        char[] chars = x.toCharArray();
        int from = 0;

        for(int i=0;i<x.length();i++)
        {
            check.append(chars[i]);
            if(decodedMap.containsKey(check.toString()))
            {
                sb.append(decodedMap.get(check.toString()));
                check = new StringBuilder();
            }
        }

        return sb.toString();
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
        String compress = "abaabdedbff";
        String compressed = huffman.compress(compress);
        System.out.println(compress);
        System.out.println(compressed);
        String decompress = huffman.extract(compressed);
        System.out.println(decompress);
        if(compress.contentEquals(decompress)) System.out.println("No loss");
        else System.out.println("Loss");
    }


}
