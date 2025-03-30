package co.icreated.portal.service;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import org.compiere.model.I_C_Payment;
import org.compiere.model.MBPBankAccount;
import org.compiere.model.MBPartner;
import org.compiere.model.MBankAccount;
import org.compiere.model.MDocType;
import org.compiere.model.MLocation;
import org.compiere.model.MPayment;
import org.compiere.model.MUser;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import co.icreated.portal.api.model.CreditCardDto;
import co.icreated.portal.api.model.OpenItemDto;
import co.icreated.portal.api.model.PaymentDto;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.mapper.PaymentMapper;
import co.icreated.portal.utils.PQuery;

@Service
public class PaymentService {

  CLogger log = CLogger.getCLogger(PaymentService.class);

  Properties ctx;
  PaymentMapper paymentMapper;

  public PaymentService(Properties ctx, PaymentMapper paymentMapper) {
    this.ctx = ctx;
    this.paymentMapper = paymentMapper;
  }

  public List<PaymentDto> findInvoicePayments(int C_Invoice_ID) {

    List<MPayment> list =
        new PQuery(ctx, I_C_Payment.Table_Name, "C_Invoice_ID=?", null).setParameters(C_Invoice_ID) //
            .setOrderBy("dateTrx DESC") //
            .list();
    return paymentMapper.toDtoList(list);
  }

  public List<PaymentDto> findBPartnerPayments(int C_BPartner_ID) {

    List<MPayment> list = new PQuery(ctx, I_C_Payment.Table_Name, "C_BPartner_ID=?", null)
        .setParameters(C_BPartner_ID) //
        .setOrderBy("dateTrx DESC") //
        .list();
    return paymentMapper.toDtoList(list);
  }

  public void createPayments(SessionUser sessionUser, List<OpenItemDto> openItems,
      CreditCardDto creditCard, String trxName) {

    openItems.stream()
        .forEach(openItem -> createCreditCardPayment(sessionUser, openItem, creditCard, trxName));
  }

  public boolean createCreditCardPayment(SessionUser sessionUser, OpenItemDto openItem,
      CreditCardDto creditCard, String trxName) {

    MPayment payment = new MPayment(ctx, 0, trxName);
    payment.setAD_Org_ID(Env.getAD_Org_ID(ctx));
    payment.setIsSelfService(true);
    payment.setAmount(openItem.getCurrencyId(), openItem.getOpenAmt());
    payment.setIsOnline(true);
    payment.setC_DocType_ID(true);
    payment.setTrxType(MPayment.TRXTYPE_Sales);
    payment.setTenderType(MPayment.TENDERTYPE_CreditCard);
    payment.setC_Invoice_ID(openItem.getInvoiceId());
    payment.setC_Order_ID(openItem.getOrderId());
    payment.setIsReceipt(true);
    payment.setIsApproved(true);
    payment.setA_EMail(sessionUser.getEmail());

    MBPBankAccount account = getBankAccount(sessionUser, openItem, trxName);
    payment.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
    payment.setBP_BankAccount(account);
    payment.saveToBP_BankAccount(account);

    MBankAccount ba =
        new PQuery(ctx, MBankAccount.Table_Name, "AD_Org_ID=? AND C_Currency_ID=?", trxName)
            .setParameters(Env.getAD_Org_ID(ctx), openItem.getCurrencyId()) //
            .setOrderBy("IsDefault DESC") //
            .first();
    if (ba != null) {
      payment.setC_BankAccount_ID(ba.getC_BankAccount_ID());
    }

    MDocType[] doctypes = MDocType.getOfDocBaseType(ctx, MDocType.DOCBASETYPE_ARReceipt);
    Stream<MDocType> stream = Arrays.stream(doctypes);
    MDocType doctype = stream.filter(item -> item.getAD_Org_ID() == Env.getAD_Org_ID(ctx))
        .findFirst().orElse(doctypes[0]);

    payment.setC_DocType_ID(doctype.getC_DocType_ID());

    // add TransactionId from Bank Return!
    // payment.setOrig_TrxID(paymentInfo.getTransactionId());
    payment.setCreditCardType(creditCard.getCreditCardType());
    payment.setCreditCardNumber(creditCard.getCreditCardNumber());
    payment.setCreditCardExpMM(creditCard.getCreditCardExpMM());
    payment.setCreditCardExpYY(creditCard.getCreditCardExpYY());

    payment.processIt(DocAction.ACTION_Complete);
    return payment.save();
  }

  public MBPBankAccount getBankAccount(SessionUser sessionUser, OpenItemDto openItem,
      String trxName) {

    MBPartner bp = new MBPartner(ctx, sessionUser.getPartnerId(), trxName);
    return Stream.of(bp.getBankAccounts(false))
        .filter(ba -> ba.getAD_User_ID() == sessionUser.getUserId()).findAny().orElseGet(() -> {
          MUser user = new MUser(ctx, sessionUser.getUserId(), trxName);
          MLocation location = MLocation.get(ctx, openItem.getBpartnerLocationId(), trxName);
          MBPBankAccount bankAccount = new MBPBankAccount(ctx, bp, user, location);
          bankAccount.setAD_User_ID(user.getAD_User_ID());
          bankAccount.save();
          return bankAccount;
        });
  }

}
