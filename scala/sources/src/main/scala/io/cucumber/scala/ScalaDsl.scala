package io.cucumber.scala

import java.lang.reflect.{ParameterizedType, Type}

import io.cucumber.scala.Aliases.{DefaultDataTableCellTransformerBody, DefaultDataTableEntryTransformerBody, DefaultParameterTransformerBody, DocStringDefinitionBody, HookBody}

import scala.reflect.ClassTag

private[scala] trait BaseScalaDsl {

  val NO_REPLACEMENT = Seq[String]()
  val EMPTY_TAG_EXPRESSION = ""
  val DEFAULT_BEFORE_ORDER = 1000
  val DEFAULT_AFTER_ORDER = 1000

  import scala.language.implicitConversions

  private[scala] val registry: ScalaDslRegistry = new ScalaDslRegistry()

}

/**
 * Base trait for a scala step definition implementation.
 */
trait ScalaDsl extends BaseScalaDsl with StepDsl with HookDsl with DataTableTypeDsl with DocStringTypeDsl with ParameterTypeDsl with DefaultTransformerDsl {

}

private[scala] trait HookDsl extends BaseScalaDsl {

  // TODO support Before/After with no parameter

  def Before(body: HookBody): Unit = {
    Before(EMPTY_TAG_EXPRESSION, DEFAULT_BEFORE_ORDER)(body)
  }

  def Before(tagExpression: String)(body: HookBody): Unit = {
    Before(tagExpression, DEFAULT_BEFORE_ORDER)(body)
  }

  def Before(order: Int)(body: HookBody): Unit = {
    Before(EMPTY_TAG_EXPRESSION, order)(body)
  }

  def Before(tagExpression: String, order: Int)(body: HookBody): Unit = {
    registry.beforeHooks += ScalaHookDetails(tagExpression, order, body)
  }

  def BeforeStep(body: HookBody): Unit = {
    BeforeStep(EMPTY_TAG_EXPRESSION, DEFAULT_BEFORE_ORDER)(body)
  }

  def BeforeStep(tagExpression: String)(body: HookBody): Unit = {
    BeforeStep(tagExpression, DEFAULT_BEFORE_ORDER)(body)
  }

  def BeforeStep(order: Int)(body: HookBody): Unit = {
    BeforeStep(EMPTY_TAG_EXPRESSION, order)(body)
  }

  def BeforeStep(tagExpression: String, order: Int)(body: HookBody): Unit = {
    registry.beforeStepHooks += ScalaHookDetails(tagExpression, order, body)
  }

  def After(body: HookBody): Unit = {
    After(EMPTY_TAG_EXPRESSION, DEFAULT_AFTER_ORDER)(body)
  }

  def After(tagExpression: String)(body: HookBody): Unit = {
    After(tagExpression, DEFAULT_AFTER_ORDER)(body)
  }

  def After(order: Int)(body: HookBody): Unit = {
    After(EMPTY_TAG_EXPRESSION, order)(body)
  }

  def After(tagExpression: String, order: Int)(body: HookBody): Unit = {
    registry.afterHooks += ScalaHookDetails(tagExpression, order, body)
  }

  def AfterStep(body: HookBody): Unit = {
    AfterStep(EMPTY_TAG_EXPRESSION, DEFAULT_AFTER_ORDER)(body)
  }

  def AfterStep(tagExpression: String)(body: HookBody): Unit = {
    AfterStep(tagExpression, DEFAULT_AFTER_ORDER)(body)
  }

  def AfterStep(order: Int)(body: HookBody): Unit = {
    AfterStep(EMPTY_TAG_EXPRESSION, order)(body)
  }

  def AfterStep(tagExpression: String, order: Int)(body: HookBody): Unit = {
    registry.afterStepHooks += ScalaHookDetails(tagExpression, order, body)
  }

}

