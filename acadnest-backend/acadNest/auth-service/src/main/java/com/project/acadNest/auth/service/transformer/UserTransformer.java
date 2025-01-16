package com.project.acadNest.auth.service.transformer;

import com.project.acadNest.auth.service.model.MUser;
import com.project.acadNest.auth.service.pojo.User;

public class UserTransformer {

    // Convert POJO to Entity
    public static MUser toEntity(User user) {
        if (user == null) {
            return null;
        }

        MUser mUser = new MUser();
        mUser.setId(user.getId());
        mUser.setEmailId(user.getEmailId());
        mUser.setPassword(user.getPassword());
        mUser.setGoogleId(user.getGoogleId());
        mUser.setRole(user.getRole());
        // Set other fields as null by default
        return mUser;
    }

    // Convert Entity to POJO
    public static User toPojo(MUser mUser) {
        if (mUser == null) {
            return null;
        }

        User user = new User();
        user.setId(mUser.getId());
        user.setEmailId(mUser.getEmailId());
        user.setPassword(mUser.getPassword());
        user.setGoogleId(mUser.getGoogleId());
        user.setRole(mUser.getRole());
        // Leave non-common fields as null
        return user;
    }
}

