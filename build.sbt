name := "hbase-spark"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion  = "2.2.2"
val hadoopVersion = "2.7.0"
val hbaseVersion  = "2.2.2"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.2"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.2"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.9.2"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.0"
libraryDependencies += "org.apache.hbase" % "hbase-common" % "2.1.0"
libraryDependencies += "org.apache.hbase.connectors.spark" % "hbase-spark" % "1.0.0"
libraryDependencies += "org.apache.hbase.thirdparty" % "hbase-shaded-miscellaneous" % "2.1.0"

//libraryDependencies ++= Seq(
//  "org.apache.spark"  %% "spark-core"      % sparkVersion  % "provided",
//  "org.apache.spark"  %% "spark-streaming" % sparkVersion  % "provided",
//  "org.apache.hadoop" %  "hadoop-client"   % hadoopVersion % "provided",
//  "org.apache.hbase"  %  "hbase-common"    % hbaseVersion,
//  "org.apache.hbase"  %  "hbase-server"    % hbaseVersion,
//  "org.apache.hbase"  %  "hbase-protocol"  % hbaseVersion,
//  "org.apache.hbase"  %  "hbase-client"    % hbaseVersion
//)
