package io.cucumber.scala

import java.lang.reflect.{ParameterizedType, Type}

import io.cucumber.scala.Aliases.HookBody

/**
 * Base trait for a scala step definition implementation.
 */
trait ScalaDsl {
  self =>

  val EMPTY_TAG_EXPRESSION = ""
  val DEFAULT_BEFORE_ORDER = 1000
  val DEFAULT_AFTER_ORDER = 1000

  import scala.language.implicitConversions

  val registry = new ScalaDslRegistry()

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
    registry.beforeHooks += new ScalaHookDefinition(tagExpression, order, body)
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
    registry.beforeStepHooks += new ScalaHookDefinition(tagExpression, order, body)
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
    registry.afterHooks += new ScalaHookDefinition(tagExpression, order, body)
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
    registry.afterStepHooks += new ScalaHookDefinition(tagExpression, order, body)
  }


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
    def apply[T1](f: (T1) => Any)(implicit m1: Manifest[T1]) = {
      register(m1) {
        case List(a1: AnyRef) =>
          f(a1.asInstanceOf[T1])
      }
    }

    def apply[T1, T2](f: (T1, T2) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2]) = {
      register(m1, m2) {
        case List(a1: AnyRef, a2: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2])
      }
    }

    def apply[T1, T2, T3](f: (T1, T2, T3) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3]) = {
      register(m1, m2, m3) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3])
      }
    }

    def apply[T1, T2, T3, T4](f: (T1, T2, T3, T4) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4]) = {
      register(m1, m2, m3, m4) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4])
      }
    }

    def apply[T1, T2, T3, T4, T5](f: (T1, T2, T3, T4, T5) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5]) = {
      register(m1, m2, m3, m4, m5) {
        case List(a1: AnyRef, a2: AnyRef, a3: AnyRef, a4: AnyRef, a5: AnyRef) =>
          f(a1.asInstanceOf[T1],
            a2.asInstanceOf[T2],
            a3.asInstanceOf[T3],
            a4.asInstanceOf[T4],
            a5.asInstanceOf[T5])
      }
    }

    def apply[T1, T2, T3, T4, T5, T6](f: (T1, T2, T3, T4, T5, T6) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7](f: (T1, T2, T3, T4, T5, T6, T7) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8](f: (T1, T2, T3, T4, T5, T6, T7, T8) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18], m19: Manifest[T19]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18], m19: Manifest[T19], m20: Manifest[T20]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18], m19: Manifest[T19], m20: Manifest[T20], m21: Manifest[T21]) = {
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

    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22](f: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) => Any)(implicit m1: Manifest[T1], m2: Manifest[T2], m3: Manifest[T3], m4: Manifest[T4], m5: Manifest[T5], m6: Manifest[T6], m7: Manifest[T7], m8: Manifest[T8], m9: Manifest[T9], m10: Manifest[T10], m11: Manifest[T11], m12: Manifest[T12], m13: Manifest[T13], m14: Manifest[T14], m15: Manifest[T15], m16: Manifest[T16], m17: Manifest[T17], m18: Manifest[T18], m19: Manifest[T19], m20: Manifest[T20], m21: Manifest[T21], m22: Manifest[T22]) = {
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
      registry.stepDefinitions += new ScalaStepDefinition(Utils.frame(self), name, regex, types, pf)
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
