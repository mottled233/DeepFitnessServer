package util;

import java.util.ArrayList;

/**
 * This class is a short list that can get number by index which is order by time their was put in.
 * If the number of elements put in this list more than capacity,it while delete the oldest element automatically.
 * @author mottled
 *
 */
public class ShortList {
	private int[] arr;
	private int capacity;
	private int size;
	public ShortList(int capacity){
		this.capacity = capacity;
		size = 0;
		arr = new int[capacity];
	}
	public void put(int element){
		if(size!=capacity){
			arr[size]=element;
			size++;
		}else{
			for(int i = 0;i<size-1;i++){
				arr[i] = arr[i+1];
			}
			arr[size-1] = element;
		}
	}
	/**
	 * Get element stored in this list.
	 * @param i 0 to get the last element,1 to get the second last element,and so on,but less than size.
	 * @return If i out of bound,return -1.
	 */
	public int get(int i){
		if(i<size){
			return arr[size-1-i];
		}else
			return -1;
	}
	
	public int[] getMax(){
		ArrayList<Integer> list = new ArrayList<>();
		int max = 0;
		int index = -1;
		for(int i = 0;i<size;i++){
			if(max<get(i)) {max = get(i);index = i;}
		}
		for(int i = 0;i<size;i++){
			if((max-get(i))<10 && get(i)!=max){
				list.add(i);
				list.add(get(i));
			}
		}
		int[] array = new int[list.size()+2];
		array[0]=index;
		array[1]=max;
		for(int i = 0; i<list.size();i++){
			array[i+2] = list.get(i);
		}
		
		return array;
	}
	
	public int[] getMin(){
		ArrayList<Integer> list = new ArrayList<>();
		int min = 180;
		int index = -1;
		for(int i = 0;i<size;i++){
			if(min>get(i)) {min = get(i);index = i;}
		}
		for(int i = 0;i<size;i++){
			if((min-get(i))>-10 && get(i)!=min){
				list.add(i);
				list.add(get(i));
			}
		}
		int[] array = new int[list.size()+2];
		array[0]=index;
		array[1]=min;
		for(int i = 0; i<list.size();i++){
			array[i+2] = list.get(i);
		}
		
		return array;
	}
	
	public int size(){
		return size;
	}
	
	public int capacity(){
		return capacity;
	}
	
}
