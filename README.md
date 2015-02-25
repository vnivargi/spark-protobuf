This standalone respository contains a simple Spark job that uses Protocol Buffer classes and encounters ClassNotFoundExceptions when doing so.

You can compile and run the driver using the simple script, `run.sh`.

By default, the driver, MessageProcessor, will simply deserialize the protobuf-encoded messages and print them to screen.

However, there are two commented out sections. The first attemps to create an RDD from the messages, and this fails with ClassNotFoundExceptions.

The second section creates a trivial RDD of ints, and tries to print each one. This fails when setting `spark.files.userClassPathFirst`.

The driver can be found in: `src/main/scala/com/defend7/spark-protobuf/MessageProcessor.scala`.

