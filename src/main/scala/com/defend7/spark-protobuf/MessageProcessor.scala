package com.defend7.processors

import com.defend7.messages.Messages.TestMessage
import java.io.File
import java.nio.file.{Path, Files, FileSystems}
import java.lang.System
import java.security.MessageDigest
import org.apache.spark.{SparkContext, SparkConf}
import scala.collection.JavaConversions._

object MessageProcessor {
  private val sparkConf = new SparkConf().setAppName("MessageProcessor")
    //.set("spark.files.userClassPathFirst", "false")
  private val sc = new SparkContext(sparkConf)

  def main(args: Array[String]) = {
    val protobufMessageBytes = getMessages()
    val protobufMessages = protobufMessageBytes.map(bytes => TestMessage.parseFrom(bytes))

    printClassPath("driver")

    // This works just fine.
    protobufMessages.foreach((message: TestMessage) => {
      println(message.toString)
    })

    // XXX: Printing from within the SparkContext throws class-not-found
    // exceptions, why?
     sc.makeRDD(protobufMessages).foreach((message: TestMessage) => {
       printClassPath("worker")
       println(message.toString)
     })

    // XXX: This trivial RDD foreach loop throws a java.lang.ClassCastException
    // when I set "spark.files.userClassPathFirst" to true, why?
     //val l = List(1, 2, 3)
     //sc.makeRDD(l).foreach((x: Int) => {
     //  printClassPath("worker")
     //  println(x.toString)
     //})
  }

  private def md5(s: String) = {
    MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02X".format(_)).mkString
  }

  private def printClassPath(header: String) {
    val classpath: String = System.getProperty("java.class.path")
    val classpathEntries = classpath.split(File.pathSeparator).toList
    println("%s:\n\tnum. entries = %d\n\tmd5 = %s\n\tclasspath = %s\n".format(header, classpathEntries.size, md5(classpath), classpath))
  }

  private def getMessages() = {
    val messagesPath = "src/test/protobuf"
    val messagesDir = new File(messagesPath)
    val messageFiles = messagesDir.listFiles.filter(_.getName.endsWith(".message"))
    messageFiles.map(file => {
      val path = FileSystems.getDefault().getPath(messagesPath, file.getName())
      Files.readAllBytes(path)
    })
  }
}
