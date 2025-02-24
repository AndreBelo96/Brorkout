package com.andrea.belotti.brorkout.plans_creation.contract;

import androidx.fragment.app.Fragment;

import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.entity.User;
import com.andrea.belotti.brorkout.plans_creation.adapter.ShareFriendItemAdapter;

import java.util.List;

public interface CreationMenuContract {

    interface Presenter {
        void onNewPlanClick();
        void onCopyPlanClick();
        ShareFriendItemAdapter retrieveUserListFromDB();
    }

    interface View {
    }

}
