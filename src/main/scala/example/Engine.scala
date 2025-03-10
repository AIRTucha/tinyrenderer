package tinyrenderer
import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.ImageData
import Commone.{ Vec3, Color, Row }
import scala.collection.mutable.ListBuffer

class Engine(val canvas: Canvas) {
  if(dom.window.location.href.contains("localhost")) {
    //dev mode
    canvas.width = 1000;
    canvas.height = 1000;
  } else {
    //demo mode
    canvas.width = 750;
    canvas.height = 750;
  }
  private val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
  def render(scene: Scene) = ctx.putImageData(scene.getImg, 0, 0)
  def Scene(low: Vec3, high: Vec3) = new Scene(
    canvas.width,
    canvas.height,
    low,
    high,
    ctx.getImageData(0, 0, canvas.width, canvas.height)
  )
}
class Scene(
  val width: Int,
  val height: Int,
  val low: Vec3,
  val high: Vec3,
  private val img: ImageData
) {
  val zBuffer: Array[Array[Double]] = Array.fill(height, width)(-2)
  val lights: ListBuffer[Vec3] = ListBuffer()
  val dataAmount = width * height * 4
  def clear = {
    var i = 0
    while (i < dataAmount) {
      img.data(i) = 0
      img.data(i + 1) = 0
      img.data(i + 2) = 0
      img.data(i + 3) = 255
      i += 4
    }
  }
  def dot(
    x: Int,
    y: Int,
    z: Double,
    r: Double,
    g: Double,
    b: Double,
    a: Double
  ) = if (zBuffer(x)(y) < z) {
      val redIndex: Int = (width * y + x) * 4
      img.data(redIndex) = r.asInstanceOf[Short]
      img.data(redIndex + 1) = g.asInstanceOf[Short]
      img.data(redIndex + 2) = b.asInstanceOf[Short]
      img.data(redIndex + 3) = 255 //a.asInstanceOf[Short]
      zBuffer(x)(y) = z
    }
  def getImg = img
  def scale( vec: Vec3 ) = 
    Matrix4x4(
      ( width/2,        0,   0, width/2),
      ( 0      , -height/2, 0, height/2),
      ( 0      ,      0,   1, 1),
      ( 0      ,      0, 0, 0)
    ) * vec
}
