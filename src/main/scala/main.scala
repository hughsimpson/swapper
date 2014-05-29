import org.apache.commons.io.EndianUtils
import java.util.UUID
import java.nio.ByteBuffer

object Main extends App {
  def swapEndian(uuid: UUID) = new UUID(
     EndianUtils.readSwappedLong(ByteBuffer.allocate(8).putLong(uuid.getMostSignificantBits).array, 0),
     EndianUtils.readSwappedLong(ByteBuffer.allocate(8).putLong(uuid.getLeastSignificantBits).array, 0)
  )
  def swapStrip(s:String) = swapEndian(UUID.fromString(s)).toString.filterNot(_=='-')
  def swapAdd(s:String) = {
    val uuid = UUID.fromString(s"${s.substring(0,8)}-${s.substring(8,12)}" +
      s"-${s.substring(12,16)}-${s.substring(16,20)}-${s.substring(20,32)}")
    swapEndian(uuid)
  }
  val fromMongo = """^([a-fA-F0-9]{32})$""".r
  val uuidRegex = """^(\p{XDigit}{8}(?:-\p{XDigit}{4}){3}-\p{XDigit}{12})$""".r

  def commandLoop(): Unit = {
    val out = scala.io.StdIn.readLine() match {
      case fromMongo(u) => swapAdd(u)
      case uuidRegex(u) => swapStrip(u)
      case _ => "not a valid uuid"
    }
    println(out)

    commandLoop()
  }

  commandLoop()
}
