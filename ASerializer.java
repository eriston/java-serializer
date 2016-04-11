package serialization2;

import java.awt.Point;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class ASerializer implements serialization2.Serializer {
	
	
	
	final int INT_SIZE = Integer.SIZE / Byte.SIZE;
	final int DOUBLE_SIZE = Double.SIZE / Byte.SIZE;
	
	static HashMap<Integer, Integer> alreadySerialized = new HashMap<Integer, Integer>();

	static HashMap<Integer, Object> alreadyDeSerialized = new HashMap<Integer, Object>();
	
	static final boolean DEDUPLICATING = !false;
	
	
	private ACustomSerializer getNewCustomSerializer() {
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

		return cs;
	}
	
	
	public Object returnObject(ByteBuffer inputBuffer, Object obj, int objSystemId) {
		if(DEDUPLICATING) {
			if(   alreadyDeSerialized.containsKey(objSystemId) &&
				!(alreadyDeSerialized.get(objSystemId) instanceof Point)) {
				System.out.println("Returning Dup 2");
				inputBuffer.rewind();
				return (Object)alreadyDeSerialized.get(objSystemId);
			} if(alreadyDeSerialized.containsKey(objSystemId) &&
				(alreadyDeSerialized.get(objSystemId) instanceof Point)) {
				System.out.println("Returning Self Dup");
				alreadyDeSerialized.remove(objSystemId);
				alreadyDeSerialized.put(objSystemId, obj);
				inputBuffer.rewind();
				obj = obj;
				return obj;
			} else {
				System.out.println("Returning Original");
				alreadyDeSerialized.put(objSystemId, obj);
				inputBuffer.rewind();
				return obj;
			}
		} else {
			inputBuffer.rewind();
			return obj;
		}
		

	}
	
	public Object objectFromByteBuffer(ByteBuffer inputBuffer) throws StreamCorruptedException {

		Object obj = new Object();
		int incomingObjectId = inputBuffer.getInt();
		System.out.println("Incoming Object ID: "+incomingObjectId);
		
		System.out.println(alreadyDeSerialized);
		if(alreadyDeSerialized.containsKey(incomingObjectId)) {
			System.out.println("Returning Dup 1");
			return returnObject(inputBuffer, alreadyDeSerialized.get(incomingObjectId), incomingObjectId);
		} else {
			alreadyDeSerialized.put(incomingObjectId, new Point(0,0));
		}
		
		int incomingClassLength = inputBuffer.getInt();
		System.out.println("incomingObjectId: "+incomingObjectId);
		System.out.println("incomingClassLength: "+incomingClassLength);
		
		if(DEDUPLICATING) {
			System.out.println("incomingClassLength: "+incomingClassLength);
		}

		byte[] incomingClassNameBytes = new byte[incomingClassLength];
		inputBuffer.get(incomingClassNameBytes, 0, incomingClassLength);
		String decoded = new String();
		try {
			decoded = new String(incomingClassNameBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ("null".equals(decoded)) {
			return returnObject(inputBuffer, null, incomingObjectId);
		}
		if (Integer.class.getName().equals(decoded)) {
			int incomingInteger = inputBuffer.getInt();
			obj = new Integer(incomingInteger);

			return returnObject(inputBuffer, obj, incomingObjectId);
		}
		if (Double.class.getName().equals(decoded)) {
			double incomingInteger = inputBuffer.getDouble();
			obj = new Double(incomingInteger);

			return returnObject(inputBuffer, obj, incomingObjectId);
		}
		if (Boolean.class.getName().equals(decoded)) {
			int incomingInteger = inputBuffer.getInt();
			obj = new Boolean(true);
			if (incomingInteger == 0)
				obj = false;

			return returnObject(inputBuffer, obj, incomingObjectId);
		}
		if (String.class.getName().equals(decoded)) {
			int stringSize = inputBuffer.getInt();
			byte[] stringBytes = new byte[stringSize];
			inputBuffer.get(stringBytes, 0, stringSize);

			obj = new String();
			try {
				obj = new String(stringBytes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return returnObject(inputBuffer, obj, incomingObjectId);
		}

		try {

			if (Class.forName(decoded).newInstance() instanceof java.util.Collection ||
					Class.forName(decoded).newInstance() instanceof java.util.Set ||
					Class.forName(decoded).newInstance() instanceof java.util.HashSet) {
				obj = (Collection) Class.forName(decoded).newInstance();
				int elementsSize = inputBuffer.getInt();
				int elementsBytes = inputBuffer.getInt();
				System.out.println("Elements size: "+elementsSize);
				for (int i = 0; i < elementsSize; i++) {

					ASerializer deSer = getNewCustomSerializer();
					int numberOfDeSerializedObjects = alreadyDeSerialized.size();
					Object item = deSer.objectFromByteBuffer(inputBuffer.slice());
					if(numberOfDeSerializedObjects == alreadyDeSerialized.size()){
						inputBuffer.position((int) inputBuffer.position() + INT_SIZE);
						if(item instanceof Point) {
							((Collection)obj).add(obj);
						} else {
							((Collection)obj).add(item);
						}
						continue;
					}
					
					int newPosition = (int) inputBuffer.position();

					if (item == null) {
						newPosition += 2 * INT_SIZE + "null".getBytes().length + INT_SIZE;
					} else if (item instanceof java.lang.Integer || item instanceof java.lang.Boolean) {
						newPosition += 2 * INT_SIZE + "java.lang.Integer".getBytes().length + INT_SIZE;
					} else if (item instanceof java.lang.Double) {
						newPosition += 2 * INT_SIZE + "java.lang.Double".getBytes().length + DOUBLE_SIZE;
					} else if (item instanceof java.lang.String) {
						newPosition += 2 * INT_SIZE + "java.lang.String".getBytes().length + INT_SIZE
								+ ((String) item).length();
					} else 

					if (item instanceof java.util.Collection) {
						newPosition += 2*INT_SIZE + item.getClass().getName().toString().length() + INT_SIZE;

						inputBuffer.position(newPosition);
						int moveUp = inputBuffer.getInt();
						inputBuffer.position(inputBuffer.position() + moveUp);
					}
					if (!(item instanceof java.util.Collection) && newPosition <= inputBuffer.limit()) {
						inputBuffer.position(newPosition);
					}

					((Collection)obj).add(item);
				}
				return returnObject(inputBuffer, obj, incomingObjectId);
			}

			if (Class.forName(decoded).newInstance() instanceof java.util.Map ||
					Class.forName(decoded).newInstance() instanceof java.util.HashMap) {
				Map<Object, Object> obj_map = (Map) Class.forName(decoded).newInstance();
				int elementsBytes_map = inputBuffer.getInt();

				ASerializer deSer = getNewCustomSerializer();
				ArrayList keys = (ArrayList) deSer.objectFromByteBuffer(inputBuffer.slice());
				inputBuffer.position(inputBuffer.position() + elementsBytes_map);
				ArrayList values = (ArrayList) deSer.objectFromByteBuffer(inputBuffer.slice());
				
				for (int i = 0; i < keys.size(); i++) {
					obj_map.put(keys.get(i), values.get(i));
				}

				return returnObject(inputBuffer, obj_map, incomingObjectId);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Class not found Exception for "+decoded);
			System.exit(1);
			e.printStackTrace();
		}

		return returnObject(inputBuffer, null, incomingObjectId);
	}

	@Override
	public ByteBuffer outputBufferFromObject(Object object) throws NotSerializableException {

		ByteBuffer bb;

		if(object != null && object.getClass().isEnum()) {
			System.out.println("Enum Object1: " + "");
			System.out.println("Enum Object1: " + object);
			object = ((Enum)object).toString();
			System.out.println("Enum Object2: " + "");
			System.out.println("Enum Object2: " + object);
			
		}
		
		if(DEDUPLICATING) {
			int objectSystemId = System.identityHashCode(object);
			System.out.println("Object: "+object+" System.identityHashCode(object): "+System.identityHashCode(object));
			if(object!=null){System.out.println(object.getClass());};
			
			if(alreadySerialized.containsKey(objectSystemId)) {
				System.out.println("Found duplicate: "+objectSystemId);
				bb = ByteBuffer.allocate(1*(Integer.SIZE / Byte.SIZE));
				bb.putInt(System.identityHashCode(object));
				bb.flip();
				return bb;
			} else {
				System.out.println("Not Found duplicate: "+objectSystemId);
				alreadySerialized.put(objectSystemId, objectSystemId);
			}
		}
				

		if (object == null) {
			bb = ByteBuffer.allocate(1*(Integer.SIZE / Byte.SIZE) + Integer.SIZE / Byte.SIZE + 4 + Integer.SIZE / Byte.SIZE);
			bb.putInt(System.identityHashCode(object));
			System.out.println("Hash code for null: "+System.identityHashCode(object));
			bb.putInt(4);
			bb.put("null".getBytes());
			bb.putInt(0);
			bb.flip();
			return bb;
		}


		String objectClassString = object.getClass().getName();

		int objectClassStringSize = objectClassString.getBytes().length;

		if (Integer.class.getName().equals(objectClassString)) {
			bb = ByteBuffer.allocate(1*(Integer.SIZE / Byte.SIZE) + Integer.SIZE / Byte.SIZE + objectClassStringSize + Integer.SIZE / Byte.SIZE);
			bb.putInt(System.identityHashCode(object));
			bb.putInt(objectClassStringSize);
			bb.put(objectClassString.getBytes());
			bb.putInt((Integer) object);

		} else

		if (Double.class.getName().equals(objectClassString)) {
			bb = ByteBuffer.allocate(1*(Integer.SIZE / Byte.SIZE) + Integer.SIZE / Byte.SIZE + objectClassStringSize + Double.SIZE / Byte.SIZE);
			bb.putInt(System.identityHashCode(object));
			bb.putInt(objectClassStringSize);
			bb.put(objectClassString.getBytes());
			bb.putDouble((Double) object);

		} else

		if (Boolean.class.getName().equals(objectClassString)) {
			bb = ByteBuffer.allocate(1*(Integer.SIZE / Byte.SIZE) + Integer.SIZE / Byte.SIZE + objectClassStringSize + Integer.SIZE / Byte.SIZE);
			bb.putInt(System.identityHashCode(object));
			bb.putInt(objectClassStringSize);
			bb.put(objectClassString.getBytes());
			if ((Boolean) object == true)
				bb.putInt(1);
			else
				bb.putInt(0);

		} else

		if (String.class.getName().equals(objectClassString) /*|| object.getClass().isEnum()*/) {
			//System.out.flush();

			bb = ByteBuffer.allocate(1*(Integer.SIZE / Byte.SIZE) + Integer.SIZE / Byte.SIZE + objectClassStringSize + Integer.SIZE / Byte.SIZE
					+ ((String) object).getBytes().length);
			bb.putInt(System.identityHashCode(object));
			bb.putInt(objectClassStringSize);
			bb.put(String.class.getName().getBytes());
			bb.putInt(((String) object).getBytes().length);
			bb.put(((String) object).getBytes());

		} else

		if (object instanceof Collection<?>) {
			bb = ByteBuffer.allocate(1*(Integer.SIZE / Byte.SIZE) + Integer.SIZE / Byte.SIZE + Integer.SIZE / Byte.SIZE + objectClassStringSize
					+ Integer.SIZE / Byte.SIZE);
			bb.putInt(System.identityHashCode(object));
			bb.putInt(objectClassStringSize);
			bb.put(objectClassString.getBytes());
			bb.putInt(((Collection<?>) object).size());
			int sizePosition = bb.position();
			bb.putInt(0);

			int totalBytesOfElements = 0;
			Iterator it = ((Collection<?>) object).iterator();
			while(it.hasNext()) {
				Object element = it.next();
				ASerializer elementSerializer = getNewCustomSerializer();
				ByteBuffer elementBb = elementSerializer.outputBufferFromObject(element);

				totalBytesOfElements += elementBb.limit();

				bb.flip();
				bb = ByteBuffer.allocate(bb.limit() + elementBb.limit()).put(bb).put(elementBb);
			}
			bb.putInt(sizePosition, totalBytesOfElements);
		} else

		if (object instanceof Map<?, ?>) {
			System.out.println("is a map");
			bb = ByteBuffer.allocate(1*(Integer.SIZE / Byte.SIZE) + Integer.SIZE / Byte.SIZE + objectClassStringSize + Integer.SIZE / Byte.SIZE);
			bb.putInt(System.identityHashCode(object));
			bb.putInt(objectClassStringSize);
			bb.put(objectClassString.getBytes());
			int sizePosition = bb.position();
			bb.putInt(0);

			ArrayList keysAsArrayList = new ArrayList();
			ArrayList itemAsArrayList = new ArrayList();

			Set keys = ((Map<?, ?>) object).keySet();
			for (Object k : keys) {
				keysAsArrayList.add(k);
				System.out.print("-1");
				itemAsArrayList.add(((Map) object).get(k));
				System.out.print("-2");
			}


			int totalBytesOfElements = 0;

			ASerializer elementSerializer = getNewCustomSerializer();
			ByteBuffer elementBb = elementSerializer.outputBufferFromObject(keysAsArrayList);
			totalBytesOfElements += elementBb.limit();
			bb.flip();
			bb = ByteBuffer.allocate(bb.limit() + elementBb.limit()).put(bb).put(elementBb);

			elementSerializer = getNewCustomSerializer();
			elementBb = elementSerializer.outputBufferFromObject(itemAsArrayList);
			bb.flip();
			bb = ByteBuffer.allocate(bb.limit() + elementBb.limit()).put(bb).put(elementBb);

			bb.putInt(sizePosition, totalBytesOfElements);
		}

		else {
			throw new NotSerializableException();
		}
		bb.flip();
		return bb;
	}
}
