package tatteam.com.app_common.ads;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import tatteam.com.app_common.util.AppConstant;
import tatteam.com.app_common.util.AppLocalSharedPreferences;

/**
 * Created by ThanhNH-Mac on 10/30/15.
 */
public class AdsSmallBannerHandler extends AdListener {
    private AdView adView;
    private ViewGroup adsContainer;
    private Context context;
    private AppConstant.AdsType adsType;

    public AdsSmallBannerHandler(Context context, ViewGroup adsContainer, AppConstant.AdsType adsType) {
        this.context = context;
        this.adsContainer = adsContainer;
        this.adsType = adsType;
    }

    public void setup() {
        if (this.adsContainer != null && this.adsType != null) {
            String unitId = AppLocalSharedPreferences.getInstance().getAdsId(this.adsType);
            if (!unitId.trim().isEmpty()) {
                this.adView = new AdView(this.context);
                this.adView.setAdSize(AdSize.SMART_BANNER);
                this.adView.setAdUnitId(unitId);
                this.adsContainer.addView(adView);

                AdRequest adRequest = new AdRequest.Builder().build();
                adView.setAdListener(this);
                adView.loadAd(adRequest);
            }
        }
    }

    public AdView getAdView() {
        return this.adView;
    }

    @Override
    public void onAdClosed() {
        super.onAdClosed();
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        super.onAdFailedToLoad(errorCode);
        if (adView != null) {
            adView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAdLeftApplication() {
        super.onAdLeftApplication();
    }

    @Override
    public void onAdOpened() {
        super.onAdOpened();
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        if (adView != null) {
            adView.setVisibility(View.VISIBLE);
        }
    }


}
