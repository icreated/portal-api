package co.icreated.portal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import org.compiere.model.MBPBankAccount;
import org.compiere.model.MBPartner;
import org.compiere.model.MBankAccount;
import org.compiere.model.MDocType;
import org.compiere.model.MLocation;
import org.compiere.model.MPayment;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.CreditCard;
import co.icreated.portal.bean.Payment;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.bean.VOpenItem;

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
	
	
	
	public void createPayments(SessionUser sessionUser, List<VOpenItem> openItems, CreditCard creditCard, String trxName) {
		
		openItems.stream().forEach(openItem -> createCreditCardPayment(sessionUser, openItem, creditCard, trxName));
		
	}
	
	
	public boolean createCreditCardPayment(SessionUser sessionUser, VOpenItem openItem, CreditCard creditCard, String trxName) {
		
		MPayment payment = new MPayment(ctx, 0, trxName);
		payment.setAD_Org_ID(Env.getAD_Org_ID(ctx));
		payment.setIsSelfService(true);
		payment.setAmount (openItem.getCurrencyId(), openItem.getOpenAmt());
		payment.setIsOnline (true);
		payment.setC_DocType_ID(true);
		payment.setTrxType(MPayment.TRXTYPE_Sales);
		payment.setTenderType(MPayment.TENDERTYPE_CreditCard);
		payment.setC_Invoice_ID (openItem.getInvoiceId());						
		payment.setC_Order_ID (openItem.getOrderId());
		payment.setIsReceipt(true);
		payment.setIsApproved(true);
		payment.setA_EMail(sessionUser.getEmail());
		
		
		MBPBankAccount account = getBankAccount(sessionUser, openItem, trxName);
		payment.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		payment.setBP_BankAccount(account);
		payment.saveToBP_BankAccount(account);

		
		MBankAccount ba = new Query(ctx,MBankAccount.Table_Name, "AD_Org_ID=? AND C_Currency_ID=?", trxName)
			.setParameters(Env.getAD_Org_ID(ctx), openItem.getCurrencyId())
			.setOrderBy("IsDefault DESC")
			.first();
		if (ba != null)
			payment.setC_BankAccount_ID(ba.getC_BankAccount_ID());
		
		
		MDocType[] doctypes = MDocType.getOfDocBaseType(ctx, MDocType.DOCBASETYPE_ARReceipt);
		Stream<MDocType> stream = Arrays.stream(doctypes);
		MDocType doctype = stream.filter(item -> item.getAD_Org_ID() == Env.getAD_Org_ID(ctx))
				.findFirst().orElse(doctypes[0]);
		
		payment.setC_DocType_ID(doctype.getC_DocType_ID());

		//add TransactionId from Bank Return!
//		payment.setOrig_TrxID(paymentInfo.getTransactionId());
		payment.setCreditCardType(creditCard.getCardType());
		payment.setCreditCardNumber(creditCard.getCreditCard());
		payment.setCreditCardExpMM(creditCard.getExpirationMonth());
		payment.setCreditCardExpYY(creditCard.getExpirationYear());
		

		payment.processIt(DocAction.ACTION_Complete);
		boolean ok = payment.save();
		return ok;
	}
	
	

	
	
	public MBPBankAccount getBankAccount(SessionUser sessionUser, VOpenItem openItem, String trxName) {
		
		MBPartner bp = new MBPartner(ctx, sessionUser.getPartnerId(), trxName);

		Stream<MBPBankAccount> stream = Arrays.stream(bp.getBankAccounts(true));
		MBPBankAccount retValue = stream.filter(item -> item.getAD_User_ID() == sessionUser.getUserId() && item.isActive())
				.findFirst().orElse(null);

		//	create new
		if (retValue == null)
		{
			MUser user = new MUser(ctx, sessionUser.getUserId(), trxName);
			
			MLocation location = MLocation.getBPLocation(ctx, openItem.getBpartnerLocationId(), trxName);
			retValue = new MBPBankAccount (ctx, bp, user, location);
			retValue.setAD_User_ID(sessionUser.getUserId());
			retValue.save();
		}
		
		return retValue;
	}

}
