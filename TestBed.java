package serialization2;

import static org.junit.Assert.assertEquals;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class TestBed {

	public enum Fruit {
		Apples, Oranges, Pears
	}

	public static void main(String[] args) throws NotSerializableException, StreamCorruptedException {		
		
		ACustomSerializer cs = new ACustomSerializer();
		cs.registerSerializer(Integer.class, new ASerializer());
		cs.registerSerializer(Double.class, new ASerializer());
		cs.registerSerializer(String.class, new ASerializer());
		cs.registerSerializer(Boolean.class, new ASerializer());
		cs.registerSerializer(HashSet.class, new ASerializer());
		cs.registerSerializer(ArrayList.class, new ASerializer());
		cs.registerSerializer(Vector.class, new ASerializer());
		cs.registerSerializer(HashMap.class, new ASerializer());
		cs.registerSerializer(Hashtable.class, new ASerializer());
		cs.registerSerializer(Enum.class, new ASerializer());
		cs.registerSerializer(Object[].class, new AnArraySerializer());
		
		
		ByteBuffer bytebuffer;
		Object output;
		
		Object[] values2 = {};
		
		bytebuffer = cs.outputBufferFromObject(values2);
		output = cs.objectFromByteBuffer(bytebuffer);

		System.out.println(" input: "+values2);
		System.out.println("output: "+output);
		System.out.println(" input class: "+values2.getClass().getName());
		System.out.println("output class: "+output.getClass().getName());

		for(Object a:(Object[])values2){
			System.out.println(a);
		}
		for(Object a:(Object[])output){
			System.out.println(a);
		}
	}
}
