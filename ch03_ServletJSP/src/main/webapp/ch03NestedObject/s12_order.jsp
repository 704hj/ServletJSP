<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map.Entry"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 내역</title>

</head>
<body>
    <%--
구매 금액이 30만원 미만이면 배송비 5천원 추가
[출력예시]
이름: 
배송희망일:
구입 내용:
배송비:
총구매비용(배송비포함):
--%>

    <%
    //가격 정보            가방     신발    옷    식사권
    int[] orderArray = { 200000, 100000, 50000, 150000 };
    HashMap<String, Integer> orderMap = new HashMap<String, Integer>();

    int total = 0;

    String myItems = "";
    request.setCharacterEncoding("utf-8");

    orderMap.put("가방", 200000);
    orderMap.put("신발", 100000);
    orderMap.put("옷", 50000);
    orderMap.put("식사권", 150000);
%>
    이름:<%=request.getParameter("name")%><br> 
    배송희망일:<%=request.getParameter("order_date")%><br>
<%
    String[] items = request.getParameterValues("item");
    if (items != null) {
        for (int i = 0; i < items.length; i++) {
            //구매상품 금액 누적
            Integer price = orderMap.get(items[i]);
            if (price != null) {
                total += price;
            } 
            else 
            {
            // 기본값 설정 또는 예외 처리
                out.println("존재하지 않는 항목: " + items[i] + "<br>");
            }

            if (i != items.length - 1) {
                myItems += items[i] + ", ";
            } else {
                myItems += items[i];
            }
        }
    } 
    else {
        myItems = "구입 내용 없음";
    }
    %> 

    구입 내용:<%=myItems%><br> 
    배송비:<%=total >= 300000 || total == 0 ? "0원" : "5000원"%><br> 
    총구매비용(배송비포함): <%=total >= 300000 || total == 0 ? String.format("%,d", total) : String.format("%,d", total + 5000)%>원
    <br>
    
</body>
</html>