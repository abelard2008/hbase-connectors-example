package com.hubble.hbase.spark
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.client.{ConnectionFactory, Get, Put, Result}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.spark.{HBaseContext, HBaseRelation}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.spark.HBaseRDDFunctions._
import org.apache.hadoop.hbase.spark.datasources.HBaseTableCatalog
import org.apache.hadoop.hbase.spark.example.datasources.HBaseRecord
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}
import org.apache.spark.sql.DataFrame
import org.apache.yetus.audience.InterfaceAudience

case class Employee(key: String, fName: String, lName: String,
                    mName:String, addressLine:String, city:String,
                    state:String, zipCode:String)

object CatalogTableExample {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("HBaseBulkPutExample " +  "test" + " " + "cf")

    val conf = HBaseConfiguration.create()
    val spark = SparkSession.builder()
      .appName("HBaseBulkPutExample " + "test" + " " + "cf")
      .master("local")
      .getOrCreate()

    val sqlContext = spark.sqlContext
    val sc = spark.sparkContext
    val config = HBaseConfiguration.create()
    config.addResource(new Path("/your-hbase-conf-path/hbase-site.xml"))
    val connection = ConnectionFactory.createConnection(config);
    val hbaseContext = new HBaseContext(sc, config,null) // this statement must be required for hbase cluster!!!
    def catalog =
      s"""{
         |"table":{"namespace":"default", "name":"employee"},
         |"rowkey":"key",
         |"columns":{
         |"key":{"cf":"rowkey", "col":"key", "type":"string"},
         |"fName":{"cf":"person", "col":"firstName", "type":"string"},
         |"lName":{"cf":"person", "col":"lastName", "type":"string"},
         |"mName":{"cf":"person", "col":"middleName", "type":"string"},
         |"addressLine":{"cf":"address", "col":"addressLine", "type":"string"},
         |"city":{"cf":"address", "col":"city", "type":"string"},
         |"state":{"cf":"address", "col":"state", "type":"string"},
         |"zipCode":{"cf":"address", "col":"zipCode", "type":"string"}
         |}
         |}""".stripMargin

    val data = Seq(Employee("1", "Abby", "Smith", "K", "3456 main", "Orlando", "FL", "45235"),
      Employee("2", "Amaya", "Williams", "L", "123 Orange", "Newark", "NJ", "27656"),
      Employee("3", "Alchemy", "Davis", "P", "Warners", "Sanjose", "CA", "34789"))

    import spark.implicits._
     val df = spark.sparkContext.parallelize(data).toDF

     df.write.options(
             Map(HBaseTableCatalog.tableCatalog -> catalog, HBaseTableCatalog.newTable -> "4")).
             format("org.apache.hadoop.hbase.spark").
             save()

    // Reading from HBase to DataFrame
    val hbaseDF = spark.read
      .options(Map(HBaseTableCatalog.tableCatalog -> catalog))
      .format("org.apache.hadoop.hbase.spark")
      .load()

    //Display Schema from DataFrame
    hbaseDF.printSchema()

    //Collect and show Data from DataFrame
    hbaseDF.show(false)
    println("Dillon hello")
  }
}

