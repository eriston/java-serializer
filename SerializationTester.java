package serialization2;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import inputport.datacomm.duplex.buffer.AGenericDuplexBufferClientInputPort;
import serialization.Serializer;
import serialization.SerializerSelector;
import serialization.examples.ANamedBMISpreadsheet;
import serialization.examples.AStringHistory;
import serialization.examples.AnObjectHistory;
import serialization.examples.AnotherBMISpreadsheet;
import serialization.examples.BMISpreadsheet;
import serialization.examples.NamedBMISpreadsheet;
import serialization.examples.ObjectHistory;
import serialization.examples.StringHistory;
import util.trace.Tracer;

public class SerializationTester {

	public static void main(String[] args) {

		testSerialization();
	}
	
	enum Color {RED, GREEN, BLUE}

	public static void testSerialization() {
		
		ACustomSerializer cs = new ACustomSerializer();
		ACustomSerializer serializer;

		serializer = cs;
		Tracer.showInfo(true);
		Tracer.setKeywordPrintStatus(AGenericDuplexBufferClientInputPort.class, true);
		
		Object o = new Object();
		System.out.println(o.getClass().getName());
		Object[] o2 = {};
		System.out.println(o2.getClass().getName());
		Integer i = new Integer(1);
		System.out.println(i.getClass().getName());
		Integer[] i2 = {};
		System.out.println(i2.getClass().getName());
		System.out.println(Color.RED.getClass().isEnum());
		System.out.println(Color.RED.getClass().getEnumConstants().toString());
		for(Object o3 : Color.RED.getClass().getEnumConstants()) {
			System.out.print(o3+" ");
		}

		//part 1

		translate(serializer, 5);
		translate(serializer, 5.5);
		translate(serializer, "hello world");
		translate(serializer, true);
		
		translate(serializer, Color.RED);

		Object[] values = { };
		System.out.println("Object[]: "+values.getClass().getName());
		System.out.println("Object[]: "+Object[].class.getName());
		translate(serializer, values);
		Object[] values2 = { "Hello World", "Goodbye World", Color.GREEN };
		
		List list = new ArrayList();
		System.out.println("\n\nlist: "+list.getClass().toString());
		list.add("Hello world");
		list.add(3);
		list.add(Color.BLUE);
		list.add(Color.GREEN);
		list.add(Color.GREEN);
		list.add(null);
		translate(serializer, list);
		
		
		Map map = new HashMap();
		map.put("greeting", "ni hao");
		map.put(5, 4.0);
		translate(serializer, map);
		
		
		Set<String> set = new HashSet();
		set.add("Hello world");
		set.add("Goodbye world");
		translate(serializer, set);
		
		
		list.add(set);
		list.add(map);
		translate(serializer, list);
		
		// part 2
		
		List recursive = new ArrayList();
		recursive.add(null);
		recursive.add(values2);
		recursive.add(recursive);
		recursive.add(list);
		translate(serializer, recursive);
		
		List recursiveList = new ArrayList();
		recursiveList.add(recursiveList);
		translate(serializer, recursiveList);
		
		
		BMISpreadsheet bmi = new AnotherBMISpreadsheet();
		bmi.setHeight(3.0);
		bmi.setMale(true);
	    translate(serializer, bmi);
	    
	    
		NamedBMISpreadsheet namedBMI = new ANamedBMISpreadsheet();
		namedBMI.setName("Joe Doe");
		namedBMI.setHeight(2.0);
		translate(serializer, namedBMI);
		
		
		StringHistory stringHistory = new AStringHistory();
		stringHistory.add("James Dean");
		stringHistory.add("Joe Doe");
		stringHistory.add("Jane Smith");
		stringHistory.add("John Smith");
		translate(serializer, stringHistory);

		
		ObjectHistory objectHistory = new AnObjectHistory();
		objectHistory.add(new AnObjectHistory());
		objectHistory.add("hello");
		translate(serializer, objectHistory);
	}

	static void translate(ACustomSerializer serializer, Object object) {
		try {
			ByteBuffer buffer = serializer.outputBufferFromObject(object);
			Object readVal = serializer.objectFromByteBuffer(buffer);
			System.out.println("            : " + object);
			System.out.println("Deserialized: " + readVal);
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
