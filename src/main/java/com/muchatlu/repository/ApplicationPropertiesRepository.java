package com.muchatlu.repository;

import com.muchatlu.model.ApplicationProperties;
import com.muchatlu.model.AuthenticateToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationPropertiesRepository extends JpaRepository<ApplicationProperties, Long>{

    @Query(value = "select application_value from application_properties where application_property='expo_token'",nativeQuery = true)
    public String getExpoToken();

}
