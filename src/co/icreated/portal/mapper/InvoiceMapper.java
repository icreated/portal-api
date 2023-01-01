package co.icreated.portal.mapper;

import java.util.List;

import org.compiere.model.MBPartner;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MLocation;
import org.compiere.model.MTax;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import co.icreated.portal.model.AddressDto;
import co.icreated.portal.model.DocumentDto;
import co.icreated.portal.model.InvoiceDto;
import co.icreated.portal.model.InvoiceLineDto;
import co.icreated.portal.model.TaxDto;
import co.icreated.portal.service.PaymentService;

@Mapper(componentModel = "spring", uses = {PaymentService.class},
    imports = {MBPartner.class, MLocation.class, MTax.class})
public abstract class InvoiceMapper {

  @Autowired
  PaymentService paymentService;

  @Mapping(target = "countryName", source = "country.name")
  public abstract AddressDto toAddressDto(MLocation location);

  @Mapping(target = "name",
      expression = "java(MTax.get(invoiceTax.getCtx(), invoiceTax.getC_Tax_ID()).getName())")
  @Mapping(target = "tax", source = "taxAmt")
  public abstract TaxDto toTaxDto(MInvoiceTax invoiceTax);

  public abstract List<TaxDto> toTaxDtoList(MInvoiceTax[] invoiceTaxes);


  @Mapping(target = "id", source = "c_Invoice_ID")
  @Mapping(target = "bpartnerName",
      expression = "java(MBPartner.get(invoice.getCtx(), invoice.getC_BPartner_ID()).getName())")
  @Mapping(target = "date", source = "dateInvoiced")
  @Mapping(target = "poReference", source = "POReference")
  @Mapping(target = "currency", source = "currencyISO")
  public abstract InvoiceDto toDto(MInvoice invoice);

  @AfterMapping
  public void updateInvoice(MInvoice invoice, @MappingTarget InvoiceDto invoiceDto) {
    MLocation billLocation =
        MLocation.getBPLocation(invoice.getCtx(), invoice.getC_BPartner_Location_ID(), null);
    AddressDto address = toAddressDto(billLocation);
    address.setId(invoice.getC_BPartner_Location_ID());
    invoiceDto.setBillAddress(address);
    invoiceDto.setPayments(paymentService.findInvoicePayments(invoice.getC_Invoice_ID()));


    invoiceDto.setTaxes(toTaxDtoList(invoice.getTaxes(false)));
  }



  @Mapping(target = "id", source = "c_Invoice_ID")
  @Mapping(target = "bpartnerName",
      expression = "java(MBPartner.get(invoice.getCtx(), invoice.getC_BPartner_ID()).getName())")
  @Mapping(target = "date", source = "dateInvoiced")
  @Mapping(target = "poReference", source = "POReference")
  @Mapping(target = "currency", source = "currencyISO")
  public abstract DocumentDto toDocumentDto(MInvoice invoice);

  public abstract List<DocumentDto> toDocumentDtoList(List<MInvoice> invoices);


  @Mapping(target = "id", source = "c_InvoiceLine_ID")
  @Mapping(target = "qty", source = "qtyInvoiced")
  @Mapping(target = "price", source = "priceActual")
  public abstract InvoiceLineDto toDto(MInvoiceLine invoice);

}
