package model.impl;

import db.DBConnection;
import dto.CustomerDto;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerModeImpl implements CustomerModel {

    @Override
    public boolean saveCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public List<CustomerDto> allCustomer() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.executeQuery();
        List<CustomerDto> list = null;
        while (pstm.executeQuery().next()){
            ResultSet rst = pstm.getResultSet();
            list.add(new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            ));
        }
        return list;
    }

    @Override
    public CustomerDto searchCustomer(String id) {
        return null;
    }
}
