package tatteam.com.app_common.ui.fragment;

import android.app.Fragment;

import tatteam.com.app_common.ui.activity.BaseActivity;

/**
 * Created by ThanhNH-Mac on 2/10/16.
 */
public class BaseFragment extends Fragment {

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            getBaseActivity().onCloseActivity();
        }
    }

    public void getHolder(String key) {
        getBaseActivity().getHolder(key);
    }

    public void putHolder(String key, Object value) {
        getBaseActivity().putHolder(key, value);
    }

    public boolean containHolder(String key) {
        return getBaseActivity().containHolder(key);
    }

    public void removeHolder(String key) {
        getBaseActivity().removeHolder(key);
    }

    public void clearAllHolder() {
        getBaseActivity().clearAllHolder();
    }

    public BaseFragment getCurrentFragment() {
        return getBaseActivity().getCurrentFragment();
    }

    public BaseFragment findFragment(String fragmentTag) {
        return getBaseActivity().findFragment(fragmentTag);
    }

    public void popToFragment(String transactionTag) {
        getBaseActivity().popToFragment(transactionTag);
    }

    public void popToFirstFragment() {
        getBaseActivity().popToFirstFragment();
    }

    public void replaceFragment(BaseFragment fragment, String fragmentTag, boolean needCommitAllowingStateLoss) {
        replaceFragment(fragment, fragmentTag, fragmentTag, needCommitAllowingStateLoss);
    }

    public void replaceFragment(BaseFragment fragment, String fragmentTag) {
        replaceFragment(fragment, fragmentTag, fragmentTag, false);
    }

    public void replaceFragment(BaseFragment fragment, String fragmentTag, String transactionTag) {
        replaceFragment(fragment, fragmentTag, transactionTag, false);
    }

    public void replaceFragment(BaseFragment fragment, String fragmentTag, String transactionTag, boolean needCommitAllowingStateLoss) {
        getBaseActivity().replaceFragment(fragment, fragmentTag, transactionTag, needCommitAllowingStateLoss);
    }


}
