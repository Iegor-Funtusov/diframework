package org.diframework.dao;

import org.diframework.core.crud.dao.BaseDao;
import org.diframework.entity.Customer;

public interface CustomerDao extends BaseDao<Integer, Customer> {

    boolean existByEmail(String email);
}
