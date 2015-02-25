Spark-Protocol Buffers 
======================

This standalone respository contains a very simple Spark job that uses Protocol Buffer classes and encounters ClassNotFoundExceptions when doing so. You can compile and run the driver using the provided script, `run.sh`.

By default, the driver, `MessageProcessor`, will simply deserialize the protobuf-encoded messages and print them to screen. However, there are two commented out sections in the driver. The first attemps to create an RDD from the messages, This fails with ClassNotFoundExceptions:

```
Caused by: java.lang.RuntimeException: Unable to find proto buffer class
	at com.google.protobuf.GeneratedMessageLite$SerializedForm.readResolve(GeneratedMessageLite.java:775)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:483)
	...
Caused by: java.lang.ClassNotFoundException: com.defend7.messages.Messages$TestMessage
	at java.net.URLClassLoader$1.run(URLClassLoader.java:372)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:361)
	...
```

The second section creates a trivial RDD of ints, and tries to print each one. This fails when setting `spark.files.userClassPathFirst`:
```
java.lang.ClassCastException: cannot assign instance of com.defend7.processors.MessageProcessor$$anonfun$main$1 to field org.apache.spark.rdd.RDD$$anonfun$foreach$1.cleanF$2 of type scala.Function1 in instance of org.apache.spark.rdd.RDD$$anonfun$foreach$1
	at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2089)
	at java.io.ObjectStreamClass.setObjFieldValues(ObjectStreamClass.java:1261)
	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:1999)
	...
```

The driver can be found in: `src/main/scala/com/defend7/spark-protobuf/MessageProcessor.scala`.