private[scala] trait DocStringTypeDsl extends BaseScalaDsl {

  def DocStringType[T](contentType: String)(body: DocStringDefinitionBody[T])(implicit ev: ClassTag[T]): Unit = {
    registry.docStringTypes += ScalaDocStringTypeDetails[T](contentType, body, ev)
  }

}

private[scala] trait DataTableTypeDsl extends BaseScalaDsl {

  def DataTableType: DataTableTypeBody = DataTableType(NO_REPLACEMENT)

  def DataTableType(replaceWithEmptyString: String): DataTableTypeBody = DataTableType(Seq(replaceWithEmptyString))

  private def DataTableType(replaceWithEmptyString: Seq[String]) = new DataTableTypeBody(replaceWithEmptyString)

  final class DataTableTypeBody(replaceWithEmptyString: Seq[String]) {

    def apply[T](body: DataTableEntryDefinitionBody[T])(implicit ev: ClassTag[T]): Unit = {
      registry.dataTableTypes += ScalaDataTableEntryTypeDetails[T](replaceWithEmptyString, body, ev)
    }

    def apply[T](body: DataTableRowDefinitionBody[T])(implicit ev: ClassTag[T]): Unit = {
      registry.dataTableTypes += ScalaDataTableRowTypeDetails[T](replaceWithEmptyString, body, ev)
    }

    def apply[T](body: DataTableCellDefinitionBody[T])(implicit ev: ClassTag[T]): Unit = {
      registry.dataTableTypes += ScalaDataTableCellTypeDetails[T](replaceWithEmptyString, body, ev)
    }

    def apply[T](body: DataTableDefinitionBody[T])(implicit ev: ClassTag[T]): Unit = {
      registry.dataTableTypes += ScalaDataTableTableTypeDetails[T](replaceWithEmptyString, body, ev)
    }

  }

}

private[scala] trait ParameterTypeDsl extends BaseScalaDsl {

  def ParameterType(name: String, regex: String) = new ParameterTypeBody(name, regex)

  final class ParameterTypeBody(name: String, regex: String) {

    // Important: use the piece of code in the file gen.scala to generate these methods easily

    def apply[R](f: (String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1) =>
          f(p1)
      }
    }

    def apply[R](f: (String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2) =>
          f(p1, p2)
      }
    }

    def apply[R](f: (String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3) =>
          f(p1, p2, p3)
      }
    }

    def apply[R](f: (String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4) =>
          f(p1, p2, p3, p4)
      }
    }

    def apply[R](f: (String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5) =>
          f(p1, p2, p3, p4, p5)
      }
    }

    def apply[R](f: (String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6) =>
          f(p1, p2, p3, p4, p5, p6)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7) =>
          f(p1, p2, p3, p4, p5, p6, p7)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21)
      }
    }

    def apply[R](f: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String) => R)(implicit tag: ClassTag[R]): Unit = {
      register {
        case List(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22) =>
          f(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22)
      }
    }

    private def register[R](pf: PartialFunction[List[String], R])(implicit tag: ClassTag[R]): Unit = {
      registry.parameterTypes += ScalaParameterTypeDetails[R](name, regex, pf, tag)
    }

  }

}

