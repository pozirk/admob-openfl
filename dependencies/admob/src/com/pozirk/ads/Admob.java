/* Copyright (c) 2015 Pozirk Games
* http://www.pozirk.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.pozirk.ads;

//import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Admob extends Extension
{
	protected static AdView _adView = null;
  protected static AdSize _adSize = AdSize.BANNER;
  protected static RelativeLayout _parentView;
	protected static RelativeLayout.LayoutParams _params;
  protected static InterstitialAd _interstitial;
  protected static int _bannerOnTop = 0;
	protected static HaxeObject _callback = null;
	
	public static void init(HaxeObject callback)
	{
		_callback = callback;
		
		_parentView = new RelativeLayout(Extension.mainActivity);
		Extension.mainActivity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				Extension.mainActivity.addContentView(_parentView, new ViewGroup.LayoutParams(-1, -1));
				//Log.d("admob", "main activity: "+Extension.mainActivity);
				_callback.call("onStatus", new Object[] {"INIT_OK", ""});
			}
		});
		
		//Log.d("admob", "init: "+callback+" / "+_parentView);
	}
	
	public static void showAd(String adID, int size, int halign, int valign)
	{
		try
		{
			hideAd();
  	
			switch(size)
			{
			case 1: _adSize = AdSize.BANNER; break; //set by default, but leave it here for reference
			case 2: _adSize = AdSize.MEDIUM_RECTANGLE; break;
			case 3: _adSize = AdSize.FULL_BANNER; break;
			case 4: _adSize = AdSize.LEADERBOARD; break;
			case 5: _adSize = AdSize.SMART_BANNER; break;
			case 6: _adSize = AdSize.WIDE_SKYSCRAPER; break;
			}
			
			_adView = new AdView(Extension.mainActivity);
			_adView.setAdUnitId(adID);
			_adView.setAdSize(_adSize);
					
			AdRequest adRequest = null;
			adRequest = new AdRequest.Builder().build();
			
			_adView.setAdListener(new AdmobListener(_callback, "AD"));
			
			_adView.loadAd(adRequest);
			
			_params = new RelativeLayout.LayoutParams(-2, -2);
			_params.addRule(halign, -1);
			_params.addRule(valign, -1);
			
			//_parentView.addView(_adView, _params);
		}
		catch(Exception e)
    {
    	e.printStackTrace();
    }
		
		//Log.d("admob", "showAd: "+adID+" / "+size+" / "+halign+" / "+valign);
	}
	
	/**
   * Required to fix this problem: https://groups.google.com/forum/#!topic/google-admob-ads-sdk/avwVXvBt_sM
   */
  public static void bannerOnTop()
  {
		//Log.d("admob", "bannerOnTop");
  	if(_bannerOnTop == 0)
  	{
  		_parentView.addView(_adView, _params);
  		_bannerOnTop = 1;
  	}
  }
	
	public static void hideAd()
  {
		//Log.d("admob", "hideAd");
  	if(_adView != null)
  	{
  		_adView.pause();
  		_parentView.removeView(_adView);
  		_adView.destroy();
  	}
  	
  	_bannerOnTop = 0;
  	_adView = null;
  }
	
	public static void cacheInterstitial(String adID)
  {
		//Log.d("admob", "cacheInterstitial: "+adID);
  	_interstitial = new InterstitialAd(Extension.mainActivity);
  	_interstitial.setAdUnitId(adID);

  	AdRequest adRequest = null;
 		adRequest = new AdRequest.Builder().build();
  	
  	_interstitial.loadAd(adRequest);
  	_interstitial.setAdListener(new AdmobListener(_callback, "INTERSTITIAL"));
  }
  
  public static void showInterstitial()
  {
		//Log.d("admob", "showInterstitial");
  	if(_interstitial != null && _interstitial.isLoaded() == true)
  		_interstitial.show();
  }
}