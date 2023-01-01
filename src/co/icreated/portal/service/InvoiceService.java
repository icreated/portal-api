package co.icreated.portal.service;

import java.util.List;
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.VOpenItemDto;
import co.icreated.portal.exceptions.PortalNotFoundException;
import co.icreated.portal.mapper.InvoiceMapper;
import co.icreated.portal.model.DocumentDto;
import co.icreated.portal.model.InvoiceDto;

@Service
public class InvoiceService {

  CLogger log = CLogger.getCLogger(InvoiceService.class);

  Properties ctx;
  PaymentService paymentService;
  InvoiceMapper invoiceMapper;

  public InvoiceService(Properties ctx, PaymentService paymentService,
      InvoiceMapper invoiceMapper) {
    this.ctx = ctx;
    this.paymentService = paymentService;
    this.invoiceMapper = invoiceMapper;
  }


  /**
   * Get all invoices by C_BPartner_ID
   *
   * @param C_BPartner_ID
   * @return
   */
  public List<DocumentDto> findBPartnerInvoices(int C_BPartner_ID) {
    List<MInvoice> invoices = new Query(ctx, MInvoice.Table_Name, "C_BPartner_ID=?", null)
        .setParameters(C_BPartner_ID).setOrderBy("DocumentNo DESC").list();
    return invoiceMapper.toDocumentDtoList(invoices);
  }


  /**
   * Get Invoice by given C_Invoice_ID and C_BPartner_ID
   *
   * @param C_Invoice_ID
   * @param C_BPartner_ID
   * @return
   */
  public InvoiceDto findInvoiceById(int C_Invoice_ID) {

    MInvoice invoice = MInvoice.get(ctx, C_Invoice_ID);
    if (invoice == null) {
      throw new PortalNotFoundException("Invoice not exists");
    }
    return invoiceMapper.toDto(invoice);
  }



  /**
   * Get Open Items (Payments to do)
   *
   * @param C_BPartner_ID
   * @return
   */
  public List<VOpenItemDto> findOpenItems(int C_BPartner_ID) {

    String sql =
        "SELECT C_Invoice_ID, C_Order_ID, C_BPartner_ID, C_BPartner_Location_ID, C_Currency_ID,"
            + "documentNo, description, docStatus, " + "isSOTrx, isActive, "
            + "dateOrdered, dateInvoiced, dueDate, netDays, "
            + "totalLines, grandTotal, paidAmt, openAmt "
            + "FROM RV_OpenItem WHERE AD_Client_ID = ? AND C_BPartner_ID = ? AND isSOTrx='Y' "
            + "ORDER BY dateInvoiced DESC";

    return null;
    // return jdbcTemplate.query(sql, new Object[] { Env.getAD_Client_ID(ctx), C_BPartner_ID },
    // (rs, rowNum) -> new VOpenItemDto(rs.getInt("C_Invoice_ID"), rs.getInt("C_Order_ID"),
    // rs.getInt("C_BPartner_ID"), rs.getInt("C_BPartner_Location_ID"), rs.getInt("C_Currency_ID"),
    // rs.getString("documentNo"), rs.getString("description"), rs.getString("docStatus"),
    // rs.getString("isSOTrx").equals("Y"), rs.getString("isActive").equals("Y"),
    // rs.getTimestamp("dateOrdered"), rs.getTimestamp("dateInvoiced"), rs.getTimestamp("dueDate"),
    // rs.getInt("netDays"), rs.getBigDecimal("totalLines"), rs.getBigDecimal("grandTotal"),
    // rs.getBigDecimal("paidAmt"), rs.getBigDecimal("openAmt")));

  }

}
