package ir.phsys.game.resources

import scala.collection.mutable
import ir.phsys.game.exception.ResourceNotEnoughException
import grizzled.slf4j.Logger
import ir.phsys.game.utils.ImplicitClasses._

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/11/13
 *         Time: 4:41 PM
 */

class ResourceMap extends mutable.HashMap[String, Int] {
//  import ResourceMap._

  type ResourceHashMap = mutable.HashMap[String, Int]
  type ResourceMapType = scala.collection.Map[String, Int]

  def - /*[A >: ResourceMap <: Null]*/ (o: ResourceMapType): ResourceMap = {

    if (meets(o)) {
      new ResourceMap() ++ map {
        case (k, v) => k -> (v - o.getOrElse(k, 0))
      }
    } else {
      this.Empty
    }
  }

  def -=(o: ResourceMapType): Unit = this - o match {
    case x if x == Empty => throw new ResourceNotEnoughException
    case x => this ++= x
  }

  def ++(c: ResourceMapType): ResourceMap = {
    this ++= c
  }

  def apply(key: AbstractResource): Int = {
    ResourceMap.logger.info(s"Key is ${key.getClass.extractClassSimpleName()}")
    this(key.getClass.extractClassSimpleName())
  }

  def contains(key: AbstractResource): Boolean = super.contains(key.getClass.extractClassSimpleName())

  def put(key: AbstractResource, value: Int): Unit = {
    put(key.getClass.extractClassSimpleName(), value)
  }

  def put(key: AbstractResource): Unit = {
    put(key, key.getAmount)
  }

  def meets(that: ResourceMapType): Boolean = {
    that.forall {
      case (k, v) => this.contains(k) && this(k) >= v
    }
  }

  def +=(a: AbstractResource) = {
    this.synchronized {
      if (this.contains(a.getClass.extractClassSimpleName())) {
        val quantity = apply(a) + a.getAmount
        put(a, quantity)
      } else {
        put(a)
      }
    }
  }

  def add(a: ResourceMap): Unit = {
    this.synchronized {
      a.foreach {
        case (k, v) => {
          if (this.contains(k)) {
            val quantity = ResourceMap.this(k) + v
            ResourceMap.this.put(k, quantity)
          } else {
            ResourceMap.this.put(k, v)
          }
        }
      }
    }
  }

  def Empty: ResourceMap = new ResourceMap

  override def toString(): String = {
    val buff: StringBuffer = new StringBuffer()
    this.foreach {
      case (k, v) => {
        buff.append(s"$k->$v;")
      }
    }
    buff.toString
  }
}


object ResourceMap {
  val logger = Logger[this.type]

  def apply(elems: AbstractResource*): ResourceMap = {
    def generateRequirement(elems: List[AbstractResource]): ResourceMap#ResourceMapType =
      elems match {
        case Nil => new ResourceMap
        case x :: xs => {
          generateRequirement(xs) ++
            mutable.HashMap((x.getClass.extractClassSimpleName(), x.getAmount))
        }
      }
    new ResourceMap().++(generateRequirement(elems.toList))
  }

  implicit def toMap(str:String): ResourceMap = {
    val resMap = ResourceMap()
    val Pattern = """([a-zA-Z]*)\s*\*\s*(\d*)""".r
    str.split(',').foreach(_ match {
      case Pattern(x, y) => {
        val multiplier = y.toInt
        x.toLowerCase match {
          case "food" => resMap += Food() * multiplier
          case "wood" => resMap += Wood() * multiplier
          case "knowledge" => resMap += Knowledge() * multiplier
          case "metal" => resMap += Metal() * multiplier
          case "oil" => resMap += Oil() * multiplier
          case "coin" => resMap += Coin() * multiplier
        }
      }
    })
    resMap
  }
}