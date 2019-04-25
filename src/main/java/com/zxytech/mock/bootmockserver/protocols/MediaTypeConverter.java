package com.zxytech.mock.bootmockserver.protocols;

import com.mongodb.DBRef;
import org.bson.conversions.Bson;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.util.TypeInformation;

public class MediaTypeConverter implements MongoConverter {
  @Override
  public MongoTypeMapper getTypeMapper() {
    return null;
  }

  @Override
  public MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty>
      getMappingContext() {
    return null;
  }

  @Override
  public ConversionService getConversionService() {
    return null;
  }

  @Override
  public <R> R read(Class<R> aClass, Bson bson) {
    return null;
  }

  @Override
  public Object convertToMongoType(Object o, TypeInformation<?> typeInformation) {
    return null;
  }

  @Override
  public DBRef toDBRef(Object o, MongoPersistentProperty mongoPersistentProperty) {
    return null;
  }

  @Override
  public void write(Object o, Bson bson) {}
}
