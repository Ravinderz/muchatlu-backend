package com.muchatlu.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.muchatlu.model.Person;

import java.io.IOException;

public class PersonSerializer extends StdSerializer<Person> {

    public PersonSerializer() {
        this(null);
    }

    public PersonSerializer(Class<Person> t) {
        super(t);
    }

    @Override
    public void serialize(
            Person value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.id);
        jgen.writeStringField("username", value.username);
        jgen.writeStringField("email", value.email);
        jgen.writeStringField("avatar", value.avatar);

        if(value.getIsOnline() != null){
            jgen.writeBooleanField("isOnline", value.isOnline);
        }else{
            jgen.writeNullField("isOnline");
        }

        if(value.getStatus() != null){
            jgen.writeStringField("status", value.status);
        }else{
            jgen.writeNullField("status");
        }

        jgen.writeArrayFieldStart("friends");
        for(Person user : value.getFriends()) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", user.id);
            jgen.writeStringField("username", user.username);
            jgen.writeStringField("email", user.email);
            jgen.writeStringField("avatar", user.avatar);
            jgen.writeBooleanField("isOnline", user.isOnline);
            jgen.writeStringField("status", user.status);
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
