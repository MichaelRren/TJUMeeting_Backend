package com.ictwsn.util;
 
import java.io.IOException;  
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCount {
	public static void main(String[] args){
		try {
			long start=System.currentTimeMillis();
			WordCount.wordCountAndSort();
		    System.out.println(System.currentTimeMillis()-start); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void wordCountAndSort() throws IOException {
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<String> list2=new ArrayList<String>();
        for(int i=0;i<10;i++){
        	list2.add(RandomString.generateString(10));
        }
        String s="";
        for(int i=0;i<10;i++){
        	s=list2.get(i);
        	 if(!map.containsKey(s))
                 map.put(s,1);
        	 else
                 map.put(s,map.get(s)+1);
        }
        
        //排序。将Map转为list，然后进行sort
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        
        
    }
}