private[scala] trait DefaultTransformerDsl extends BaseScalaDsl {

  def DefaultParameterTransformer(body: DefaultParameterTransformerBody): Unit = {
    registry.defaultParameterTransformers += ScalaDefaultParameterTransformerDetails(body)
  }

  def DefaultDataTableCellTransformer(body: DefaultDataTableCellTransformerBody): Unit = {
    DefaultDataTableCellTransformer(NO_REPLACEMENT)(body)
  }

  def DefaultDataTableCellTransformer(replaceWithEmptyString: String)(body: DefaultDataTableCellTransformerBody): Unit = {
    DefaultDataTableCellTransformer(Seq(replaceWithEmptyString))(body)
  }

  private def DefaultDataTableCellTransformer(replaceWithEmptyString: Seq[String])(body: DefaultDataTableCellTransformerBody): Unit = {
    registry.defaultDataTableCellTransformers += ScalaDefaultDataTableCellTransformerDetails(replaceWithEmptyString, body)
  }

  def DefaultDataTableEntryTransformer(body: DefaultDataTableEntryTransformerBody): Unit = {
    DefaultDataTableEntryTransformer(NO_REPLACEMENT)(body)
  }

  def DefaultDataTableEntryTransformer(replaceWithEmptyString: String)(body: DefaultDataTableEntryTransformerBody): Unit = {
    DefaultDataTableEntryTransformer(Seq(replaceWithEmptyString))(body)
  }

  private def DefaultDataTableEntryTransformer(replaceWithEmptyString: Seq[String])(body: DefaultDataTableEntryTransformerBody): Unit = {
    registry.defaultDataTableEntryTransformers += ScalaDefaultDataTableEntryTransformerDetails(replaceWithEmptyString, body)
  }

}

