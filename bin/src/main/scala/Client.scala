import akka.actor._

object Local extends App {

  implicit val system = ActorSystem("LocalSystem")
  val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")  // the local actor
  localActor ! "START"                                                     // start the action

}

class LocalActor extends Actor {

  // create the remote actor (Akka 2.1 syntax)
  //have a remote contxt to refer ti srver
  val remote = context.actorSelection("akka.tcp://HelloRemoteSystem@127.0.0.1:5150/user/RemoteActor")
  var counter = 0 

  def receive = {
    case "START" =>
        remote ! "Hello from the LocalActor"
    case msg: String =>
        println(s"LocalActor received message: '$msg'")
        if (counter < 5) {//keep on sending message to each other
            sender ! "Hello back to you"
            counter += 1
        }
  }
}
