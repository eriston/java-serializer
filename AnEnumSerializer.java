package serialization2;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class AnEnumSerializer extends ASerializer implements Serializer {
	
	ASerializer s = new ASerializer();

	@Override
	public ByteBuffer outputBufferFromObject(Object object) {
		System.out.println("object:"+object);
		for(Object a:(Object[])object){
			System.out.println(a);
		}
		System.out.println("-- AnEnumSerializer - making bytebuffer");
		ByteBuffer bb;
		bb = ByteBuffer.allocate(0);
		ArrayList values = new ArrayList(Arrays.asList((Object[])object));
		
		System.out.println("values:"+values);
		for(Object a:values){
			System.out.println(a);
		}
		
		try {
			bb = s.outputBufferFromObject(values);
			
		} catch (NotSerializableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bb;
	}

	@Override
	public Object objectFromByteBuffer(ByteBuffer inputBuffer) {
		System.out.println("-- AnEnumSerializer - returning object");
		Object[] obj = (Object[]) Array.newInstance(Integer.class, 1);
		ArrayList values = null;
		try {
			values = (ArrayList) s.objectFromByteBuffer(inputBuffer);
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj = values.toArray(new Object[values.size()]);
		return obj;
	}
}
