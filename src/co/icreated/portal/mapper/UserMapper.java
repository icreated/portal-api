package co.icreated.portal.mapper;

import org.compiere.model.X_AD_User;
import org.mapstruct.Mapper;

import co.icreated.portal.model.UserDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  public abstract UserDto toDto(X_AD_User user);



}
