//> using dep "com.github.fd4s::fs2-kafka:3.6.0"

import cats.effect.unsafe.implicits.global
import cats.effect.*

object Main extends IOApp.Simple:

  override def run: IO[Unit] = IO.println("Hello World")

