package serialization2;

//import java.beans.Introspector;
//import java.beans.PropertyDescriptor;
import java.beans.BeanInfo;
//import java.beans.IntrospectionException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
//import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
//import java.util.Locale;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;

import inputport.datacomm.duplex.buffer.AGenericDuplexBufferClientInputPort;
import util.trace.Tracer;

public class ACustomSerializer extends ASerializer implements CustomSerializer, serialization.Serializer, SerializerFactory {

	HashMap<Class, ASerializer> registrations = new HashMap<Class, ASerializer>();
	HashMap<Class, Class> customDeSerializations = new HashMap<Class, Class>();

	ASerializer s = new ASerializer();
	ByteBuffer b = ByteBuffer.allocate(0);

	void register() {
		registrations.put(null, new ASerializer());
		registrations.put(Integer.class, new ASerializer());
		registrations.put(Double.class, new ASerializer());
		registrations.put(double.class, new ASerializer());
		registrations.put(String.class, new ASerializer());
		registrations.put(Boolean.class, new ASerializer());
		registrations.put(HashSet.class, new ASerializer());
		registrations.put(ArrayList.class, new ASerializer());
		registrations.put(Vector.class, new ASerializer());
		registrations.put(ArrayDeque.class, new ASerializer());
		registrations.put(Stack.class, new ASerializer());
		registrations.put(LinkedList.class, new ASerializer());
		registrations.put(LinkedHashSet.class, new ASerializer());
		registrations.put(LinkedHashMap.class, new ASerializer());
		registrations.put(HashMap.class, new ASerializer());
		registrations.put(TreeMap.class, new ASerializer());
		registrations.put(Hashtable.class, new ASerializer());
		registrations.put(Enum.class, new ASerializer());
		registrations.put(Object[].class, new AnArraySerializer());
		registrations.put(BeanInfo.class, new ABeanSerializer());
	}

	public void registerSerializer(Class c, ASerializer s) {
		registrations.put(c, s);
	}

	public void registerDeserializingClass(Class input, Class output) {
		customDeSerializations.put(input, output);
	}

	@Override
	public ByteBuffer outputBufferFromObject(Object object) throws NotSerializableException {
		register();
		if (object == null) {
			b = s.outputBufferFromObject(null);
			return b;
		}
		if (!(object instanceof java.io.Serializable) && !(object instanceof Object[])) {
			System.out.println("Its not serializable!");
			throw new java.io.NotSerializableException();
		}

		if (object.getClass().isEnum()) {
			object = ((Enum) object).name();
		}

		
		if (!registrations.containsKey(object.getClass()) 
				) {
			
			System.out.println("found a bean - "+object.getClass()+" "+object.getClass().getName());
			System.out.println("found a bean - "+object);
			
			s = registrations.get(BeanInfo.class);
			System.out.println("The objects Cust Serial class: "+object.getClass());

			System.out.println(s.getClass());
			System.out.println(object);
			System.out.println(s);
			b = s.outputBufferFromObject(object);

			return b;

		} else {

			try {

				System.out.println("The objects Cust Serial class: " + object.getClass());

				s = registrations.get(object.getClass());
				System.out.println(object);
				System.out.println(s);
				b = s.outputBufferFromObject(object);

				return b;

			} catch (NotSerializableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Object objectFromByteBuffer(ByteBuffer inputBuffer) {

		try {
			Object obj = s.objectFromByteBuffer(inputBuffer);
			System.out.println("ACustomSerializer - returing object: ");
			System.out.println("obj:"+obj);
			return obj;
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Serializer createSerializer() {
		// TODO Auto-generated method stub
		Tracer.showInfo(true);
		Tracer.setKeywordPrintStatus(AGenericDuplexBufferClientInputPort.class, true);
		ACustomSerializer me = new ACustomSerializer();
		return me;
	}

	@Override
	public Object objectFromInputBuffer(ByteBuffer inputBuffer) throws StreamCorruptedException {
		return this.objectFromByteBuffer(inputBuffer);
	}
}
