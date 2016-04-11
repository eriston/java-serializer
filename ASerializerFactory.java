package serialization2;

import serialization.Serializer;

public class ASerializerFactory implements serialization.SerializerFactory {

	public ASerializerFactory() {
		
	}
	
	@Override
	public serialization.Serializer createSerializer() {
		serialization.Serializer s = (Serializer) new ACustomSerializer();
		return s;
	}
}
