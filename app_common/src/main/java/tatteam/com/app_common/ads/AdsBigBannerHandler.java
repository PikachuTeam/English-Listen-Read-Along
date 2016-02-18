package tatteam.com.app_common.ads;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import tatteam.com.app_common.util.AppConstant;
import tatteam.com.app_common.util.AppLocalSharedPreferences;

/**
 * Created by ThanhNH-Mac on 11/5/15.
 */
public class AdsBigBannerHandler {
    private Context context;
    private InterstitialAd interstitialAd;
    private AppConstant.AdsType adsType;

    public AdsBigBannerHandler(Context context, AppConstant.AdsType adsType) {
        this.context = context;
        this.adsType = adsType;
    }

    public void setup() {
        if (this.adsType != null) {
            String unitId = AppLocalSharedPreferences.getInstance().getAdsId(this.adsType);
            if (!unitId.trim().isEmpty()) {
                interstitialAd = new InterstitialAd(this.context);
                interstitialAd.setAdUnitId(AppLocalSharedPreferences.getInstance().getAdsId(this.adsType));
                requestNewInterstitial();
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        requestNewInterstitial();
                    }
                });
            }
        }
    }

    public void show() {
        if (this.interstitialAd != null) {
            this.interstitialAd.show();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    public InterstitialAd getInterstitialAd() {
        return interstitialAd;
    }
}
