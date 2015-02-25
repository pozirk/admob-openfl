/* Copyright (c) 2015 Pozirk Games
 * http://www.pozirk.com
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