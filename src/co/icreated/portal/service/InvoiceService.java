package co.icreated.portal.service;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.compiere.model.MInvoiceTax;
import org.compiere.model.MRefList;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.Address;
import co.icreated.portal.bean.Document;
import co.icreated.portal.bean.DocumentLine;
import co.icreated.portal.bean.Invoice;
import co.icreated.portal.bean.Payment;
import co.icreated.portal.bean.Tax;


@Service
public class InvoiceService {
	
	CLogger log = CLogger.getCLogger(InvoiceService.class);
	
	@Autowired
	Properties ctx;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public List<Document> findBPartnerInvoices(int C_BPartner_ID) {
		
		String sql = "SELECT C_Invoice_ID, DocumentNo, null, Description, docStatus, dateInvoiced, totalLines, grandTotal " +
				"FROM C_Invoice WHERE C_BPartner_ID=? " +
				"ORDER BY DocumentNo DESC";		
		
		return jdbcTemplate.query(sql,
				new Object[]{C_BPartner_ID},
				(rs, rowNum) ->
					new Document(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
					rs.getString(5), rs.getDate(6), rs.getBigDecimal(7), rs.getBigDecimal(8), 
					MRefList.getListName(ctx, 131, rs.getString(5)))
        );		
	}
	
	
	public Invoice findInvoiceById(int C_Invoice_ID, int C_BPartner_ID) {
		
		String sql = "SELECT i.C_Invoice_ID, i.DocumentNo, i.Description, i.docStatus, i.dateInvoiced, i.totalLines, " +
				"bpl.C_BPartner_Location_ID, bpl.Name addrLabel, u.Name userName, l.Address1, l.Address2, l.Postal, l.City, bpl.phone, " +
				"l.C_Country_ID, c.Name countryName, i.GrandTotal " +
				"FROM C_Invoice i " +
				"INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = i.C_BPartner_Location_ID  " +
				"INNER JOIN C_Location l ON l.C_Location_ID = bpl.C_Location_ID  " +
				"INNER JOIN C_Country c ON c.C_Country_ID = l.C_Country_ID  " +
				"LEFT JOIN AD_User u ON i.AD_User_ID = u.AD_User_ID  " +
				"WHERE i.C_Invoice_ID = ? AND i.C_BPartner_ID = ?";	
		
		
		return jdbcTemplate.queryForObject(sql,
				new Object[]{C_Invoice_ID, C_BPartner_ID},
				(rs, rowNum) -> {
					
					String docStatusName = MRefList.getListName(ctx, 131, rs.getString("docStatus"));
					
					Address invoiceAddress = new Address(rs.getInt("C_BPartner_Location_ID"), rs.getString("addrLabel"), 
							rs.getString("userName"),rs.getString("address1"), rs.getString("address2"), 
							rs.getString("postal"), rs.getString("city"), rs.getString("phone"), 
							rs.getInt("C_Country_ID"), rs.getString("countryName"));

					Invoice invoice = new Invoice(rs.getInt("C_Invoice_ID"), rs.getString("documentNo"), rs.getString("description"), 
							rs.getString("docStatus"), rs.getDate("dateInvoiced"),rs.getBigDecimal("totalLines"), 
							rs.getBigDecimal("grandTotal"), docStatusName);
					
					invoice.setBillAddress(invoiceAddress);
					invoice.setLines(findInvoiceLines(rs.getInt("C_Invoice_ID")));
					invoice.setPayments(findPayments(rs.getInt("C_Invoice_ID")));
		
			
					List<MInvoiceTax> taxes = new Query(ctx, MInvoiceTax.Table_Name, "C_Invoice_ID=?", null).setParameters(C_Invoice_ID)
							.list();

					invoice.setTaxes(taxes.stream()
							.map(t-> new Tax(MTax.get(ctx, t.getC_Tax_ID()).getName(), t.getTaxAmt()))
							.collect(Collectors.toList()));		
					
					return invoice;
				}
					
        );	
		
	}
	
	
	public List<DocumentLine> findInvoiceLines(int C_Invoice_ID) {
		
		String sql = "SELECT l.C_InvoiceLine_ID, l.M_Product_ID, l.Line, p.Name, p.Description, " +
				"l.PriceList, l.PriceActual, l.qtyInvoiced, l.LineNetAmt " +
				"FROM C_InvoiceLine l " +
				"INNER JOIN M_Product p ON l.M_Product_ID = p.M_Product_ID " +
				"WHERE l.C_Invoice_ID = ? ORDER BY l.Line" ;	
		
		return jdbcTemplate.query(sql,
				new Object[]{C_Invoice_ID},
				(rs, rowNum) ->
					new DocumentLine(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
							rs.getBigDecimal(6), rs.getBigDecimal(7), rs.getBigDecimal(8),rs.getBigDecimal(9))
        );		
	}

	

	public List<Payment> findPayments(int C_Invoice_ID) {
		
		String sql = "SELECT p.C_Payment_ID, p.DocumentNo, p.Description, p.docStatus, p.payAmt, p.orig_trxid, c.iso_code, p.tenderType " +
				"FROM C_Payment p " +
				"INNER JOIN C_Currency c ON p.C_Currency_ID = c.C_Currency_ID " +
				"WHERE C_Invoice_ID=? " +
				"AND DocStatus NOT IN ('DR') ORDER BY DocumentNo DESC";	
		
		return jdbcTemplate.query(sql,
				new Object[]{C_Invoice_ID},
				(rs, rowNum) ->
					new Payment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
							rs.getBigDecimal(5),  rs.getString(6),  rs.getString(7),  rs.getString(8))
        );		
	}
	



}
