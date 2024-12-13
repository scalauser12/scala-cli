import cats.effect.*
import cats.syntax.all.*
import fs2.kafka.{AdminClientSettings, KafkaAdminClient}
import org.apache.kafka.clients.admin.NewTopic

import java.util.Optional
import java.lang.Integer as JavaInteger
import java.lang.Short as JavaShort


object CreateTopics extends IOApp.Simple:
  private val bootstrapServers = "localhost:9092"
  private val topics = List("cmdp.biomed.topic1", "cmdp.biomed.topic2", "cmdp.biomed.topic3")
  private val numberOfPartitions = 12

  override def run: IO[Unit] =
    KafkaAdminClient.resource[IO](
        AdminClientSettings(bootstrapServers = bootstrapServers)// .withCredentials( ??? )
      )
      .use(admin => topics.traverse { topicName =>
        val partitions = Optional.of[JavaInteger](numberOfPartitions)
        val replicas = Optional.empty[JavaShort]
        val newTopic = new NewTopic(topicName, partitions, replicas)
        admin.createTopic(newTopic)
      }.void)



