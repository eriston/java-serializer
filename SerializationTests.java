package serialization2;

import static org.junit.Assert.*;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;

import org.junit.Test;

import serialization.examples.ANamedBMISpreadsheet;
import serialization.examples.AnObjectHistory;
import serialization.examples.AnotherBMISpreadsheet;
import serialization.examples.BMISpreadsheet;
import serialization.examples.NamedBMISpreadsheet;
import serialization.examples.ObjectHistory;

//import synchronization.SerializationTester.Color;

//import synchronization.SerializationTester.Color;

enum Color {
	RED, GREEN, BLUE
}

public class SerializationTests {

	@Test
	public void testSerializationOfBool() throws NotSerializableException, StreamCorruptedException {
		boolean testbool = true;
		ASerializer serlzer = new ACustomSerializer();

		ByteBuffer bb = serlzer.outputBufferFromObject(testbool);
		boolean returnedDouble = (boolean) serlzer.objectFromByteBuffer(bb);
		assertEquals(true, returnedDouble);

		boolean testbool2 = false;
		ASerializer serlzer2 = new ASerializer();

		ByteBuffer bb2 = serlzer.outputBufferFromObject(testbool2);
		boolean returnedDouble2 = (boolean) serlzer.objectFromByteBuffer(bb2);
		assertEquals(false, returnedDouble2);
	}

	@Test
	public void testSerializationOfBoolDoubleRead() throws NotSerializableException, StreamCorruptedException {
		boolean testint = true;
		ASerializer serlzer = new ACustomSerializer();

		ByteBuffer bb = serlzer.outputBufferFromObject(testint);
		boolean returnedint = (boolean) serlzer.objectFromByteBuffer(bb);
		assertEquals(true, returnedint);
		boolean returnedint2 = (boolean) serlzer.objectFromByteBuffer(bb);
		assertEquals(true, returnedint2);
	}

	@Test
	public void testSerializationOfDouble() throws NotSerializableException, StreamCorruptedException {
		double testint = 4.2;
		ASerializer serlzer = new ASerializer();

		ByteBuffer bb = serlzer.outputBufferFromObject(testint);
		double returnedDouble = (double) serlzer.objectFromByteBuffer(bb);
		assertEquals(4.2, returnedDouble, 0.01);
	}

	@Test
	public void testSerializationOfIntDoubleDoubleRead() throws NotSerializableException, StreamCorruptedException {
		double testint = 4.2;
		ASerializer serlzer = new ASerializer();

		ByteBuffer bb = serlzer.outputBufferFromObject(testint);
		double returnedint = (double) serlzer.objectFromByteBuffer(bb);
		assertEquals(4.2, returnedint, 0.01);
		double returnedint2 = (double) serlzer.objectFromByteBuffer(bb);
		assertEquals(4.2, returnedint2, 0.01);
	}

	@Test
	public void testSerializationOfInt() throws NotSerializableException, StreamCorruptedException {
		int testint = 42;
		ASerializer serlzer = new ASerializer();

		ByteBuffer bb = serlzer.outputBufferFromObject(testint);
		int returnedint = (int) serlzer.objectFromByteBuffer(bb);
		assertEquals(42, returnedint);
	}

	@Test
	public void testSerializationOfIntDoubleRead() throws NotSerializableException, StreamCorruptedException {
		int testint = 42;
		ASerializer serlzer = new ASerializer();

		ByteBuffer bb = serlzer.outputBufferFromObject(testint);
		int returnedint = (int) serlzer.objectFromByteBuffer(bb);
		assertEquals(42, returnedint);
		int returnedint2 = (int) serlzer.objectFromByteBuffer(bb);
		assertEquals(42, returnedint2);
	}

	@Test
	public void testSerializationOfNull() throws NotSerializableException, StreamCorruptedException {
		// int testint = 42;
		ASerializer serlzer = new ASerializer();
		ByteBuffer bb = serlzer.outputBufferFromObject(null);

		Object returned = serlzer.objectFromByteBuffer(bb);
		assertEquals(null, returned);
	}

	@Test
	public void testSerializationOfNullDoubleRead() throws NotSerializableException, StreamCorruptedException {
		// int testint = 42;
		ASerializer serlzer = new ASerializer();
		ByteBuffer bb = serlzer.outputBufferFromObject(null);

		Object returned = serlzer.objectFromByteBuffer(bb);
		assertEquals(null, returned);
		Object returned2 = serlzer.objectFromByteBuffer(bb);
		assertEquals(null, returned2);
	}

