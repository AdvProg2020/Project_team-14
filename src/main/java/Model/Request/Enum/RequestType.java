package Model.Request.Enum;

import java.io.Serializable;

public enum RequestType implements Serializable {
    REGISTER_SALESMAN, ADD_NEW_PRODUCT, CHANGE_PRODUCT, ADD_NEW_SALE, CHANGE_SALE,
    DELETE_PRODUCT, DELETE_SALE, COMMENT_CONFIRMATION, ADD_TO_PRODUCT;
}

