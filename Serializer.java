package serialization2;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;

public interface Serializer {
	public ByteBuffer outputBufferFromObject(Object object) throws NotSerializableException;
	public Object objectFromByteBuffer(ByteBuffer inputBuffer) throws StreamCorruptedException;
}
