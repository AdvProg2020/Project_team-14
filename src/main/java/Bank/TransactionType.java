package Bank;

import java.io.Serializable;

public enum TransactionType implements Serializable {
    TRANSFER, WITHDRAW, DEPOSIT;
}
