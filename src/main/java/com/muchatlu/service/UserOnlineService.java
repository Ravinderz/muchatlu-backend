package com.muchatlu.service;

import org.springframework.stereotype.Service;

import com.muchatlu.model.UserModel;

import io.reactivex.Observable;

@Service
public class UserOnlineService {
	
	public Observable<UserModel> getUserOnlineStatus(UserModel user){
		return Observable.create(s -> {
			s.onNext(user);
		});
	}
	
}
