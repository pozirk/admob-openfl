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
	protected static AdmobListener _listenerAd = null;
	protected static AdmobListener _listenerInter = null;
	
	public static void init(HaxeObject callback)
	{
		_callback = callback;
		_listenerAd = new AdmobListener(_callback, "AD");
		_listenerInter = new AdmobListener(_callback, "INTERSTITIAL");
		
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
	
	public static void showAd(final String adID, final int size, final int halign, final int valign)
	{
		try
		{
			hideAd();
  	
			Extension.mainActivity.runOnUiThread(new Runnable()
			{
				public void run()
				{
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
					
					_adView.setAdListener(_listenerAd);
			
					_adView.loadAd(adRequest);
					
					_params = new RelativeLayout.LayoutParams(-2, -2);
					_params.addRule(halign, -1);
					_params.addRule(valign, -1);
					
					//_parentView.addView(_adView, _params);
				}
			});
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
		Extension.mainActivity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				if(_adView != null)
				{
					_adView.pause();
					_parentView.removeView(_adView);
					_adView.destroy();
					
					_bannerOnTop = 0;
					_adView = null;
				}
			}
  	});
  }
	
	public static void cacheInterstitial(final String adID)
  {
		Extension.mainActivity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				//Log.d("admob", "cacheInterstitial: "+adID);
				_interstitial = new InterstitialAd(Extension.mainActivity);
				_interstitial.setAdUnitId(adID);

				AdRequest adRequest = null;
				adRequest = new AdRequest.Builder().build();
				
				_interstitial.loadAd(adRequest);
				_interstitial.setAdListener(_listenerInter);
			}
		});
  }
  
  public static void showInterstitial()
  {
		Extension.mainActivity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				//Log.d("admob", "showInterstitial");
				if(_interstitial != null && _interstitial.isLoaded() == true)
					_interstitial.show();
			}
		});
  }
	
	@Override
	public void onResume()
	{
		if(_adView != null)
			_adView.resume();
		
		super.onResume();
	}
	
	@Override
	public void onPause()
	{
		if(_adView != null)
			_adView.pause();
		
		super.onPause();
	}
	
	@Override
	public void onDestroy()
	{
		if(_adView != null)
			_adView.destroy();
		
		super.onDestroy();
	}
}