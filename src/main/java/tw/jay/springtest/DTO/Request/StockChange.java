package tw.jay.springtest.DTO.Request;

public class StockChange {
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public StockChange() {
    }

    public StockChange(int quantity) {
        this.quantity = quantity;
    }
}