private[scala] trait StepDsl extends BaseScalaDsl {
  self =>

  final class Step(name: String) {
    def apply(regex: String): StepBody = new StepBody(name, regex)
  }

  final class Fun0(val f: Function0[Any])

  object Fun0 {
    implicit def function0AsFun0(f: Function0[Any]): Fun0 = new Fun0(f)
  }

  final class StepBody(name: String, regex: String) {

    /*
      * apply0 needs to be able to handle both calls by value and reference.
      *
      * Call by value:
      *
      *    And("^multipy the things$") {
      *      z = x * y;
      *    }
      *
      * Call by reference:
      *
      *    And("^multipy the things$") { ()=>
      *      z = x * y;
      *    }
      *
      * Call by value has the signature => Unit while call by reference has the signature () => Any
      *
      * Due to type erasure both would end up with the signature () => Unit.
      *
      * Fun0 and the implicit conversion lets us work around this.
      *
      **/
    def apply(f: => Unit): Unit = {
      apply(f _)
    }

    def apply(fun: Fun0): Unit = {
      register() {
        case Nil => fun.f()
      }
    }

    /*
     * Generated apply1 to apply22 below
     */
    def apply[T1](f: T1 => Any)(implicit m1: Manifest[T1]): Unit = {
      register(m1) {
        case List(a1: AnyRef) =>
          f(a1.asInstanceOf[T1])
      }
    }

    def apply[T1, T2](f: (T1, T2) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2]): Unit = {
      register(m1, m2) {
        case List(a1: AnyRef, a2: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2])
      }
    }

    def apply[T1, T2, T3](f: (T1, T2, T3) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3]): Unit = {
      register(m1, m2, m3) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3])
      }
    }

    def apply[T1, T2, T3, T4](f: (T1, T2, T3, T4) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4]): Unit = {
      register(m1, m2, m3, m4) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4])
      }
    }

    def apply[T1, T2, T3, T4, T5](f: (T1, T2, T3, T4, T5) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5]): Unit = {
      register(m1, m2, m3, m4, m5) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6](f: (T1, T2, T3, T4, T5, T6) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6]): Unit = {
      register(m1, m2, m3, m4, m5, m6) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7](f: (T1, T2, T3, T4, T5, T6, T7) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8](f: (T1, T2, T3, T4, T5, T6, T7, T8) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef, a14: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13],
            a14.asInstanceOf[T14])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef, a14: AnyRef, a15: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13],
            a14.asInstanceOf[T14],
            a15.asInstanceOf[T15])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef, a14: AnyRef, a15: AnyRef, a16: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13],
            a14.asInstanceOf[T14],
            a15.asInstanceOf[T15],
            a16.asInstanceOf[T16])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef, a14: AnyRef, a15: AnyRef, a16: AnyRef, a17: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13],
            a14.asInstanceOf[T14],
            a15.asInstanceOf[T15],
            a16.asInstanceOf[T16],
            a17.asInstanceOf[T17])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef, a14: AnyRef, a15: AnyRef, a16: AnyRef, a17: AnyRef, a18: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13],
            a14.asInstanceOf[T14],
            a15.asInstanceOf[T15],
            a16.asInstanceOf[T16],
            a17.asInstanceOf[T17],
            a18.asInstanceOf[T18])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18], m19: Manifest[T19]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef, a14: AnyRef, a15: AnyRef, a16: AnyRef, a17: AnyRef, a18: AnyRef, a19: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13],
            a14.asInstanceOf[T14],
            a15.asInstanceOf[T15],
            a16.asInstanceOf[T16],
            a17.asInstanceOf[T17],
            a18.asInstanceOf[T18],
            a19.asInstanceOf[T19])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18], m19: Manifest[T19], m20: Manifest[T20]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef, a14: AnyRef, a15: AnyRef, a16: AnyRef, a17: AnyRef, a18: AnyRef, a19: AnyRef, a20: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13],
            a14.asInstanceOf[T14],
            a15.asInstanceOf[T15],
            a16.asInstanceOf[T16],
            a17.asInstanceOf[T17],
            a18.asInstanceOf[T18],
            a19.asInstanceOf[T19],
            a20.asInstanceOf[T20])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18], m19: Manifest[T19], m20: Manifest[T20], m21: Manifest[T21]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m21) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef, a14: AnyRef, a15: AnyRef, a16: AnyRef, a17: AnyRef, a18: AnyRef, a19: AnyRef, a20: AnyRef, a21: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13],
            a14.asInstanceOf[T14],
            a15.asInstanceOf[T15],
            a16.asInstanceOf[T16],
            a17.asInstanceOf[T17],
            a18.asInstanceOf[T18],
            a19.asInstanceOf[T19],
            a20.asInstanceOf[T20],
            a21.asInstanceOf[T21])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18], m19: Manifest[T19], m20: Manifest[T20], m21: Manifest[T21], m22: Manifest[T22]): Unit = {
      register(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m21, m22) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef, a6: AnyRef, a7: AnyRef, a8: AnyRef, a9: AnyRef, a10: AnyRef, a11: AnyRef, a12: AnyRef, a13: AnyRef, a14: AnyRef, a15: AnyRef, a16: AnyRef, a17: AnyRef, a18: AnyRef, a19: AnyRef, a20: AnyRef, a21: AnyRef, a22: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5],
            a6.asInstanceOf[T6],
            a7.asInstanceOf[T7],
            a8.asInstanceOf[T8],
            a9.asInstanceOf[T9],
            a10.asInstanceOf[T10],
            a11.asInstanceOf[T11],
            a12.asInstanceOf[T12],
            a13.asInstanceOf[T13],
            a14.asInstanceOf[T14],
            a15.asInstanceOf[T15],
            a16.asInstanceOf[T16],
            a17.asInstanceOf[T17],
            a18.asInstanceOf[T18],
            a19.asInstanceOf[T19],
            a20.asInstanceOf[T20],
            a21.asInstanceOf[T21],
            a22.asInstanceOf[T22])
      }
    }

    private def register(ms: Manifest[_ <: Any]*)(pf: PartialFunction[List[Any], Any]): Unit = {
      val types = ms.map(m => toJavaType(m)).toArray
      registry.stepDefinitions += ScalaStepDetails(Utils.frame(self), name, regex, types, pf)
    }

    private def toJavaType(m: Manifest[_]): Type = {
      val typeArgs = m.typeArguments
      if (typeArgs.isEmpty) {
        m.runtimeClass
      } else {
        new ParameterizedType {
          override def getActualTypeArguments: Array[Type] = typeArgs.map(toJavaType).toArray

          override def getRawType: Type = m.runtimeClass

          override def getOwnerType: Type = ???
        }
      }
    }
  }

}
