package co.icreated.portal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.Payment;

@Service
public class PaymentService {
	
	CLogger log = CLogger.getCLogger(PaymentService.class);
	
	@Autowired
	Properties ctx;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public List<Payment> findPayments(int C_Invoice_ID, int C_BPartner_ID) {
		
		List<Object> params = new ArrayList<>();
		String sql = "SELECT p.C_Payment_ID, p.DocumentNo, p.Description, p.docStatus, p.payAmt, p.orig_trxid, c.iso_code, " + 
		"p.tenderType, p.dateTrx " +
				"FROM C_Payment p " +
				"INNER JOIN C_Currency c ON p.C_Currency_ID = c.C_Currency_ID " +
				"WHERE DocStatus NOT IN ('DR') AND p.AD_Client_ID = ? ";
		
		params.add(Env.getAD_Client_ID(ctx));
		
		if (C_Invoice_ID > 0) {
			sql += "AND C_Invoice_ID = ? ";
			params.add(C_Invoice_ID);
		}
		if (C_BPartner_ID > 0) {
			sql += "AND C_BPartner_ID = ? ";
			params.add(C_BPartner_ID);
		}

		sql += "ORDER BY DocumentNo DESC";
		
		return jdbcTemplate.query(sql,
				params.toArray(),
				(rs, rowNum) ->
					new Payment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
							rs.getBigDecimal(5),  rs.getString(6),  rs.getString(7),  rs.getString(8), rs.getTimestamp(9))
        );		
	}
	
	

	

}
