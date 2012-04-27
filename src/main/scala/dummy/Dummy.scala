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

// ----------------------------------------------------------------
// DOMAIN MODEL

case class Someone(name:String, age:Int)

case class Car(someone:Someone, model:String, year:Int, color:Color)

case class Color(name:String)

object Color {
  val red = Color("red")
  val blue = Color("blue")
  val green = Color("green")
  val black = Color("black")
}

case class Address(street:String, town:String, country:String)

case class Home(someone:Someone, address:Option[Address]) 

case class InformationRequest(someone:Someone, message:String)




// ----------------------------------------------------------------


import java.io.FileInputStream
import java.io.InputStreamReader
import org.drools.RuleBaseFactory
import org.drools.audit.WorkingMemoryFileLogger
import org.drools.compiler.PackageBuilder


object Dummy {

  def main(args: Array[String]) {
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

    val martine = Someone(name="Martine", age=30)
    val martin  = Someone(name="Martin", age=40)
    val jack    = Someone(name="Jack", age=12)
    val martineCar = Car(martine, "Ford", 2010, Color.blue)
    val martinCar  = Car(martin, "GM", 2010, Color.black)
    val martinCar2 = Car(martin, "Ferrari", 2012, Color.red)
    val martinCar3 = Car(martin, "Porshe", 2011, Color.red)
    
    val martinHome = Home(martin, None)
    val jackHome   = Home(jack, Some(Address("221B Baker Street", "London", "England")))
    
    List(martine, martin, jack, 
        martineCar, martinCar, martinCar2, martinCar3,
        martinHome, jackHome
    ).foreach(session.insert(_)) 

    session.fireAllRules()

    session.dispose()
  }
}
