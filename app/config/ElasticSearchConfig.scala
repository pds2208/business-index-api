package config

case class ElasticSearchConfig(username: String, password: String, index: String, host: String, port: Int, ssl: Boolean)
