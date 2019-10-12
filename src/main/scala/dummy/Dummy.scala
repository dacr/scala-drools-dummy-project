/*
 * Copyright 2012 David Crosson
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dummy

import dummy.model._

import org.kie.api.io._
import org.kie.internal.builder._
import org.kie.internal.io._

import collection.JavaConverters._
import org.slf4j.LoggerFactory

object Dummy {
  val logger = LoggerFactory.getLogger(Dummy.getClass())

  def main(args: Array[String]) {
    logger.warn("# test me through test cases...")
    logger.warn("run 'sbt test'")
    analyze(model1, "KB-People.drl")
  }


  def model1 = {
    val martine = Someone(name = "Martine", age = 30, nicknames = List("titine", "titi").asJava, attributes = Map("hairs" -> "brown").asJava)
    val martin = Someone(name = "Martin", age = 40, nicknames = List("tintin", "titi").asJava, attributes = Map("hairs" -> "black").asJava)
    val jack = Someone(name = "Jack", age = 12, nicknames = List("jacquouille").asJava, attributes = Map("eyes" -> "blue").asJava)
    val martineCar = Car(martine, "Ford", 2010, Color.blue)
    val martinCar = Car(martin, "GM", 2010, Color.black)
    val martinCar2 = Car(martin, "Ferrari", 2012, Color.red)
    val martinCar3 = Car(martin, "Porshe", 2011, Color.red)

    val martinHome = Home(martin, None)
    val jackHome = Home(jack, Some(Address("221B Baker Street", "London", "England")))

    List(
      martine,
      martin,
      jack,
      martineCar,
      martinCar,
      martinCar2,
      martinCar3,
      martinHome,
      jackHome
    )
  }


  def using[R, T <% {def dispose()}](getres: => T)(doit: T => R): R = {
    val res = getres
    try doit(res) finally res.dispose
  }

  def analyze(model: List[Any], kb: String) = {

    val config = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration()
    config.setProperty("drools.dialect.mvel.strict", "false")
    val kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(config)

    val res = ResourceFactory.newClassPathResource(kb)
    kbuilder.add(res, ResourceType.DRL)

    val errors = kbuilder.getErrors();
    if (errors.size() > 0) {
      for (error <- errors.asScala) logger.error(error.getMessage())
      throw new IllegalArgumentException("Problem with the Knowledge base");
    }

    val kbase = kbuilder.newKieBase()

    val results = using(kbase.newKieSession()) { session =>
      session.setGlobal("logger", LoggerFactory.getLogger(kb))
      model.foreach(session.insert(_))
      session.fireAllRules()
      session.getObjects()
    }

    results
  }

}
