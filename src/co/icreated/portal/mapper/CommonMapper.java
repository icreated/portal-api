package co.icreated.portal.mapper;

import java.util.List;

import org.compiere.util.ValueNamePair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.icreated.portal.api.model.ValueLabelDto;

@Mapper(componentModel = "spring")
public abstract class CommonMapper {

  @Mapping(source = "name", target = "label")
  public abstract ValueLabelDto vnpToValueLabelDto(ValueNamePair valueNamePair);

  public abstract List<ValueLabelDto> vnpListToValueLabelDtoList(ValueNamePair[] valueNamePairList);

}
