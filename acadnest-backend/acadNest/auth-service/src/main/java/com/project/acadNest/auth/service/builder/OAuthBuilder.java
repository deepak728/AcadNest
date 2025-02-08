package com.project.acadNest.auth.service.builder;

import com.project.acadNest.auth.service.dao.AuthDao;
import com.project.acadNest.auth.service.model.MUser;
import com.project.acadNest.auth.service.pojo.User;
import com.project.acadNest.auth.service.transformer.UserTransformer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;


@Getter
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OAuthBuilder {

    private final AuthDao authDao;

    public User saveUser(User user){
        if(user ==null) return null;
        User isAlreadyPresent = findByEmail(user.getEmailId());
        if(isAlreadyPresent!=null) return isAlreadyPresent;

        return UserTransformer.toPojo(authDao.save(UserTransformer.toEntity(user)));
    }

    private User findByEmail(String email){
        if(email==null || email.isEmpty()) return null;
        Optional<MUser> mUser = authDao.findUserByEmail(email);
        if(mUser.isPresent())
            return UserTransformer.toPojo(mUser.get());
        else return null;

    }
}
