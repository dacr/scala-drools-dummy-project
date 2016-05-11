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

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import collection.JavaConversions._

import model._

@RunWith(classOf[JUnitRunner])
class DummyTest extends FunSuite with ShouldMatchers {
  
  def model1 = {
    val martine = Someone(name="Martine", age=30, nicknames=List("titine", "titi"), attributes=Map("hairs"->"brown"))
    val martin  = Someone(name="Martin", age=40, nicknames=List("tintin", "titi"), attributes=Map("hairs"->"black"))
    val jack    = Someone(name="Jack", age=12, nicknames=List("jacquouille"), attributes=Map("eyes"->"blue"))
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
  
  test("fired up test") {
    val found = Dummy.analyze(model1, "KB-People.drl")
    val all = found collect { case x:Information => x}
    all.foreach{i=> info(i.toString)}
    
    val valuableInfos = all collect { case x:InformationRemarkable => x}
    val partialInfos = all collect { case x:InformationRequest => x}
    
    valuableInfos should have size(2)
    partialInfos should have size(2)
    
    partialInfos.map(_.someone.name) should contain("Martine")
  }
  
}
