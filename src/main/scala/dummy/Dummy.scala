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
import org.drools.RuleBaseFactory
import org.drools.audit.WorkingMemoryFileLogger
import org.drools.compiler.PackageBuilder
import org.drools.builder.KnowledgeBuilderFactory
import org.drools.io.ResourceFactory
import java.io.File
import org.drools.builder.ResourceType
import collection.JavaConversions._


object Dummy {

  def factsExample1:List[Any] = {
    val martine = Someone(name="Martine", age=30)
    val martin  = Someone(name="Martin", age=40)
    val jack    = Someone(name="Jack", age=12)
    val martineCar = Car(martine, "Ford", 2010, Color.blue)
    val martinCar  = Car(martin, "GM", 2010, Color.black)
    val martinCar2 = Car(martin, "Ferrari", 2012, Color.red)
    val martinCar3 = Car(martin, "Porshe", 2011, Color.red)
    
    val martinHome = Home(martin, None)
    val jackHome   = Home(jack, Some(Address("221B Baker Street", "London", "England")))
    
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
  
  
  
  
  def main(args: Array[String]) {
    usageCase2(factsExample1)
  }
  
  
  
  
  // ===> newStatefulSession
  
  def usageCase1(facts: List[Any]) {
    System.setProperty("drools.dialect.mvel.strict", "false")
    
    val rulesfilename = "src/main/resources/KBExpertise.drl"
    val source = new InputStreamReader(new FileInputStream(rulesfilename))
    
    val builder = new PackageBuilder()
    builder.addPackageFromDrl(source)
    if (builder.hasErrors()) {
      System.out.println(builder.getErrors().toString())
      throw new RuntimeException("Unable to compile " + rulesfilename + ".")
    }

    val pkg = builder.getPackage()
    val ruleBase = RuleBaseFactory.newRuleBase()
    ruleBase.addPackage(pkg)

    val session = ruleBase.newStatefulSession()
    //session.addEventListener(new org.drools.event.DebugAgendaEventListener())
    //session.addEventListener(new org.drools.event.DebugWorkingMemoryEventListener())
    
    // setup the audit logging
    val logger:WorkingMemoryFileLogger = new WorkingMemoryFileLogger(session)
    logger.setFileName("drools")
    logger.writeToDisk()

    facts.foreach(session.insert(_)) 

    session.fireAllRules()

    session.dispose()
  }
  
  
  // ===> newStatefulKnowledgeSession
  // how to extract facts, and process them from java/scala
  
  def usageCase2(facts: List[Any]):Iterable[Information] = {
    val rulesResource = "src/main/resources/KBExpertise.drl"

    val config = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration()
    config.setProperty("drools.dialect.mvel.strict", "false")
    val kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(config)
    
    kbuilder.add(ResourceFactory.newFileResource(new File(rulesResource)), ResourceType.DRL )

    val kbase = kbuilder.newKnowledgeBase()
    val session = kbase.newStatefulKnowledgeSession()
    //session.addEventListener(new org.drools.event.DebugAgendaEventListener())
    //session.addEventListener(new org.drools.event.DebugWorkingMemoryEventListener())
    
    // setup the audit logging
    val logger:WorkingMemoryFileLogger = new WorkingMemoryFileLogger(session)
    logger.setFileName("drools")
    logger.writeToDisk()

    facts.foreach(session.insert(_))
        
    session.fireAllRules()
    
    val results = session.getObjects() collect { case x:Information => x } 
    
    session.dispose()
    
    results

  }
  
}
