import cats.effect.*
import cats.syntax.all.*
import fs2.kafka.{AdminClientSettings, KafkaAdminClient}

object DeleteTopics extends IOApp.Simple:
  private val bootstrapServers = "localhost:9092"
  private val topicPrefix = "cmdp.biomed."

  override def run: IO[Unit] =
    KafkaAdminClient.resource[IO](
        AdminClientSettings(bootstrapServers = bootstrapServers) // .withCredentials( ??? )
      )
      .use { admin =>
        for
          currentTopics <- admin.listTopics.names
          _ <- currentTopics.toList.traverse { topic =>
            if topic.startsWith(topicPrefix) then admin.deleteTopic(topic)
            else IO.unit
          }
        yield ()
      }



