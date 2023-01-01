package co.icreated.portal.mapper;

import java.util.List;

import org.compiere.model.MPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.icreated.portal.model.PaymentDto;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {

  @Mapping(target = "id", source = "c_Payment_ID")
  @Mapping(target = "date", source = "dateTrx")
  @Mapping(target = "currency", source = "currencyISO")
  @Mapping(target = "trxid", source = "orig_TrxID")
  public abstract PaymentDto toDto(MPayment payment);

  public abstract List<PaymentDto> toDtoList(List<MPayment> payments);

}
