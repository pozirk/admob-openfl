/* Copyright (c) 2015 Pozirk Games
 * http://inside.pozirk.com
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
package com.pozirk.ads;

import openfl.events.Event;

/**
 * Ad events
 * @author Pozirk Games (http://www.pozirk.com)
 */
class AdmobEvent extends Event
{
	public static var INIT_OK:String = "INIT_OK";
	public static var AD_SHOW_OK:String = "AD_SHOW_OK";
	public static var AD_SHOW_FAIL:String = "AD_SHOW_FAIL";
	public static var AD_LEFT_APP:String = "AD_LEFT_APP"; //Called when an Ad touch will launch a new application.
	public static var AD_OPENED:String = "AD_OPENED";
	public static var AD_CLOSED:String = "AD_CLOSED";
	public static var INTERSTITIAL_SHOW_OK:String = "INTERSTITIAL_SHOW_OK";
	public static var INTERSTITIAL_SHOW_FAIL:String = "INTERSTITIAL_SHOW_FAIL";
	public static var INTERSTITIAL_LEFT_APP:String = "INTERSTITIAL_LEFT_APP"; //Called when an Ad touch will launch a new application.
	public static var INTERSTITIAL_CACHE_OK:String = "INTERSTITIAL_CACHE_OK";
	public static var INTERSTITIAL_CACHE_FAIL:String = "INTERSTITIAL_CACHE_FAIL";
	public static var INTERSTITIAL_OPENED:String = "INTERSTITIAL_OPENED";
	public static var INTERSTITIAL_CLOSED:String = "INTERSTITIAL_CLOSED";
	
	public var _data:String; //extra info about event
	
	public function new(type:String, data:String = null)
	{
		super(type, false, false);
		_data = data;
	}
}