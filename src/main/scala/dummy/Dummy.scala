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

import java.io.FileInputStream
import java.io.InputStreamReader
import org.drools.RuleBaseFactory
import org.drools.audit.WorkingMemoryFileLogger
import org.drools.compiler.PackageBuilder



case class Someone(name:String, age:Int)



object Dummy {

  def main(args: Array[String]) {
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

    session.insert(Someone(name="toto", age=30))
    session.insert(Someone(name="tata", age=20))

    session.fireAllRules()

    session.dispose()
  }
}
