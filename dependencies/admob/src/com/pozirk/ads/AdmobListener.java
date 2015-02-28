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

//import android.util.Log;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import org.haxe.lime.HaxeObject;

public class AdmobListener extends AdListener
{
	protected HaxeObject _callback = null;
	protected String _who = null;
	
	public AdmobListener(HaxeObject callback, String who)
	{
		_callback = callback;
		_who = who;
	}
	
	 @Override
   public void onAdLoaded()
	 {
		//Log.d("admob", "onAdLoaded");
		 if(_who == "AD")
		 {
			 Admob.bannerOnTop();
			 _callback.call("onStatus", new Object[] {_who+"_SHOW_OK", ""});
		 }
		 else
			_callback.call("onStatus", new Object[] {_who+"_CACHE_OK", ""});
   }

   @Override
   public void onAdFailedToLoad(int errorCode)
   {
     String errorReason = "";
     switch(errorCode)
     {
       case AdRequest.ERROR_CODE_INTERNAL_ERROR:
         errorReason = "Internal error";
         break;
       case AdRequest.ERROR_CODE_INVALID_REQUEST:
         errorReason = "Invalid request";
         break;
       case AdRequest.ERROR_CODE_NETWORK_ERROR:
         errorReason = "Network Error";
         break;
       case AdRequest.ERROR_CODE_NO_FILL:
         errorReason = "No fill";
         break;
     }
    
		//Log.d("admob", "onAdFailedToLoad: "+errorReason);
		if(_who == "AD")
			_callback.call("onStatus", new Object[] {_who+"_SHOW_FAIL", errorReason});
		else
			_callback.call("onStatus", new Object[] {_who+"_CACHE_FAIL", errorReason});
   }

   @Override
   public void onAdOpened()
   {
		 _callback.call("onStatus", new Object[] {_who+"_OPENED", "So, you've just made some money, huh?"});
   }

   @Override
   public void onAdClosed()
   {
		 _callback.call("onStatus", new Object[] {_who+"_CLOSED", ""});
   }
   
   @Override
   public void onAdLeftApplication()
   {
		 _callback.call("onStatus", new Object[] {_who+"_LEFT_APP", ""});
   }
}