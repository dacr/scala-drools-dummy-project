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
import java.io.FileInputStream
import java.io.InputStreamReader
import org.drools.core.audit.WorkingMemoryConsoleLogger
import org.drools.io.Resource
import org.drools.io.ResourceFactory
import org.drools.builder.KnowledgeBuilderFactory
import org.drools.builder.ResourceType
import java.io.File
import collection.JavaConversions._
import org.slf4j.LoggerFactory

object Dummy {
  val logger = LoggerFactory.getLogger(Dummy.getClass())

  def main(args: Array[String]) {
    logger.warn("# test me through test cases...")
    logger.warn("run 'sbt test'")
  }

  def using[R, T <% { def dispose() }](getres: => T)(doit: T => R): R = {
    val res = getres
    try doit(res) finally res.dispose
  }

  def analyze(model: List[Any], kb: String) = {
    //System.setProperty("drools.dialect.java.compiler", "JANINO")

    val config = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration()
    config.setProperty("drools.dialect.mvel.strict", "false")
    val kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(config)

    val res = ResourceFactory.newClassPathResource(kb)
    kbuilder.add(res, ResourceType.DRL)

    val errors = kbuilder.getErrors();
    if (errors.size() > 0) {
      for (error <- errors) logger.error(error.getMessage())
      throw new IllegalArgumentException("Problem with the Knowledge base");
    }


    val kbase = kbuilder.newKnowledgeBase()
    
    val results = using(kbase.newStatefulKnowledgeSession()) { session =>
      session.setGlobal("logger", LoggerFactory.getLogger(kb))
      model.foreach(session.insert(_))
      session.fireAllRules()
      session.getObjects()
    }

    results
  }

}
