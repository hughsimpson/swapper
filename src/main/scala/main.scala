import org.apache.commons.io.EndianUtils
import java.util.UUID
import java.nio.ByteBuffer

object Main extends App {
   def swapEndian(uuid: UUID) = new UUID(
      EndianUtils.readSwappedLong(ByteBuffer.allocate(8).putLong(uuid.getMostSignificantBits).array, 0),
      EndianUtils.readSwappedLong(ByteBuffer.allocate(8).putLong(uuid.getLeastSignificantBits).array, 0)
   )
   def swap(s:String) = swapEndian(UUID.fromString(s)).toString.filterNot(_=='-')
  def commandLoop(): Unit = {
      try{
         val swapped = swap(Console.readLine())
         println(swapped)
      } catch {
         case _ => println("not a valid uuid")
      }

      commandLoop()
   }

   commandLoop()
}
