package tatteam.com.app_common;

import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import tatteam.com.app_common.ui.dialog.MoreAppsDialog;
import tatteam.com.app_common.util.AppConstant;
import tatteam.com.app_common.util.AppLocalSharedPreferences;


/**
 * Created by ThanhNH-Mac on 10/4/15.
 */
public class AppCommon implements AppConstant {
    private static AppCommon instance;
    private Context context;

    private AppCommon() {
    }

    public static AppCommon getInstance() {
        if (instance == null) {
            instance = new AppCommon();
        }
        return instance;
    }

    public void initIfNeeded(Context context) {
        if (this.context == null) {
            this.context = context;
            AppLocalSharedPreferences.getInstance().initIfNeeded(this.context);
        }
    }

    public void increaseLaunchTime() {
        AppLocalSharedPreferences.getInstance().increaseAppLaunchTime();
    }

    public MoreAppsDialog openMoreAppDialog(Context activity) {
        return openMoreAppDialog(activity, null);
    }

    public MoreAppsDialog openMoreAppDialog(Context activity, String url) {
        MoreAppsDialog moreAppsDialog = new MoreAppsDialog(activity, url);
        moreAppsDialog.show();
        return moreAppsDialog;
    }

    public void syncAdsIfNeeded(final AdsType... adsTypes) {
        if (AppLocalSharedPreferences.getInstance().shouldSyncAds(RE_SYNC_ADS_LAUNCH_TIME_INTERVAL, adsTypes)) {
            Ion.with(context)
                    .load(DEFAULT_ADS_URL)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                if (result != null) {
                                    for (AdsType adsType : adsTypes) {
                                        String adsUnitId = result.get(adsType.getType()).getAsString();
                                        if (adsUnitId != null && !adsUnitId.trim().isEmpty()) {
                                            AppLocalSharedPreferences.getInstance().setAdsId(adsType, adsUnitId);
                                        } else {
                                            AppLocalSharedPreferences.getInstance().removeAdsId(adsType);
                                        }
                                    }
                                } else {
                                    for (AdsType adsType : adsTypes) {
                                        AppLocalSharedPreferences.getInstance().removeAdsId(adsType);
                                    }
                                }
                            } catch (Exception ex) {
                                for (AdsType adsType : adsTypes) {
                                    AppLocalSharedPreferences.getInstance().removeAdsId(adsType);
                                }
                            }
                        }
                    });
        }
    }

    public void destroy() {
        instance = null;
    }

}
