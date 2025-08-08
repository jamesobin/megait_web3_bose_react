package com.clonebose.bose.models;

import lombok.Data;

@Data
public class Order {
    private int orderId;                    // 주문 정보 일련번호
    private int userId;                     // 회원 정보 --> user 테이블의 user_id (FK)
    private String orderNumber;            // 주문번호 --> 주문날짜시간 + 사용자아이디
    private String receiverName;            // 수취인 이름
    private String receiverPhone;           // 수취인 전화번호
    private String receiverPostcode;        // 수취인 우편번호 --> 숫자 5자리
    private String receiverAddress;         // 수취인 주소
    private String receiverSpecificAddress; // 수취인 상세주소
    private String receiverMemo;            // 수취인 메모
    private String orderStatus;             // 주문 상태 --> ENUM("배송준비중", "배송중", "배송완료", "취소완료")
    private int orderPrice;                 // 주문 금액
    private String deliveryMethod;          // 배송 방법
    private int deliveryPrice;              // 배송 가격
    private boolean purchaseTerm;           // 구매 약관 동의 여부
    private String purchaseMethod;          // 결제 수단 --> ENUM("신용카드", "가상계좌", "실시간계좌이체", "토스페이")
    private String regDate;                 // 주문 정보를 등록한 날짜 시간 정보
    private String editDate;                // 주문 정보를 수정한 날짜 시간 정보
}