	@Test
	public void testSerializationOfString() throws NotSerializableException, StreamCorruptedException {
		String testString = new String("Hi There World");
		ASerializer serlzer = new ASerializer();
		ByteBuffer bb = serlzer.outputBufferFromObject(testString);

		Object returned = serlzer.objectFromByteBuffer(bb);
		assertEquals("Hi There World", returned);
	}

	@Test
	public void testSerializationOfStringDoubleRead() throws NotSerializableException, StreamCorruptedException {
		String testString = new String("Hi There World");
		ASerializer serlzer = new ASerializer();
		ByteBuffer bb = serlzer.outputBufferFromObject(testString);

		Object returned = serlzer.objectFromByteBuffer(bb);
		assertEquals("Hi There World", returned);
		Object returned2 = serlzer.objectFromByteBuffer(bb);
		assertEquals("Hi There World", returned2);
	}

	@Test
	public void testSerializationOfArrayListOneNull() throws NotSerializableException, StreamCorruptedException {
		ArrayList<Integer> arraylist1 = new ArrayList<Integer>();
		ACustomSerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add(null);
		// arraylist1.add(null);
		// arraylist1.add(2);
		// arraylist1.add(23);

		serializer = new ACustomSerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist1, output);

	}

	@Test
	public void testSerializationOfArrayListTwoNull() throws NotSerializableException, StreamCorruptedException {
		ArrayList<Integer> arraylist1 = new ArrayList<Integer>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add(null);
		arraylist1.add(null);
		// arraylist1.add(2);
		// arraylist1.add(23);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist1, output);

	}

	@Test
	public void testSerializationOfArrayListInt1() throws NotSerializableException, StreamCorruptedException {
		ArrayList<Integer> arraylist1 = new ArrayList<Integer>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add(12);
		// arraylist1.add(null);
		// arraylist1.add(2);
		// arraylist1.add(23);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist1, output);
	}

	@Test
	public void testSerializationOfArrayListInt2() throws NotSerializableException, StreamCorruptedException {
		ArrayList<Integer> arraylist1 = new ArrayList<Integer>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add(12);
		arraylist1.add(13);
		arraylist1.add(14);
		// arraylist1.add(23);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist1, output);
	}

	@Test
	public void testSerializationOfArrayListInt3() throws NotSerializableException, StreamCorruptedException {
		ArrayList<Integer> arraylist1 = new ArrayList<Integer>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add(null);
		arraylist1.add(13);
		arraylist1.add(14);
		// arraylist1.add(23);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist1, output);
	}

	@Test
	public void testSerializationOfArrayListInt4() throws NotSerializableException, StreamCorruptedException {
		ArrayList<Integer> arraylist1 = new ArrayList<Integer>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add(12);
		arraylist1.add(null);
		arraylist1.add(14);
		// arraylist1.add(23);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist1, output);
	}

	@Test
	public void testSerializationOfArrayListInt5() throws NotSerializableException, StreamCorruptedException {
		ArrayList<Integer> arraylist1 = new ArrayList<Integer>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add(12);
		arraylist1.add(13);
		arraylist1.add(null);
		// arraylist1.add(23);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist1, output);
	}

	@Test
	public void testSerializationOfArrayListInt6() throws NotSerializableException, StreamCorruptedException {
		ArrayList<Integer> arraylist1 = new ArrayList<Integer>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		// arraylist1.add(null);
		arraylist1.add(13);
		// arraylist1.add(null);
		arraylist1.add(23);

		serializer = new ACustomSerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist1, output);
	}

	@Test
	public void testSerializationOfArrayListString1() throws NotSerializableException, StreamCorruptedException {
		ArrayList<String> arraylist1 = new ArrayList<String>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add("12");
		arraylist1.add("cat");
		arraylist1.add(null);
		arraylist1.add("the cow jumped over the moon");

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist1, output);
	}

	@Test
	public void testSerializationOfArrayListString2() throws NotSerializableException, StreamCorruptedException {
		ArrayList<String> arraylist1 = new ArrayList<String>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		String cat = "cat";

		arraylist1.add("12");
		arraylist1.add("12");
		arraylist1.add(new String("cat"));
		arraylist1.add(cat);
		arraylist1.add(cat);
		arraylist1.add("cheese");
		arraylist1.add("the cow jumped over the moon");

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		System.out.println("Expected: " + arraylist1);
		System.out.println("     Got: " + output);
		System.out.println(
				"(ArrayList<String>)output).get(0): " + System.identityHashCode(((ArrayList<String>) output).get(0)));
		System.out.println(
				"(ArrayList<String>)output).get(1): " + System.identityHashCode(((ArrayList<String>) output).get(1)));
		System.out.println(
				"(ArrayList<String>)output).get(0): " + System.identityHashCode(((ArrayList<String>) output).get(2)));
		System.out.println(
				"(ArrayList<String>)output).get(1): " + System.identityHashCode(((ArrayList<String>) output).get(3)));

		assertTrue(((ArrayList<String>) output).get(0) == ((ArrayList<String>) output).get(1));
		assertTrue(((ArrayList<String>) output).get(2) != ((ArrayList<String>) output).get(3));
		assertTrue(((ArrayList<String>) output).get(3) == ((ArrayList<String>) output).get(4));
		assertEquals(arraylist1, output);

	}

	@Test
	public void testSerializationOfArrayArrayListString2() throws NotSerializableException, StreamCorruptedException {
		ArrayList<String> arraylist1 = new ArrayList<String>();
		ArrayList<ArrayList<String>> arraylist2 = new ArrayList<ArrayList<String>>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add("12");
		arraylist1.add("cat");
		arraylist1.add("cheese");
		arraylist1.add("the cow jumped over the moon");

		arraylist2.add(arraylist1);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist2);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist2, output);
	}

	@Test
	public void testSerializationOfArrayArrayListString3() throws NotSerializableException, StreamCorruptedException {
		ArrayList<String> arraylist1 = new ArrayList<String>();
		ArrayList<ArrayList<String>> arraylist2 = new ArrayList<ArrayList<String>>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add("12");
		arraylist1.add("cat");
		arraylist1.add("cheese");
		arraylist1.add("the cow jumped over the moon");

		arraylist2.add(arraylist1);
		arraylist2.add(arraylist1);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist2);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist2, output);
	}

	@Test
	public void testSerializationOfArrayArrayListString4() throws NotSerializableException, StreamCorruptedException {
		ArrayList<String> arraylist1 = new ArrayList<String>();
		ArrayList<ArrayList<String>> arraylist2 = new ArrayList<ArrayList<String>>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add("12");
		arraylist1.add("cat");
		arraylist1.add("cheese");
		arraylist1.add("the cow jumped over the moon");

		arraylist2.add(null);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist2);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist2, output);
	}

	@Test
	public void testSerializationOfArrayArrayListString5() throws NotSerializableException, StreamCorruptedException {
		ArrayList<String> arraylist1 = new ArrayList<String>();
		ArrayList<ArrayList<String>> arraylist2 = new ArrayList<ArrayList<String>>();
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		arraylist1.add("12");
		arraylist1.add("cat");
		arraylist1.add("cheese");
		arraylist1.add("the cow jumped over the moon");

		arraylist2.add(arraylist1);
		arraylist2.add(null);
		arraylist2.add(arraylist1);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylist2);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylist2, output);
	}

	@Test
	public void testSerializationOfNestedArrayListInt() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		ArrayList<Integer> arraylist1 = new ArrayList<Integer>();
		arraylist1.add(11);

		ArrayList<Integer> arraylist2 = new ArrayList<Integer>();
		arraylist2.add(12);
		arraylist2.add(13);

		ArrayList<Integer> arraylist3 = new ArrayList<Integer>();
		arraylist3.add(14);
		arraylist3.add(15);
		arraylist3.add(16);

		ArrayList<ArrayList<Integer>> arraylistlist = new ArrayList<ArrayList<Integer>>();
		arraylistlist.add(arraylist3);
		arraylistlist.add(arraylist2);
		arraylistlist.add(arraylist1);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(arraylistlist);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(arraylistlist, output);
	}

	@Test
	public void testSerializationOfNestedLinkedListInt() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		LinkedList<Integer> LinkedList1 = new LinkedList<Integer>();
		LinkedList1.add(11);

		LinkedList<Integer> LinkedList2 = new LinkedList<Integer>();
		LinkedList2.add(12);
		LinkedList2.add(13);

		LinkedList<Integer> LinkedList3 = new LinkedList<Integer>();
		LinkedList3.add(14);
		LinkedList3.add(15);
		LinkedList3.add(16);

		LinkedList<LinkedList<Integer>> LinkedListlist1 = new LinkedList<LinkedList<Integer>>();
		LinkedListlist1.add(LinkedList3);
		LinkedListlist1.add(LinkedList2);
		LinkedListlist1.add(LinkedList1);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(LinkedListlist1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(LinkedListlist1, output);

		LinkedList<LinkedList<Integer>> LinkedListlist2 = new LinkedList<LinkedList<Integer>>();
		LinkedListlist2.add(LinkedList1);
		LinkedListlist2.add(LinkedList2);
		LinkedListlist2.add(LinkedList3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(LinkedListlist2);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(LinkedListlist2, output);

		LinkedList<LinkedList<Integer>> LinkedListlist3 = new LinkedList<LinkedList<Integer>>();
		LinkedListlist3.add(LinkedList2);
		LinkedListlist3.add(LinkedList1);
		LinkedListlist3.add(LinkedList3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(LinkedListlist3);
		output = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(LinkedListlist3, output);
	}

	@Test
	public void testSerializationOfNestedStackInt() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output1, output2, output3;

		Stack<Integer> Stack1 = new Stack<Integer>();
		Stack1.add(11);

		Stack<Integer> Stack2 = new Stack<Integer>();
		Stack2.add(12);
		Stack2.add(13);

		Stack<Integer> Stack3 = new Stack<Integer>();
		Stack3.add(14);
		Stack3.add(15);
		Stack3.add(16);

		Stack<Stack<Integer>> Stacklist1 = new Stack<Stack<Integer>>();
		Stacklist1.add(Stack3);
		Stacklist1.add(Stack2);
		Stacklist1.add(Stack1);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(Stacklist1);
		output1 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(Stacklist1, output1);

		Stack<Stack<Integer>> Stacklist2 = new Stack<Stack<Integer>>();
		Stacklist2.add(Stack1);
		Stacklist2.add(Stack2);
		Stacklist2.add(Stack3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(Stacklist2);
		output2 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(Stacklist2, output2);

		Stack<Stack<Integer>> Stacklist3 = new Stack<Stack<Integer>>();
		Stacklist3.add(Stack2);
		Stacklist3.add(Stack1);
		Stacklist3.add(Stack3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(Stacklist3);
		output3 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(Stacklist3, output3);
	}

	@Test
	public void testSerializationOfNestedDequeInt() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output1, output2, output3;

		ArrayDeque<Integer> ArrayDeque1 = new ArrayDeque<Integer>();
		ArrayDeque1.add(11);

		ArrayDeque<Integer> ArrayDeque2 = new ArrayDeque<Integer>();
		ArrayDeque2.add(12);
		ArrayDeque2.add(13);

		ArrayDeque<Integer> ArrayDeque3 = new ArrayDeque<Integer>();
		ArrayDeque3.add(14);
		ArrayDeque3.add(15);
		ArrayDeque3.add(16);

		ArrayDeque<ArrayDeque<Integer>> ArrayDequelist1 = new ArrayDeque<ArrayDeque<Integer>>();
		ArrayDequelist1.add(ArrayDeque3);
		ArrayDequelist1.add(ArrayDeque2);
		ArrayDequelist1.add(ArrayDeque1);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(ArrayDequelist1);
		output1 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(ArrayDequelist1.toString(), output1.toString());

		ArrayDeque<ArrayDeque<Integer>> ArrayDequelist2 = new ArrayDeque<ArrayDeque<Integer>>();
		ArrayDequelist2.add(ArrayDeque1);
		ArrayDequelist2.add(ArrayDeque2);
		ArrayDequelist2.add(ArrayDeque3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(ArrayDequelist2);
		output2 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(ArrayDequelist2.toString(), output2.toString());

		ArrayDeque<ArrayDeque<Integer>> ArrayDequelist3 = new ArrayDeque<ArrayDeque<Integer>>();
		ArrayDequelist3.add(ArrayDeque2);
		ArrayDequelist3.add(ArrayDeque1);
		ArrayDequelist3.add(ArrayDeque3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(ArrayDequelist3);
		output3 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(ArrayDequelist3.toString(), output3.toString());
	}

	@Test
	public void testSerializationOfNestedHashSetInt() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output1, output2, output3;

		HashSet<Integer> HashSet1 = new HashSet<Integer>();
		HashSet1.add(11);

		HashSet<Integer> HashSet2 = new HashSet<Integer>();
		HashSet2.add(12);
		HashSet2.add(13);

		HashSet<Integer> HashSet3 = new HashSet<Integer>();
		HashSet3.add(14);
		HashSet3.add(15);
		HashSet3.add(16);

		HashSet<HashSet<Integer>> HashSetlist1 = new HashSet<HashSet<Integer>>();
		HashSetlist1.add(HashSet3);
		HashSetlist1.add(HashSet2);
		HashSetlist1.add(HashSet1);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(HashSetlist1);
		output1 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(HashSetlist1.toString(), output1.toString());

		HashSet<HashSet<Integer>> HashSetlist2 = new HashSet<HashSet<Integer>>();
		HashSetlist2.add(HashSet1);
		HashSetlist2.add(HashSet2);
		HashSetlist2.add(HashSet3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(HashSetlist2);
		output2 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(HashSetlist2.toString(), output2.toString());

		HashSet<HashSet<Integer>> HashSetlist3 = new HashSet<HashSet<Integer>>();
		HashSetlist3.add(HashSet2);
		HashSetlist3.add(HashSet1);
		HashSetlist3.add(HashSet3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(HashSetlist3);
		output3 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(HashSetlist3.toString(), output3.toString());
	}

	@Test
	public void testSerializationOfLinkedHashSetInt() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output1, output2, output3;

		LinkedHashSet<Integer> LinkedHashSet1 = new LinkedHashSet<Integer>();
		LinkedHashSet1.add(11);

		LinkedHashSet<Integer> LinkedHashSet2 = new LinkedHashSet<Integer>();
		LinkedHashSet2.add(12);
		LinkedHashSet2.add(13);

		LinkedHashSet<Integer> LinkedHashSet3 = new LinkedHashSet<Integer>();
		LinkedHashSet3.add(14);
		LinkedHashSet3.add(15);
		LinkedHashSet3.add(16);

		LinkedHashSet<LinkedHashSet<Integer>> LinkedHashSetlist1 = new LinkedHashSet<LinkedHashSet<Integer>>();
		LinkedHashSetlist1.add(LinkedHashSet3);
		LinkedHashSetlist1.add(LinkedHashSet2);
		LinkedHashSetlist1.add(LinkedHashSet1);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(LinkedHashSetlist1);
		output1 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(LinkedHashSetlist1.toString(), output1.toString());

		LinkedHashSet<LinkedHashSet<Integer>> LinkedHashSetlist2 = new LinkedHashSet<LinkedHashSet<Integer>>();
		LinkedHashSetlist2.add(LinkedHashSet1);
		LinkedHashSetlist2.add(LinkedHashSet2);
		LinkedHashSetlist2.add(LinkedHashSet3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(LinkedHashSetlist2);
		output2 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(LinkedHashSetlist2.toString(), output2.toString());

		LinkedHashSet<LinkedHashSet<Integer>> LinkedHashSetlist3 = new LinkedHashSet<LinkedHashSet<Integer>>();
		LinkedHashSetlist3.add(LinkedHashSet2);
		LinkedHashSetlist3.add(LinkedHashSet1);
		LinkedHashSetlist3.add(LinkedHashSet3);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(LinkedHashSetlist3);
		output3 = serializer.objectFromByteBuffer(bytebuffer);

		assertEquals(LinkedHashSetlist3.toString(), output3.toString());
	}

	@Test
	public void testSerializationOfHashMapIntString() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(22, "twentyTwo");
		map.put(null, "12");
		map.put(1, "one");

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(map);
		output = serializer.objectFromByteBuffer(bytebuffer);

		System.out.println(output);
		assertEquals(map, output);
	}

	@Test
	public void testSerializationOfHashMapIntString2() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(22, "twentyTwo");
		map.put(null, "12");
		map.put(1, "one");

		HashMap<String, HashMap<Integer, String>> map2 = new HashMap<String, HashMap<Integer, String>>();

		map2.put("OneHundred", map);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(map2);
		output = serializer.objectFromByteBuffer(bytebuffer);

		System.out.println(" input: " + map2);
		System.out.println("output: " + output);
		System.out.println(" input class: " + map2.getClass().getName());
		System.out.println("output class: " + output.getClass().getName());

		assertEquals(map2, output);
	}

	@Test
	public void testSerializationOfTreeMap() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		map.put(22, "twentyTwo");
		map.put(5, "12");
		map.put(1, "one");

		TreeMap<String, TreeMap<Integer, String>> map2 = new TreeMap<String, TreeMap<Integer, String>>();

		map2.put("OneHundred", map);

		serializer = new ACustomSerializer();
		bytebuffer = serializer.outputBufferFromObject(map2);
		output = serializer.objectFromByteBuffer(bytebuffer);

		System.out.println(" input: " + map2);
		System.out.println("output: " + output);
		System.out.println(" input class: " + map2.getClass().getName());
		System.out.println("output class: " + output.getClass().getName());

		assertEquals(map2, output);
	}

	@Test
	public void testSerializationOfLinkedHashMap() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(22, "twentyTwo");
		map.put(5, "12");
		map.put(1, "one");

		LinkedHashMap<String, LinkedHashMap<Integer, String>> map2 = new LinkedHashMap<String, LinkedHashMap<Integer, String>>();

		map2.put("OneHundred", map);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(map2);
		output = serializer.objectFromByteBuffer(bytebuffer);

		System.out.println(" input: " + map2);
		System.out.println("output: " + output);
		System.out.println(" input class: " + map2.getClass().getName());
		System.out.println("output class: " + output.getClass().getName());

		assertEquals(map2, output);
	}

	@Test
	public void testSerializationOfLinkedHashMap2() throws NotSerializableException, StreamCorruptedException {
		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(22, "twentyTwo");
		map.put(5, "12");
		map.put(1, "one");
		map.put(2, "two");

		LinkedHashMap<String, LinkedHashMap<Integer, String>> map2 = new LinkedHashMap<String, LinkedHashMap<Integer, String>>();

		map2.put("OneHundred", map);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(map2);
		output = serializer.objectFromByteBuffer(bytebuffer);

		System.out.println("Size of Byte Buffer: " + bytebuffer.limit());
		System.out.println(-1 * System.identityHashCode(output));

		System.out.println(" input: " + map2);
		System.out.println("output: " + output);
		System.out.println(" input class: " + map2.getClass().getName());
		System.out.println("output class: " + output.getClass().getName());

		assertEquals(map2, output);
	}

	@Test
	public void testSerializationOfNonTree1() throws NotSerializableException, StreamCorruptedException {

		ASerializer serializer;
		ByteBuffer bytebuffer;
		Object output;

		ArrayList<ArrayList<Integer>> map1 = new ArrayList<ArrayList<Integer>>();

		ArrayList<Integer> map2a = new ArrayList<Integer>();
		ArrayList<Integer> map2b = new ArrayList<Integer>();

		Integer anInt1 = new Integer(42);
		Integer anInt2 = new Integer(43);

		map2a.add(anInt1);
		map2a.add(anInt1);
		map2b.add(anInt1);
		map2a.add(anInt2);
		map2b.add(anInt2);

		map1.add(map2a);
		map1.add(map2b);

		serializer = new ASerializer();
		bytebuffer = serializer.outputBufferFromObject(map1);
		output = serializer.objectFromByteBuffer(bytebuffer);

		// System.out.println("Size of Byte Buffer: "+bytebuffer.limit());
		// System.out.println(System.identityHashCode(((ArrayList)((ArrayList)output).get(0)).get(0)));
		// System.out.println(System.identityHashCode(((ArrayList)((ArrayList)output).get(1)).get(0)));

		// System.out.println(" input: "+map1);
		// System.out.println("output: "+output);
		// System.out.println(" input class: "+map1.getClass().getName());
		// System.out.println("output class: "+output.getClass().getName());

		assertEquals(map1, output);
		assertEquals(System.identityHashCode(((ArrayList) ((ArrayList) output).get(0)).get(0)),
				System.identityHashCode(((ArrayList) ((ArrayList) output).get(1)).get(0)));
	}

	@Test
	public void testCustomSerializer1() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		Object object = 5;
		ByteBuffer buffer = cs.outputBufferFromObject(object);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		// System.out.println(" input: "+object);
		// System.out.println("output: "+readVal);
		// System.out.println(" input class: "+object.getClass().getName());
		// System.out.println("output class: "+readVal.getClass().getName());

		assertEquals(object.getClass().getName(), readVal.getClass().getName());
		assertEquals(object, readVal);

		// translate(serializer, 5.5);
		// translate(serializer, "hello world");
		// translate(serializer, true);
	}

	@Test
	public void testCustomSerializer2() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		Object object = 5.5;
		ByteBuffer buffer = cs.outputBufferFromObject(object);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		assertEquals(object.getClass().getName(), readVal.getClass().getName());
		assertEquals(object, readVal);

		// translate(serializer, "hello world");
		// translate(serializer, true);
	}

	@Test
	public void testCustomSerializer3() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		Object object = "hello world";
		ByteBuffer buffer = cs.outputBufferFromObject(object);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		assertEquals(object.getClass().getName(), readVal.getClass().getName());
		assertEquals(object, readVal);

		// translate(serializer, true);
	}

	@Test
	public void testCustomSerializer4() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		Object object = true;
		ByteBuffer buffer = cs.outputBufferFromObject(object);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		assertEquals(object.getClass().getName(), readVal.getClass().getName());
		assertEquals(object, readVal);
	}

	@Test
	public void testCustomSerializer5() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		Object object = Color.RED;
		ByteBuffer buffer = cs.outputBufferFromObject(object);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		// assertEquals(object.getClass().getName(),
		// readVal.getClass().getName());
		assertEquals(((Enum) object).toString(), readVal);
	}

	@Test
	public void testCustomSerializer6() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		Object[] object = {};
		ByteBuffer buffer = cs.outputBufferFromObject(object);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		// assertEquals(object.getClass().getName(),
		// readVal.getClass().getName());
		assertEquals(object.getClass(), readVal.getClass());
		assertEquals(((Object[]) object).length, ((Object[]) readVal).length);
		for (int i = 0; i < ((Object[]) object).length; i++) {
			assertEquals(((Object[]) object)[i], ((Object[]) readVal)[i]);
		}
	}

	@Test
	public void testCustomSerializer7() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		Object[] object = { "Hello World", "Goodbye World", Color.GREEN };
		ByteBuffer buffer = cs.outputBufferFromObject(object);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		// assertEquals(object.getClass().getName(),
		// readVal.getClass().getName());
		assertEquals(object.getClass(), readVal.getClass());
		assertEquals(((Object[]) object).length, ((Object[]) readVal).length);
		for (int i = 0; i < ((Object[]) object).length; i++) {
			assertEquals(((Object[]) object)[i].toString(), ((Object[]) readVal)[i].toString());
		}
	}

	@Test
	public void testCustomSerializer8() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		List list = new ArrayList();
		list.add("Hello world");
		list.add(3);
		list.add(Color.BLUE);
		list.add(Color.GREEN);
		list.add(Color.GREEN);
		list.add(Color.GREEN);
		list.add(null);

		ByteBuffer buffer = cs.outputBufferFromObject(list);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		// assertEquals(object.getClass().getName(),
		// readVal.getClass().getName());
		assertEquals(list.toString(), readVal.toString());
		// System.out.println(readVal);
		// for(int i = 0; i < (list).size() ; i++) {
		// assertEquals(list.get(i).toString(),
		// (((List)readVal).get(i)).toString());
		// }
	}

	@Test
	public void testCustomSerializer9() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		Map object = new HashMap();
		object.put("greeting", "ni hao");
		object.put(5, 4.0);

		ByteBuffer buffer = cs.outputBufferFromObject(object);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		assertEquals(object, readVal);
	}

	@Test
	public void testCustomSerializer10() throws NotSerializableException, StreamCorruptedException {
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

		ACustomSerializer serializer;
		serializer = cs;

		Set<String> object = new HashSet();
		object.add("Hello world");
		object.add("Goodbye world");

		ByteBuffer buffer = cs.outputBufferFromObject(object);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		assertEquals(object, readVal);
	}

	@Test
	public void testCustomSerializer11() throws NotSerializableException, StreamCorruptedException {
		ACustomSerializer cs = new ACustomSerializer();
		ACustomSerializer serializer;
		serializer = cs;

		List list = new ArrayList();
		list.add("Hello world");
		list.add(3);
		// list.add(Color.BLUE);
		// list.add(Color.GREEN);
		// list.add(Color.GREEN);
		list.add(null);

		Map map = new HashMap();
		map.put("greeting", "ni hao");
		map.put(5, 4.0);

		Set<String> set = new HashSet();
		set.add("Hello world");
		set.add("Goodbye world");

		list.add(set);
		list.add(map);

		ByteBuffer buffer = cs.outputBufferFromObject(list);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		assertEquals(list, readVal);
	}

	@Test
	public void testCustomSerializer12() throws NotSerializableException, StreamCorruptedException {
		ACustomSerializer serializer = new ACustomSerializer();

		List list = new ArrayList();
		list.add("Hello world");
		list.add(3);
		// list.add(Color.BLUE);
		// list.add(Color.GREEN);
		// list.add(Color.GREEN);
		list.add(null);

		Map map = new HashMap();
		map.put("greeting", "ni hao");
		map.put(5, 4.0);

		Set<String> set = new HashSet();
		set.add("Hello world");
		set.add("Goodbye world");

		list.add(set);
		// list.add(map);

		List recursive = new ArrayList();
		recursive.add(null);
		// Object[] values = { "" };
		// recursive.add(values);
		recursive.add(recursive);
		recursive.add(list);

		ByteBuffer buffer = serializer.outputBufferFromObject(recursive);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		System.out.println(recursive);
		System.out.println(readVal);
		assertEquals(recursive.toString(), readVal.toString());
	}

	@Test
	public void testBeans1() throws NotSerializableException, StreamCorruptedException {
		ACustomSerializer serializer = new ACustomSerializer();

		BMISpreadsheet bmi = new AnotherBMISpreadsheet();
		bmi.setHeight(2.0);
		bmi.setMale(true);

		// bmi = (BMISpreadsheet) new Object();

		ByteBuffer buffer = serializer.outputBufferFromObject(bmi);
		Object readVal = serializer.objectFromByteBuffer(buffer);
		System.out.println("readVal:" + readVal);

		String orig = bmi.toString().substring(0, bmi.toString().indexOf('@'))
				+ bmi.toString().substring(bmi.toString().indexOf('('), bmi.toString().length());
		String out = readVal.toString().substring(0, readVal.toString().indexOf('@'))
				+ readVal.toString().substring(readVal.toString().indexOf('('), readVal.toString().length());

		System.out.println(orig);
		System.out.println(out);

		assertEquals(orig, out);
	}

	@Test
	public void testBeans2() throws NotSerializableException, StreamCorruptedException {
		ACustomSerializer serializer = new ACustomSerializer();

		NamedBMISpreadsheet bmi = new ANamedBMISpreadsheet();
		bmi.setName("Joe Doe");
		bmi.setHeight(2.0);

		// bmi = (BMISpreadsheet) new Object();

		ByteBuffer buffer = serializer.outputBufferFromObject(bmi);
		Object readVal = serializer.objectFromByteBuffer(buffer);
		System.out.println("readVal:" + readVal);

		String orig = bmi.toString().substring(0, bmi.toString().indexOf('@'))
				+ bmi.toString().substring(bmi.toString().indexOf('('), bmi.toString().length());
		String out = readVal.toString().substring(0, readVal.toString().indexOf('@'))
				+ readVal.toString().substring(readVal.toString().indexOf('('), readVal.toString().length());

		System.out.println(bmi);
		System.out.println(readVal);
		System.out.println(orig);
		System.out.println(out);

		assertEquals(orig, out);
	}

	@Test
	public void testBeans3() throws NotSerializableException, StreamCorruptedException {
		ACustomSerializer serializer = new ACustomSerializer();

		ObjectHistory objectHistory = new AnObjectHistory();
		// objectHistory.add(objectHistory);
		objectHistory.add(new AnObjectHistory());
		objectHistory.add("hello");

		ByteBuffer buffer = serializer.outputBufferFromObject(objectHistory);
		Object readVal = serializer.objectFromByteBuffer(buffer);

		String orig = objectHistory.toString().substring(0, objectHistory.toString().indexOf('@'));
		String out = readVal.toString().substring(0, readVal.toString().indexOf('@'));

		System.out.println(orig);
		System.out.println(out);
		assertEquals(orig, out);

		for (int i = 0; i < objectHistory.size(); i++) {

			String one = objectHistory.get(i).toString();
			if (one.indexOf('@') > -1)
				one = one.substring(0, objectHistory.toString().indexOf('@'));
			System.out.println("one-" + one);

			String two = ((ObjectHistory) readVal).get(i).toString();
			if (two.indexOf('@') > -1)
				two = two.substring(0, objectHistory.toString().indexOf('@'));
			System.out.println("two-" + two);

			// System.out.println(one);
			// System.out.println(two);
			// assertEquals(one, two);
		}
	}

}
