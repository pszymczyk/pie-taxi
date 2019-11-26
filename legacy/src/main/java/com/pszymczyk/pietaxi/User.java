package com.pszymczyk.pietaxi;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

class User {

    public enum Status {
        ACTIVE,
        BLOCKED;
    }

    public enum MarketingAgreements {
        MAIL_CONTACT,
        PHONE_CONTACT,
        NEW_OFFERS,
        PROMOTION_CODES,
        POOLS
    }

    private final UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String[] address;
    private BigDecimal debt;
    private BigDecimal limit;
    private Status status;
    private Set<MarketingAgreements> agreements;
    private MobilePhone mobilePhone;

    public User(UUID id) {
        this.id = id;
    }

    public void charge(BigDecimal money) {
        debt = debt.add(money);
        if (debtGreaterOrEqualToLimit()) {
            blockAccount();
        }
    }

    private void blockAccount() {
        status = Status.BLOCKED;
    }

    private boolean debtGreaterOrEqualToLimit() {
        return debt.compareTo(limit) >= 0;
    }

    public void supply(BigDecimal supply) {
        if (debt.compareTo(supply) < 0) {
            overpayment(supply);
        } else {
            debt = debt.subtract(supply);
            if (status == Status.BLOCKED && debt.compareTo(limit) < 0) {
                activateAccount();
            }
        }
    }

    private void overpayment(BigDecimal supply) {
        debt = BigDecimal.ZERO;

        if (status == Status.BLOCKED) {
            activateAccount();
        }
    }

    private void activateAccount() {
        status = Status.ACTIVE;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<MarketingAgreements> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<MarketingAgreements> agreements) {
        this.agreements = agreements;
    }

    public MobilePhone getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(MobilePhone mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
