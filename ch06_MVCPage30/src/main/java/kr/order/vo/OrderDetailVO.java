package kr.order.vo;

public class OrderDetailVO {
	private long detail_num;
	private long item_num;
	private String item_name;
	private int item_price;
	private int item_total;	//동일 상품의 총주문 금액
	private int order_quantity;
	private long order_num;
	
	public long getDetail_num() {
		return detail_num;
	}
	public void setDetail_num(long detail_num) {
		this.detail_num = detail_num;
	}
	public long getItem_num() {
		return item_num;
	}
	public void setItem_num(long item_num) {
		this.item_num = item_num;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getItem_price() {
		return item_price;
	}
	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}
	public int getItem_total() {
		return item_total;
	}
	public void setItem_total(int item_total) {
		this.item_total = item_total;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	public long getOrder_num() {
		return order_num;
	}
	public void setOrder_num(long order_num) {
		this.order_num = order_num;
	}
}



