package serialization2;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import util.misc.RemoteReflectionUtility;

public class ABeanSerializer extends ASerializer implements Serializer {

	ASerializer s = new ASerializer();
	ArrayList theBean = new ArrayList();

	@Override
	public ByteBuffer outputBufferFromObject(Object object) {
		System.out.println("object:" + object);


		System.out.println("-- ABeanSerializer - making bytebuffer");

		try {
			BeanInfo beaninfo = Introspector.getBeanInfo(object.getClass());
			System.out.println("\n---------------------");
			System.out.println("beaninfo: ");

			theBean.add(object.getClass().getName());
			ArrayList beanWritingMethodNames = new ArrayList();
			ArrayList<Object> beanWritingMethodTypes = new ArrayList<Object>();
			ArrayList beanWritingMethodValues = new ArrayList();
			int listPatternSize = 0;
			ArrayList listPatternObjects = new ArrayList();
			
			if(util.misc.RemoteReflectionUtility.isList(object.getClass())) {
				listPatternSize = util.misc.RemoteReflectionUtility.listSize(object);
				for(int i = 0; i < listPatternSize; i++) {
					Object bb = null;
					bb = util.misc.RemoteReflectionUtility.listGet(object, i);
					listPatternObjects.add(bb);
				}
				
			}

			MethodDescriptor[] methodDescriptors = beaninfo.getMethodDescriptors();
			for (int i = 0; i < methodDescriptors.length; i++) {
				MethodDescriptor md = methodDescriptors[i];
				if (md != null && "writeExternal".equals(md.getName())) {
					System.out.println("md: " + md.getDisplayName() + ":" + md.getName());
				}
			}

			PropertyDescriptor[] propertyDescriptors = beaninfo.getPropertyDescriptors();
			System.out.println("propertyDescriptors:");
			System.out.println(propertyDescriptors);
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
				try {

					if (propertyDescriptor != null
							&& (true || !"class".equals(propertyDescriptor.getName().toString()))) {
						System.out.print(propertyDescriptor.getName() + ":" );
						if(propertyDescriptor.getReadMethod() != null) {
							System.out.print(propertyDescriptor.getReadMethod().invoke(object));
						}
						System.out.print( ":" + propertyDescriptor.getPropertyType() + ":");
						if(propertyDescriptor.getReadMethod() != null) {
							System.out.print( propertyDescriptor.getReadMethod().getName() + ":");
						}
						System.out.flush();
						if (propertyDescriptor.getReadMethod() != null && propertyDescriptor.getWriteMethod() != null) {
							if (!RemoteReflectionUtility.isTransient(propertyDescriptor.getReadMethod())) {
								beanWritingMethodNames.add(propertyDescriptor.getWriteMethod().getName());
								beanWritingMethodTypes.add(propertyDescriptor.getPropertyType().getName());
								beanWritingMethodValues.add(propertyDescriptor.getReadMethod().invoke(object));

								System.out.println(propertyDescriptor.getWriteMethod().getName());
							} else {
								System.out.println("is Transient");
							}
						} else {
							System.out.println("--is-null--");
						}
					}

				} catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println();
			}

			theBean.add(beanWritingMethodNames);
			theBean.add(beanWritingMethodTypes);
			theBean.add(beanWritingMethodValues);
			theBean.add(listPatternSize);
			theBean.add(listPatternObjects);

			System.out.println("---------------------");
		} catch (IntrospectionException e1) {
			System.out.println("Not a bean");
		}

		ByteBuffer bb;
		bb = ByteBuffer.allocate(0);

		try {
			bb = s.outputBufferFromObject(theBean);
		} catch (NotSerializableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bb;
	}

	@Override
	public Object objectFromByteBuffer(ByteBuffer inputBuffer) {
		System.out.println("-- ABeanSerializer - returning object");
		Object object = null;
		String methodName = null;
		ArrayList beanWritingMethodNames = new ArrayList();
		ArrayList beanWritingMethodTypes = new ArrayList();
		ArrayList beanWritingMethodValues = new ArrayList();
		int beanListPatternSize = 0;
		ArrayList beanListPatternObjects = new ArrayList();

		ArrayList values = null;

		try {
			Object fromBuffer = s.objectFromByteBuffer(inputBuffer);
			System.out.println("fromBuffer: " + fromBuffer);
			values = (ArrayList) fromBuffer;
			methodName = (String) values.get(0);
			beanWritingMethodNames = (ArrayList) values.get(1);
			beanWritingMethodTypes = (ArrayList) values.get(2);
			beanWritingMethodValues = (ArrayList) values.get(3);
			beanListPatternSize = (int) values.get(4);
			beanListPatternObjects = (ArrayList) values.get(5);

		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("oops");
			e.printStackTrace();
		}


		
		try {
			Class myClass = Class.forName(methodName);
			System.out.println("->" + myClass.getName());
			try {
				Constructor constructor = myClass.getConstructor();

				try {
					object = constructor.newInstance();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (int i = 0; i < beanListPatternSize; i++) {
			util.misc.RemoteReflectionUtility.listAdd(object, beanListPatternObjects.get(i));
		}

		for (int i = 0; i < beanWritingMethodNames.size(); i++) {
			String name = (String) beanWritingMethodNames.get(i);
			Class type = null;
			try {
				String typeName = (String) beanWritingMethodTypes.get(i);
				if ("double".equals(typeName)) {
					type = double.class;
				} else if ("boolean".equals(typeName)) {
					type = boolean.class;
				} else if ("int".equals(typeName)) {
					type = int.class;
				} else {
					type = Class.forName(typeName);
				}
				System.out.println(type);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println(object);
			Method method = null;
			try {
				method = object.getClass().getMethod(name, type);
			} catch (NoSuchMethodException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				method.invoke(object, beanWritingMethodValues.get(i));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		RemoteReflectionUtility.invokeInitSerializedObject(object);
		System.out.println("-- ABeanSerializer - returning object -- DONE");
		return object;

	}

}
