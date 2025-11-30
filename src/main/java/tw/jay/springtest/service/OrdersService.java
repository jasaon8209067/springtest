package tw.jay.springtest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.jay.springtest.DTO.Request.OrderCreateRequest;
import tw.jay.springtest.DTO.Request.OrderItemRequest;
import tw.jay.springtest.DTO.Response.OrderResponse;
import tw.jay.springtest.entity.Event;
import tw.jay.springtest.entity.EventTicketType;
import tw.jay.springtest.entity.Member;
import tw.jay.springtest.entity.OrderItem;
import tw.jay.springtest.entity.Orders;
import tw.jay.springtest.repository.EventTicketTypeRep;
import tw.jay.springtest.repository.EventsRepository;
import tw.jay.springtest.repository.MemberRepository;
import tw.jay.springtest.repository.OrderItemRepository;
import tw.jay.springtest.repository.OrdersRep;

@Service
public class OrdersService {
    @Autowired
    private OrdersRep ordersRep;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private EventTicketTypeRep eventTicketTypeRep;
    @Autowired
    private EventsRepository eventsRep;
    @Autowired
    private EventTicketTypeService eventTicketTypeService;
    @Autowired
    private MemberRepository memberRepo;

    /**
     * 創建訂單並原子性地扣減庫存
     * @param request 結帳請求 DTO
     * @return OrderResponse 訂單回應 DTO
     */

    @Transactional
    public OrderResponse createOrderAndDecreaseStock(OrderCreateRequest request){
        //檢查活動是否存在
        Event event = eventsRep.findById(request.getEventId())
            .orElseThrow(() -> new RuntimeException("此活動不存在" + request.getEventId()));
        List<OrderItem> orderItems = new ArrayList<>();

        //扣庫存的票
        for (OrderItemRequest itemReq : request.getItems()){
            boolean success = eventTicketTypeService.decreaseStock(
                itemReq.getEventTicketTypeId(), itemReq.getQuantity());

                if(!success){
                    throw new RuntimeException("票種ID: " + itemReq.getEventTicketTypeId() + " 庫存不足");
                }

        // 準備 OrderItem
                EventTicketType ett = eventTicketTypeRep.findById(itemReq.getEventTicketTypeId())
                    .orElseThrow(() -> new RuntimeException("活動票種不存在:" + itemReq.getEventTicketTypeId()));

                OrderItem item = new OrderItem();
                item.setEventTicketType(ett);
                item.setQuantity(itemReq.getQuantity());
                item.setUnitPrice(itemReq.getPrice());
                orderItems.add(item);    

        }
        //建立order

        Orders order = new Orders();
        // 假設用戶 ID 邏輯已處理，這裡暫時寫死或跳過
        // order.setUser(userService.getCurrentUser());
        Member temporaryUser = new Member();
        temporaryUser.setId(2L);
        order.setUser(temporaryUser);

        order.setEvent(event);
        order.setTotalAmount(request.getTotalAmount());
        // order.setBuyerName(request.getBuyerName());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        //保存Orders
        Orders savedOrder = ordersRep.save(order);

        //關連並保存orderitem
        for(OrderItem item : orderItems){
            item.setOrder(savedOrder);
        }
        orderItemRepository.saveAll(orderItems);

        //返回DTO
        OrderResponse response = new OrderResponse();
        response.setOrderId(savedOrder.getId());
        response.setStatus(savedOrder.getStatus());
        response.setCreatedAt(savedOrder.getCreatedAt());
        // 實際應用中這裡會產生並設定跳轉到支付系統的 redirectUrl
        response.setRedirectUrl("/payment-gateway?orderId=" + savedOrder.getId());
        return response;
    }


    //後續回滾
    @Transactional
    public boolean rollbackStockByOrderId(Long orderId){
        Orders order = ordersRep.findById(orderId)
        .orElseThrow(() -> new RuntimeException("訂單不存在: " + orderId));
    
        //只有待付款的單才能回滾
        if(!"PENDING".equals(order.getStatus())){
            return false;
        }

        //獲取訂單明細，並退回庫存
        List<OrderItem> items = orderItemRepository.findByOrder_Id(orderId);

        // 假設在 OrdersItemRep 中有 findByOrderId 方法 或使用 Order 實體的 @OneToMany
        // ordersItemRep.findByOrderId(orderId); // 暫時使用 findByOrderId
        // 或者使用 orders.getItems()，但這要求 Orders 實體中配置了 @OneToMany
        boolean success = true;
        for (OrderItem item : items) {
             boolean increased = eventTicketTypeService.increaseStock(
                item.getEventTicketType().getId(), 
                item.getQuantity()
             );
             if (!increased) {
                 success = false;
                 // 記錄錯誤，但不影響其他項目的回滾
                 System.err.println("庫存回滾失敗Item ID: " + item.getId()); 
             }
        }
        
        // 更新訂單狀態為已取消
        if (success) {
            order.setStatus("CANCELLED");
            ordersRep.save(order);
        }
        
        
        
        return success;
    }

}
