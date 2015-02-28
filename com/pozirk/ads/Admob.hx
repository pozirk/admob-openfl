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

import openfl.events.EventDispatcher;
import openfl.utils.JNI;

class Admob extends EventDispatcher
{
	public static inline var SIZE_BANNER:Int = 1; //320x50
	public static inline var SIZE_MEDIUM_RECTANGLE:Int = 2; //300x250
	public static inline var SIZE_FULL_BANNER:Int = 3; //468x60
	public static inline var SIZE_LEADERBOARD:Int = 4; //728x90
	public static inline var SIZE_SMART_BANNER:Int = 5; //A dynamically sized banner that is full-width and auto-height.
	public static inline var SIZE_WIDE_SKYSCRAPER:Int = 6; //160x600
	
	//> from: http://developer.android.com/reference/android/widget/RelativeLayout.html
	public static inline var HALIGN_LEFT:Int = 9;
	public static inline var HALIGN_CENTER:Int = 14;
	public static inline var HALIGN_RIGHT:Int = 11;
	
	public static inline var VALIGN_TOP:Int = 10;
	public static inline var VALIGN_MIDDLE:Int = 15;
	public static inline var VALIGN_BOTTOM:Int = 12;
	
	private static inline var EXT_ADMOB:String = "com.pozirk.ads.Admob";
	
#if android
	public var _inited(default, null):Int = 0;
	private var _initFunc:Dynamic;
	private var _showAdFunc:Dynamic;
	private var _hideAdFunc:Dynamic;
	private var _cacheInterstitialFunc:Dynamic;
	private var _showInterstitialFunc:Dynamic;
	
	public function init()
	{
		if(_initFunc == null)
			_initFunc = openfl.utils.JNI.createStaticMethod(EXT_ADMOB, "init", "(Lorg/haxe/lime/HaxeObject;)V");
			
		_initFunc(this);
	}
	
	/**
	 * Show ad
	 * @param	adID - Ad unit ID
	 * @param	size - one of the constants from AdParams
	 * @param	halign - left, center, right
	 * @param	valign - top, middle, bottom
	 */
	public function showAd(adID:String, size:Int, halign:Int, valign:Int):Void
	{
		if(_inited == 1)
		{
			if(_showAdFunc == null)
				_showAdFunc = openfl.utils.JNI.createStaticMethod(EXT_ADMOB, "showAd", "(Ljava/lang/String;III)V");
			
			_showAdFunc(adID, size, halign, valign);
		}
		else
			onStatus(AdmobEvent.AD_SHOW_FAIL, "Admob not initialized!");
	}
	
	public function hideAd():Void
	{
		if(_hideAdFunc == null)
			_hideAdFunc = openfl.utils.JNI.createStaticMethod(EXT_ADMOB, "hideAd", "()V");
			
		_hideAdFunc();
	}
	
	/**
	 * Cache interstitial ad, listen for AdmobEvent.INTERSTITIAL_CACHE_OK before showing it
	 * @param	adID - Ad unit ID
	 */
	public function cacheInterstitial(adID:String):Void
	{
		if(_inited == 1)
		{
			if(_cacheInterstitialFunc == null)
				_cacheInterstitialFunc = openfl.utils.JNI.createStaticMethod(EXT_ADMOB, "cacheInterstitial", "(Ljava/lang/String;)V");
			
			_cacheInterstitialFunc(adID);
		}
		else
			onStatus(AdmobEvent.INTERSTITIAL_CACHE_FAIL, "Admob not initialized!");
	}
	
	/**
	 * Show interstitial ad, if it is not cached yet, nothing will be shown
	 */
	public function showInterstitial():Void
	{
		if(_showInterstitialFunc == null)
			_showInterstitialFunc = openfl.utils.JNI.createStaticMethod(EXT_ADMOB, "showInterstitial", "()V");
			
		_showInterstitialFunc();
	}
	
	public function onStatus(code:String, reason:String):Void
	{
		//trace("onStatus: "+code);
		var ae:AdmobEvent = null;
		switch(code)
		{
			case "INIT_OK":
				_inited = 1;
				ae = new AdmobEvent(AdmobEvent.INIT_OK);
			
			case "AD_SHOW_OK":
				ae = new AdmobEvent(AdmobEvent.AD_SHOW_OK);
			
			case "AD_SHOW_FAIL":
				ae = new AdmobEvent(AdmobEvent.AD_SHOW_FAIL, reason);
			
			case "AD_LEFT_APP":
				ae = new AdmobEvent(AdmobEvent.AD_LEFT_APP);
			
			case "AD_OPENED":
				ae = new AdmobEvent(AdmobEvent.AD_OPENED, reason);
			
			case "AD_CLOSED":
				ae = new AdmobEvent(AdmobEvent.AD_CLOSED);
			
			case "INTERSTITIAL_SHOW_OK":
				ae = new AdmobEvent(AdmobEvent.INTERSTITIAL_SHOW_OK);
			
			case "INTERSTITIAL_SHOW_FAIL":
				ae = new AdmobEvent(AdmobEvent.INTERSTITIAL_SHOW_FAIL, reason);
			
			case "INTERSTITIAL_LEFT_APP":
				ae = new AdmobEvent(AdmobEvent.INTERSTITIAL_LEFT_APP);
			
			case "INTERSTITIAL_CACHE_OK":
				ae = new AdmobEvent(AdmobEvent.INTERSTITIAL_CACHE_OK);
			
			case "INTERSTITIAL_CACHE_FAIL":
				ae = new AdmobEvent(AdmobEvent.INTERSTITIAL_CACHE_FAIL, reason);
			
			case "INTERSTITIAL_OPENED":
				ae = new AdmobEvent(AdmobEvent.INTERSTITIAL_OPENED);
			
			case "INTERSTITIAL_CLOSED":
				ae = new AdmobEvent(AdmobEvent.INTERSTITIAL_CLOSED);
		}
		
		if(ae != null)
			this.dispatchEvent(ae);
	}
#end
}