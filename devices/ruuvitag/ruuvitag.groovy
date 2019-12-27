/*****************************************************************************************************************
 *  Copyright Aki Hämäläinen
 *
 *  Name: RuuviTag
 *
 *  Date: 2019-12-27
 *
 *  Version: 0.1
 *
 *  Source: https://github.com/akihamalainen/SmartThings/tree/master/devices/ruuvitag/ruuvitag.groovy
 *
 *  Author: Aki Hämäläinen
 *
 *  Description: A custom device handler to be used for creting virtual devices for accessing RuuviTag measurements from SmartThings. 
 *  		 	 Should be used together with 'RuuviTag Listener' SmartApp that retrieves the measurements from InfluxDB database:
 *  			 https://github.com/akihamalainen/SmartThings/tree/master/smartapps/ruuvitag-listener/ruuvitag-listener.groovy
 *
 *  		     More information about RuuviTags: https://ruuvi.com/
 *
 *  License:
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 * 
 /*****************************************************************************************************************/
metadata {
	definition (name: "RuuviTag", namespace: "aki.hamalainen", author: "Aki Hamalainen", cstHandler: true) {
		capability "Temperature Measurement"
		capability "Relative Humidity Measurement"
        capability "Voltage Measurement"
        capability "Signal Strength"
        capability "Sensor"        
        
        command "setTemperature", ["number"]
        command "setRelativeHumidity", ["number"]
        command "setVoltage", ["number"]
        command "setRSSI", ["number"]
	}
    
	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "RuuviTag(${device.getName()}): parse '${description}'"
	// TODO: handle 'battery' attribute
	// TODO: handle 'humidity' attribute
	// TODO: handle 'temperature' attribute

}

def installed() {
	log.debug("RuuviTag(${device.getName()}): installed.")
    initialize()
}

def updated() {
	log.debug("RuuviTag(${device.getName()}): updated.")
    initialize()
}

def initialize() {
	log.debug("RuuviTag(${device.getName()}): initialize.")

    sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
    sendEvent(name: "healthStatus", value: "online")
    sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
    
    log.debug("NetworkId: ${device.getDeviceNetworkId()}");
                
    if(device.getDeviceNetworkId().length() != 12) {
        log.error("RuuviTag network ID must the MAC address of the sensor (e.g., 'DF540BBBBF55')")
    }
}

def setTemperature(value) {
	//log.debug("RuuviTag(${device.getName()}): setTemperature: '${value} C'")
    sendEvent(name:"temperature", value: value, unit: "C")
}

def setRelativeHumidity(value) {
	//log.debug("RuuviTag(${device.getName()}): setRelativeHumidity: '${value} %'")
    sendEvent(name:"humidity", value: value, unit: "%")
}

def setVoltage(value) {
	//log.debug("RuuviTag(${device.getName()}): setVoltage: '${value} V'")
    sendEvent(name:"voltage", value: value, unit: "V")
}

def setRSSI(value) {
	//log.debug("RuuviTag(${device.getName()}): setRSSI: '${value}'")
    sendEvent(name:"rssi", value: value, unit: "dBm")
}