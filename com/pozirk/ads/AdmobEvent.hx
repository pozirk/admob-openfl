/* Copyright (c) 2015 Pozirk Games
 * http://www.pozirk.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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