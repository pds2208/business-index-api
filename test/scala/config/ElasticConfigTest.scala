package scala.config

import com.typesafe.config.{ Config, ConfigException, ConfigFactory }
import config.{ ElasticSearchConfig, ElasticSearchConfigLoader }
import org.scalatest.{ FreeSpec, Matchers }

import scala.sample.SampleConfig

class ElasticConfigTest extends FreeSpec with Matchers with SampleConfig {

  private trait ValidFixture {
    val SampleConfiguration: String =
      s"""|db {
        |  elasticsearch {
        |    username = $SampleUsername
        |    password = $SamplePassword
        |    index = $SampleIndex
        |    host = $SampleHost
        |    port = $SamplePort
        |    ssl = $SampleSsl
        |    loadTestData = $SampleLoad
        |    recreateIndex = $SampleRecreate
        |    csvFilePath = $SampleCsvFilePath
        |    connectionTimeout = $SampleConnectionTimeout
        |  }
        |}""".stripMargin
    val config: Config = ConfigFactory.parseString(SampleConfiguration)
  }

  private trait InvalidKeysFixture {
    val SampleConfiguration: String =
      s"""|db {
          |  elasticsearch {
          |    Username = $SampleUsername
          |    Password = $SamplePassword
          |    Index = $SampleIndex
          |    Host = $SampleHost
          |    Port = $SamplePort
          |    Ssl = $SampleSsl
          |    LoadTestData = $SampleLoad
          |    RecreateIndex = $SampleRecreate
          |    CsvFilePath = $SampleCsvFilePath
          |    ConnectionTimeout = $SampleConnectionTimeout
          |  }
          |}""".stripMargin
    val config: Config = ConfigFactory.parseString(SampleConfiguration)
  }

  private def parseConf(port: Any, ssl: Any, load: Any, recreate: Any, connectionTimeout: Any): Config = {
    val SampleConfiguration: String =
      s"""|db {
        |  elasticsearch {
        |    username = "esUser"
        |    password = "qwerty"
        |    index = "bi-dev"
        |    host = "localhost"
        |    port = $port
        |    ssl = $ssl
        |    loadTestData = $load
        |    recreateIndex = $recreate
        |    csvFilePath = "conf/test/sample.csv"
        |    connectionTimeout = $connectionTimeout
        |  }
        |}""".stripMargin
    ConfigFactory.parseString(SampleConfiguration)
  }

  "The config for the ElasticSearch repository" - {
    "can be successfully loaded when valid" in new ValidFixture {
      ElasticSearchConfigLoader.load(config) shouldBe ElasticSearchConfig(
        "esUser", "secret", "bi-dev", "localhost", 9000, ssl = false, loadTestData = false,
        recreateIndex = false, "conf/test/sample.csv", 10000
      )
    }

    "will fail fast when incorrect config is provided" - {
      "invalid config keys" in new InvalidKeysFixture {
        a[ConfigException] should be thrownBy {
          ElasticSearchConfigLoader.load(config)
        }
      }

      "invalid port type" in {
        val config: Config = parseConf(port = false, ssl = true, load = false, recreate = false, 10000)
        a[ConfigException] should be thrownBy {
          ElasticSearchConfigLoader.load(config)
        }
      }

      "invalid ssl type" in {
        val config: Config = parseConf(port = 9000, ssl = 100, load = false, recreate = false, 10000)
        a[ConfigException] should be thrownBy {
          ElasticSearchConfigLoader.load(config)
        }
      }

      "invalid loadTestData type" in {
        val config: Config = parseConf(port = 9000, ssl = 100, load = "100", recreate = false, 10000)
        a[ConfigException] should be thrownBy {
          ElasticSearchConfigLoader.load(config)
        }
      }

      "invalid recreateIndex type" in {
        val config: Config = parseConf(port = 9000, ssl = 100, load = "100", recreate = 200, 10000)
        a[ConfigException] should be thrownBy {
          ElasticSearchConfigLoader.load(config)
        }
      }

      "invalid connectionTimeout type" in {
        val config: Config = parseConf(port = 9000, ssl = 100, load = "100", recreate = 200, "abc")
        a[ConfigException] should be thrownBy {
          ElasticSearchConfigLoader.load(config)
        }
      }
    }
  }
}
