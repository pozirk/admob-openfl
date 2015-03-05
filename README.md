Introduction
============

Admob OpenFL Extension. (Adnroid only)
More info on Admob: https://developers.google.com/mobile-ads-sdk/


Platforms
=========
Android (Anyone willing to extend it to support iOS? Contact me!)


License
=======

Admob OpenFL Extension is free, open-source software under the [MIT license](LICENSE.md).


Usage
=======
```haxe
import com.pozirk.ads.Admob;
import com.pozirk.ads.AdmobEvent;

...

_admob = new Admob();
_admob.addEventListener(AdmobEvent.INIT_OK, onAdmobInit);
_admob.addEventListener(AdmobEvent.INTERSTITIAL_CACHE_OK, onAdmobCache);
_admob.init(); //you can only use Admob after successful initialization

...

function onAdmobInit(ae:AdmobEvent):Void
{
	_admob.showAd([Ad unit ID], Admob.SIZE_LEADERBOARD, Admob.HALIGN_CENTER, Admob.VALIGN_TOP);
	_admob.cacheInterstitial([Ad unit ID]);
}

...

private function onAdmobCache(ae:AdmobEvent):Void
{
	_admob.showInterstitial();
}
```


Game with Admob extension
=======

Google Play: https://play.google.com/store/apps/details?id=com.pozirk.matchjong
Play level 2 in order to see interstitial ad.
