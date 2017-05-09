package scala.service

import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import services.store.EventStore
import uk.gov.ons.bi.writers.BiConfigManager

class EventStoreTest extends FlatSpec with Matchers with EventStore with BeforeAndAfterAll {

  private[this] val utility = HBaseTesting.hBaseServer

  "It" should "store events properly" in {
    val before = utility.countRows(table)
    val instructions = "{command: DELETE, id: 1}"
    (0 to 10).foreach { x =>
      storeEvent(instructions)
    }
    utility.countRows(table) shouldBe before + 11
    getAll.length shouldBe before + 11
    val firstTime = getAll.head._1.toLong
    getAll.tail.foreach { rec =>
      rec._1.toLong shouldBe >(firstTime)
    }
    cleanAll()
    storeEvent(instructions)
    getAll.length shouldBe before + 1
  }

  override def config: Config = BiConfigManager.envConf(ConfigFactory.load())

  override protected def tableName: String = config.getString("hbase.events.table.name")
}
