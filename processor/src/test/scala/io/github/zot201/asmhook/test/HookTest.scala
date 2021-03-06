/*
 * Copyright 2016 Zot201
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.zot201.asmhook.test

import com.google.testing.compile.JavaFileObjects
import io.github.zot201.asmhook.processing.AsmHookProcessor
import io.github.zot201.asmhook.test.example.{GenericHooks, HookExamples, MethodHookInstance}
import io.github.zot201.asmhook.test.util.Truths._
import org.junit.Test

import scala.reflect.ClassTag

class HookTest {
  /** Get JavaFileObject of the corresponding ClassTag from the 'example' project */
  def ex[T](implicit tag: ClassTag[T]) =
    JavaFileObjects.forResource(s"${tag.runtimeClass.getName.replace('.', '/')}.java")

  lazy val methodHookInstance = Seq(ex[MethodHookInstance], ex[HookExamples])
  val processor = new AsmHookProcessor

  @Test def methodHookInstance_(): Unit = {
    assertAboutJavaSources
      .that(methodHookInstance.asJava)
      .processedWith(processor)
      .compilesWithoutError()
  }

  @Test def genericHooks(): Unit = {
    assertAboutJavaSource
      .that(ex[GenericHooks[_]])
      .processedWith(processor)
      .compilesWithoutError()
  }
}
