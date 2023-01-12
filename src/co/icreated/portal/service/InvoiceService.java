package co.icreated.portal.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import co.icreated.portal.exceptions.PortalNotFoundException;
import co.icreated.portal.mapper.InvoiceMapper;
import co.icreated.portal.model.DocumentDto;
import co.icreated.portal.model.InvoiceDto;
import co.icreated.portal.model.OpenItemDto;
import co.icreated.portal.utils.PQuery;

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
    List<MInvoice> invoices =
        new PQuery(ctx, MInvoice.Table_Name, "C_BPartner_ID=?", null).setParameters(C_BPartner_ID) //
            .setOrderBy("DocumentNo DESC") //
            .list();
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
  public List<OpenItemDto> findOpenItems(int C_BPartner_ID) {

    String sql =
        "SELECT C_Invoice_ID, C_Order_ID, C_BPartner_ID, C_BPartner_Location_ID, C_Currency_ID,"
            + "documentNo, description, docStatus, " + "isSOTrx, isActive, "
            + "dateOrdered, dateInvoiced, dueDate, netDays, "
            + "totalLines, grandTotal, paidAmt, openAmt "
            + "FROM RV_OpenItem WHERE AD_Client_ID = ? AND C_BPartner_ID = ? AND isSOTrx='Y' "
            + "ORDER BY dateInvoiced DESC";

    ArrayList<OpenItemDto> list = new ArrayList<OpenItemDto>();
    try (PreparedStatement pstmtActual = DB.prepareStatement(sql, null);) {
      pstmtActual.setInt(1, Env.getAD_Client_ID(ctx));
      pstmtActual.setInt(2, C_BPartner_ID);
      ResultSet rs = pstmtActual.executeQuery();

      while (rs.next()) {
        list.add(
        //@formatter:off
	        	new OpenItemDto()
	        	.invoiceId(rs.getInt("C_Invoice_ID"))
	            .orderId(rs.getInt("C_Order_ID"))
	            .bpartnerId(rs.getInt("C_BPartner_ID"))
	            .bpartnerLocationId(rs.getInt("C_BPartner_Location_ID"))
	            .currencyId(rs.getInt("C_Currency_ID"))
	            .documentNo(rs.getString("documentNo"))
	            .description(rs.getString("description"))
	            .docStatus(rs.getString("docStatus"))
	            .isSOTRX(rs.getString("isSOTrx").equals("Y"))
	            .isActive(rs.getString("isActive").equals("Y"))
	            .dateOrdered(rs.getTimestamp("dateOrdered").toLocalDateTime().toLocalDate())
	            .dateInvoiced(rs.getTimestamp("dateInvoiced").toLocalDateTime().toLocalDate())
	            .dueDate(rs.getTimestamp("dueDate").toLocalDateTime().toLocalDate())
	            .netDays(rs.getInt("netDays"))
	            .totalLines(rs.getBigDecimal("totalLines"))
	            .grandTotal(rs.getBigDecimal("grandTotal"))
	            .paidAmt(rs.getBigDecimal("paidAmt"))
	            .openAmt(rs.getBigDecimal("openAmt"))
            //@formatter:on
        );
      }



    } catch (SQLException e) {
      e.printStackTrace();
    }

    return list;
  }
}
