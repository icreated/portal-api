package co.icreated.portal.service;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.compiere.model.MInvoiceTax;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.AddressDto;
import co.icreated.portal.bean.DocumentDto;
import co.icreated.portal.bean.DocumentLineDto;
import co.icreated.portal.bean.InvoiceDto;
import co.icreated.portal.bean.TaxDto;
import co.icreated.portal.bean.VOpenItemDto;

@Service
public class InvoiceService {

	CLogger log = CLogger.getCLogger(InvoiceService.class);

	@Autowired
	Properties ctx;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	PaymentService paymentService;

	/**
	 * Get all invoices by C_BPartner_ID
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public List<DocumentDto> findBPartnerInvoices(int C_BPartner_ID) {

		String sql = "SELECT C_Invoice_ID as id, DateInvoiced as Date, * FROM C_Invoice WHERE AD_Client_ID = ? AND C_BPartner_ID = ? "
				+ "ORDER BY DocumentNo DESC";

		return jdbcTemplate.query(sql, new Object[] { Env.getAD_Client_ID(ctx), C_BPartner_ID },
				new BeanPropertyRowMapper<DocumentDto>(DocumentDto.class));
	}

	/**
	 * Get Invoice by given C_Invoice_ID and C_BPartner_ID
	 * 
	 * @param C_Invoice_ID
	 * @param C_BPartner_ID
	 * @return
	 */
	public InvoiceDto findInvoiceById(int C_Invoice_ID, int C_BPartner_ID) {

		String sql = "SELECT i.C_Invoice_ID, i.DocumentNo, i.Description, i.docStatus, i.dateInvoiced, i.totalLines, "
				+ "bpl.C_BPartner_Location_ID, bpl.Name addrLabel, u.Name userName, l.Address1, l.Address2, l.Postal, l.City, bpl.phone, "
				+ "l.C_Country_ID, c.Name countryName, i.GrandTotal " + "FROM C_Invoice i "
				+ "INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = i.C_BPartner_Location_ID  "
				+ "INNER JOIN C_Location l ON l.C_Location_ID = bpl.C_Location_ID  "
				+ "INNER JOIN C_Country c ON c.C_Country_ID = l.C_Country_ID  "
				+ "LEFT JOIN AD_User u ON i.AD_User_ID = u.AD_User_ID  "
				+ "WHERE i.AD_Client_ID = ? AND i.C_Invoice_ID = ? AND i.C_BPartner_ID = ?";

		return jdbcTemplate.queryForObject(sql, new Object[] { Env.getAD_Client_ID(ctx), C_Invoice_ID, C_BPartner_ID },
				(rs, rowNum) -> {

					AddressDto invoiceAddress = new AddressDto(rs.getInt("C_BPartner_Location_ID"),
							rs.getString("addrLabel"), rs.getString("userName"), rs.getString("address1"),
							rs.getString("address2"), rs.getString("postal"), rs.getString("city"),
							rs.getString("phone"), rs.getInt("C_Country_ID"), rs.getString("countryName"));

					InvoiceDto invoice = new InvoiceDto(rs.getInt("C_Invoice_ID"), rs.getString("documentNo"),
							rs.getString("description"), rs.getString("docStatus"), rs.getDate("dateInvoiced"),
							rs.getBigDecimal("totalLines"), rs.getBigDecimal("grandTotal"));

					invoice.setBillAddress(invoiceAddress);
					invoice.setLines(findInvoiceLines(rs.getInt("C_Invoice_ID")));
					invoice.setPayments(paymentService.findPayments(rs.getInt("C_Invoice_ID"), C_BPartner_ID));

					List<MInvoiceTax> taxes = new Query(ctx, MInvoiceTax.Table_Name, "C_Invoice_ID=?", null)
							.setParameters(C_Invoice_ID).list();

					invoice.setTaxes(
							taxes.stream().map(t -> new TaxDto(MTax.get(ctx, t.getC_Tax_ID()).getName(), t.getTaxAmt()))
									.collect(Collectors.toList()));

					return invoice;
				}

		);
	}

	/**
	 * Get Invoice Lines by Invoice ID
	 * 
	 * @param C_Invoice_ID
	 * @return
	 */
	public List<DocumentLineDto> findInvoiceLines(int C_Invoice_ID) {

		String sql = "SELECT l.C_InvoiceLine_ID as id, l.M_Product_ID as productId, l.PriceActual as price, "
				+ "p.Description as description, l.qtyInvoiced as qty, * " + "FROM C_InvoiceLine l "
				+ "INNER JOIN M_Product p ON l.M_Product_ID = p.M_Product_ID "
				+ "WHERE l.AD_Client_ID = ? AND l.C_Invoice_ID = ? " + "ORDER BY l.Line";

		return jdbcTemplate.query(sql, new Object[] { Env.getAD_Client_ID(ctx), C_Invoice_ID },
				new BeanPropertyRowMapper<DocumentLineDto>(DocumentLineDto.class));
	}

	/**
	 * Get Open Items (Payments to do)
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public List<VOpenItemDto> findOpenItems(int C_BPartner_ID) {

		String sql = "SELECT C_Invoice_ID, C_Order_ID, C_BPartner_ID, C_BPartner_Location_ID, C_Currency_ID,"
				+ "documentNo, description, docStatus, " + "isSOTrx, isActive, "
				+ "dateOrdered, dateInvoiced, dueDate, netDays, " + "totalLines, grandTotal, paidAmt, openAmt "
				+ "FROM RV_OpenItem WHERE AD_Client_ID = ? AND C_BPartner_ID = ? AND isSOTrx='Y' "
				+ "ORDER BY dateInvoiced DESC";

		return jdbcTemplate.query(sql, new Object[] { Env.getAD_Client_ID(ctx), C_BPartner_ID },
				(rs, rowNum) -> new VOpenItemDto(rs.getInt("C_Invoice_ID"), rs.getInt("C_Order_ID"),
						rs.getInt("C_BPartner_ID"), rs.getInt("C_BPartner_Location_ID"), rs.getInt("C_Currency_ID"),
						rs.getString("documentNo"), rs.getString("description"), rs.getString("docStatus"),
						rs.getString("isSOTrx").equals("Y"), rs.getString("isActive").equals("Y"),
						rs.getTimestamp("dateOrdered"), rs.getTimestamp("dateInvoiced"), rs.getTimestamp("dueDate"),
						rs.getInt("netDays"), rs.getBigDecimal("totalLines"), rs.getBigDecimal("grandTotal"),
						rs.getBigDecimal("paidAmt"), rs.getBigDecimal("openAmt")));

	}

}
