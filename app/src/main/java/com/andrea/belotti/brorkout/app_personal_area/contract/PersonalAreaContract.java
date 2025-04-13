package com.andrea.belotti.brorkout.app_personal_area.contract;


import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public interface PersonalAreaContract {

    interface Presenter {
        void onBackClick();
        void initData(EditText usernameET,
                      EditText weightET,
                      EditText heightET,
                      EditText fatPercentileET,
                      CircleImageView image);
    }

    interface View {
        void replaceWithStartingMenuActivity(String message);;
    }

}
