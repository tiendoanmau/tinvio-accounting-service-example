package com.tinvio.accounting.repository.template;

public interface SqlUserTemplate {

    String GET_SUPPLIER_USER_INFORMATION = "select " +
            " id, " +
            " first_name, " +
            " last_name, " +
            " phone_number, " +
            " email, " +
            " status " +
            " from supplier_user su " +
            " where id = :supplieruserid and status in ('active', 'pending')";

    String GET_MERCHANT_USER_INFORMATION = "select " +
            " id, " +
            " first_name, " +
            " last_name, " +
            " phone_number, " +
            " email, " +
            " status " +
            " from merchant_user mu " +
            " where id = :merchantuserid and status in ('active', 'pending')";

    String GET_USER_INFORMATION = "select " +
            " oc.cust_id, " +
            " ad.first_name, " +
            " ad.last_name, " +
            " oc.phone, " +
            " ad.email, " +
            " r.name as role_name " +
            " from oauth2_customer oc " +
            " join admin ad on oc.cust_id = ad.id " +
            " join role r on ad.role_id = r.id " +
            " where oc.auth_token = :authtoken and oc.active = :isactive and ad.deleted = :isdeleted";
}
