package com.empopertionssix.com.entity;

/**
 * Comprehensive enum for all banking transaction types.
 * Categorized into: Deposits, Withdrawals, Transfers, Fees, and Interest
 */
public enum TransactionType {
    
    // ==================== DEPOSIT TRANSACTIONS ====================
    CASH_DEPOSIT("Cash Deposit", "CREDIT"),
    CHECK_DEPOSIT("Check Deposit", "CREDIT"),
    ONLINE_TRANSFER_IN("Online Transfer In", "CREDIT"),
    SALARY_DEPOSIT("Salary Deposit", "CREDIT"),
    GOVERNMENT_BENEFIT("Government Benefit", "CREDIT"),
    REFUND("Refund", "CREDIT"),
    
    // ==================== WITHDRAWAL TRANSACTIONS ====================
    CASH_WITHDRAWAL("Cash Withdrawal", "DEBIT"),
    CHECK_WITHDRAWAL("Check Withdrawal", "DEBIT"),
    ATM_WITHDRAWAL("ATM Withdrawal", "DEBIT"),
    
    // ==================== TRANSFER TRANSACTIONS ====================
    SELF_TRANSFER("Self Transfer (Own Accounts)", "DEBIT"),
    ACCOUNT_TO_ACCOUNT_TRANSFER("Transfer to Another Account", "DEBIT"),
    INTERNAL_TRANSFER("Internal Transfer (Same Bank)", "DEBIT"),
    INTER_BANK_TRANSFER("Inter-Bank Transfer", "DEBIT"),
    WIRE_TRANSFER("Wire Transfer", "DEBIT"),
    NEFT_TRANSFER("NEFT Transfer (India)", "DEBIT"),
    RTGS_TRANSFER("RTGS Transfer (India)", "DEBIT"),
    IMPS_TRANSFER("IMPS Transfer (India)", "DEBIT"),
    MOBILE_TRANSFER("Mobile/UPI Transfer", "DEBIT"),
    
    // ==================== CHEQUE TRANSACTIONS ====================
    CHEQUE_ISSUED("Cheque Issued", "DEBIT"),
    CHEQUE_RECEIVED("Cheque Received", "CREDIT"),
    CHEQUE_BOUNCED("Cheque Bounced", "DEBIT"),
    
    // ==================== FEE & CHARGES ====================
    MONTHLY_MAINTENANCE_FEE("Monthly Maintenance Fee", "DEBIT"),
    TRANSACTION_FEE("Transaction Fee", "DEBIT"),
    ATM_FEE("ATM Service Fee", "DEBIT"),
    OVERDRAFT_CHARGE("Overdraft Charge", "DEBIT"),
    LATE_PAYMENT_FEE("Late Payment Fee", "DEBIT"),
    
    // ==================== INTEREST & REWARDS ====================
    INTEREST_CREDIT("Interest Credit", "CREDIT"),
    DIVIDEND_PAYMENT("Dividend Payment", "CREDIT"),
    REWARD_POINTS("Reward Points", "CREDIT"),
    CASHBACK("Cashback", "CREDIT"),
    
    // ==================== ADJUSTMENTS ====================
    CORRECTION("Correction", "CREDIT"),
    REVERSAL("Reversal", "CREDIT"),
    ADJUSTMENT("Adjustment", "CREDIT"),
    DISPUTE_REVERSAL("Dispute Reversal", "CREDIT"),
    
    // ==================== OTHER ====================
    LOAN_DISBURSEMENT("Loan Disbursement", "CREDIT"),
    LOAN_REPAYMENT("Loan Repayment", "DEBIT"),
    BILL_PAYMENT("Bill Payment", "DEBIT"),
    INSURANCE_PREMIUM("Insurance Premium", "DEBIT"),
    UNKNOWN("Unknown", "UNKNOWN");
    
    private final String displayName;
    private final String transactionFlow;  // CREDIT or DEBIT
    
    TransactionType(String displayName, String transactionFlow) {
        this.displayName = displayName;
        this.transactionFlow = transactionFlow;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getTransactionFlow() {
        return transactionFlow;
    }
    
    /**
     * Check if transaction increases account balance
     * @return true if transaction is a CREDIT (adds money)
     */
    public boolean isCredit() {
        return "CREDIT".equals(this.transactionFlow);
    }
    
    /**
     * Check if transaction decreases account balance
     * @return true if transaction is a DEBIT (removes money)
     */
    public boolean isDebit() {
        return "DEBIT".equals(this.transactionFlow);
    }
}