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


object Dummy {

   
  def main(args: Array[String]) {
    println("# test me through test cases...")
    println("sbt test")
  }
  
    
  def analyze(model: List[Any]) =  {
    System.setProperty("drools.dialect.mvel.strict", "false")
    
    val rulesfilename = "src/main/resources/KBExpertise.drl"
    val source = new InputStreamReader(new FileInputStream(rulesfilename))
    
    System.setProperty("drools.dialect.java.compiler", "JANINO")
    val config = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration()
    config.setProperty("drools.dialect.mvel.strict", "false")
    //config.setProperty("drools.dialect.java.compiler", "JANINO")
    val kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(config)
    
    
    val res = ResourceFactory.newClassPathResource("KBExpertise.drl")
    kbuilder.add(res, ResourceType.DRL)

    val kbase = kbuilder.newKnowledgeBase()
    val session = kbase.newStatefulKnowledgeSession()
    
    model.foreach(session.insert(_)) 

    session.fireAllRules()

    val results = session.getObjects()

    session.dispose()
    
    results
  }
  
    
}
