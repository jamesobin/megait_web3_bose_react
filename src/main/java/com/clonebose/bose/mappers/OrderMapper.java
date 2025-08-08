package com.clonebose.bose.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    @Select("SELECT IFNULL(SUM(order_price), 0) AS order_total_price FROM orders"
            + " WHERE order_status != '취소완료' AND order_status != '결제대기중'")
    public int selectTotalOrderPrice();

}
