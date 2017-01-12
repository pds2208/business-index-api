package scala.db

import com.outworkers.phantom.builder.serializers.KeySpaceSerializer
import com.outworkers.phantom.connectors.ContactPoint
import com.outworkers.phantom.dsl._

class AppDatabase(override val connector: KeySpaceDef) extends Database[AppDatabase](connector) {
  object feedbackEntries extends FeedbackEntries with connector.Connector
}

object DbConnector {

  val keyspace = "uk-gov-ons"
  val initQuery = KeySpaceSerializer(keyspace).ifNotExists()
    .`with`(replication eqs SimpleStrategy.replication_factor(2))
    .and(durable_writes eqs true)
    .qb.queryString

  val connector = ContactPoint.local.keySpace(keyspace, (session, keyspace) => initQuery)
}

object AppDatabase extends AppDatabase(DbConnector.connector)

trait BusinessDbProvider extends DatabaseProvider[AppDatabase] {
  override def database: AppDatabase = AppDatabase
}