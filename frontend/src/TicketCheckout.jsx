import React, { useEffect, useState } from "react";
import "./styles.css";

// **** 請在這裡設定您的 Spring Boot 基礎 URL ****
const BASE_API_URL = 'http://localhost:8080';
// **********************************************

const DEFAULT_IMAGE_URL = "/images/test.jpg";

export default function TicketCheckout() {
  const params = new URLSearchParams(window.location.search);
  const eventId = Number(params.get("eventId")) || 0;

  const [event, setEvent] = useState(null);
  const [tickets, setTickets] = useState([]);
  const [message, setMessage] = useState("");

  const totalAmount = tickets.reduce(
    (acc, t) => acc + (t.selectedQty || 0) * Number(t.customprice || 0),
    0
  );
  const totalTickets = tickets.reduce((acc, t) => acc + (t.selectedQty || 0), 0);
  const selectedTicketText = tickets
    .filter((t) => t.selectedQty > 0)
    .map((t) => `${t.ticketType} ${t.selectedQty}張`)
    .join("/");

  // 載入活動資料
  useEffect(() => {
    if (!eventId) return;
    fetch(`${BASE_API_URL}/api/events/${eventId}`)
      .then((r) => {
        if (!r.ok) throw new Error("無法取得活動資料");
        return r.json();
      })
      .then((data) => setEvent(data))
      .catch((err) => {
        console.error(err);
        setMessage("讀取活動資料發生錯誤：" + err.message);
      });
  }, [eventId]);

  // 載入票種資料
  useEffect(() => {
    // 這裡使用 /api/tickets/status 獲取所有啟用票種
    fetch(`${BASE_API_URL}/api/eventtickettype/event_ticket_type/${eventId}`) 
      .then((r) => {
        if (!r.ok) throw new Error(`無法取得票種資料，狀態碼: ${r.status}`);
        return r.json();
      })
      .then((data) => {
        if (!Array.isArray(data)) {
            throw new Error("API 返回的資料格式不正確，預期為陣列。");
        }
        const withQty = data.map((t) => ({
          id: t.id ?? null,
          ticket_template_id: t.ticket_template_id,
          ticketType: t.ticketType,
          customprice: t.customprice,
          description: t.description || "",
          selectedQty: 0, 
        }));
        setTickets(withQty);
      })
      .catch((err) => {
        console.error(err);
        setMessage("讀取票種資料時發生錯誤: " + err.message);
      });
  }, [eventId]); 

  // 處理票數變更
  function handleQtyChange(ticketId, qty) {
    setTickets((prev) => prev.map((t) => (t.id === ticketId ? { ...t, selectedQty: qty } : t)));
  }

  // 處理結帳流程
  async function handleCheckout(e) {
    e.preventDefault();
    setMessage("");
    const selected = tickets.filter((t) => t.selectedQty > 0);
    if (selected.length === 0) {
      alert("請選擇至少一張票。");
      return;
    }

    // 實際結帳邏輯應放置於此
    try {
      console.log("開始結帳流程...");
      setMessage("已暫時保留票券，請於 3 分鐘內完成付款。");
        // 1. 處理需要傳送的票種資料格式
      const checkoutItems = selected.map(t => ({
          ticketTypeId: t.ticket_template_id, // 假設後端需要的是 id
          ticketType: t.ticketType,
          quantity: t.selectedQty,
          price: Number(t.customprice) // 確保是數字
      }));

      // 2. 建構最終要傳送的 JSON 物件
      const payload = {
          eventId: eventId,
          totalAmount: totalAmount,
          totalTickets: totalTickets,
          items: checkoutItems
      };
      
      // 3. 在 Console 顯示 JSON 格式的資料
      console.log("📝 準備傳送的結帳資料 (JSON):");
      console.log(JSON.stringify(payload, null, 2)); // 使用 JSON.stringify 格式化輸出
      console.log(payload); // 輸出物件方便檢查
      // 實際導向：window.location.href = "/payment.html";

    } catch (err) {
      setMessage("與伺服器通訊發生錯誤，請稍後再試");
      console.error(err);
    }
  }

  return (
    <div className="ticketpage">

      {/* 標題：佔滿全寬 */}
      <h1>🎫 線上購票系統</h1>

      {/* 活動資訊區：佔滿全寬 */}
      <div className="event-info">
        <div className="event-left">
{/*           {event && event.image ? (
            <img 
                className="event-image" 
                alt="event" 
                src={`${BASE_API_URL}${event.image}`} 
            />
          ) : (
            <div style={{ width: 120, height: 80, background: "#eee" }} />
          )}
        </div> */}

        {/*這是讀自己的圖片，非資料庫*/}
        <img 
              className="event-image" 
              alt="event" 
              src={`${BASE_API_URL}${DEFAULT_IMAGE_URL}`} 
          />
        </div>

        <div className="event-center">
          <h5 id="eventTitle" className="event-title">
            {event?.title || "活動標題載入中..."}
          </h5>
          <p id="eventDate">{event ? `展出期間: ${event.event_start} ~ ${event.event_end}` : ""}</p>
          <p id="eventLocation">{event ? `活動地點: ${event.address}` : ""}</p>
        </div>
      </div>

      {/* 核心內容置中容器：只限制購票區的寬度 (保持不變) */}
      <div className="main-content-wrapper">

        {/* 購票區 (表格區) */}
        <div className="ticketzone">
          <h2>票種選擇</h2>

          <div className="ticket-layout">

            {/* 左側表格容器 */}
            <div className="ticket-left">
              {/* 移除 style 屬性，交給 CSS 管理邊框 */}
              <table className="tickets">
                <thead>
                  <tr>
                    <th>票種</th>
                    <th>票價</th>
                    <th>數量</th>
                    <th>備註</th>
                  </tr>
                </thead>
                <tbody>
                  {tickets.length === 0 ? (
                    <tr>
                      <td colSpan="4">票種載入中或無票種資料</td>
                    </tr>
                  ) : (
                    tickets.map((t) => (
                      <tr key={t.id ?? t.ticketType} data-ticket-id={t.id ?? ""}>
                        <td>{t.ticketType}</td>
                        <td>{t.customprice}</td>
                        <td>
                          <select
                            className="ticketselct"
                            value={t.selectedQty}
                            onChange={(e) => handleQtyChange(t.id, Number(e.target.value))}
                            data-price={t.customprice}
                          >
                            <option value={0}>請選擇張數</option>
                            <option value={1}>1</option>
                            <option value={2}>2</option>
                            <option value={3}>3</option>
                            <option value={4}>4</option>
                          </select>
                        </td>
                        <td>{t.description}</td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>
          </div>

          <div id="message" style={{ marginTop: 12 }}>{message}</div>
        </div>
        
        {/* *** 關鍵變更: totalfee-fixed 移到 ticketzone 區塊下方 *** */}
        <aside className="totalfee-fixed">
          <div>
              票種: <span id="tickettype">{selectedTicketText}</span>
          </div>
          <div>總共張數: <span id="toatltickets">{`總共${totalTickets}張`}</span></div>
          <hr />
          <div>
              <strong>總金額: <span id="total">NT${totalAmount}</span></strong>
          </div>
          <div style={{ marginTop: 10 }}>
              <button className="btn" id="checkoutBtn" onClick={handleCheckout}>前往結帳</button>
          </div>
        </aside>
        {/* *** totalfee-fixed 結束 *** */}

      </div>
      {/* 核心內容置中容器 END */}

      <footer>頁尾區（可放版權、聯絡資訊）</footer>
    </div>
  );
}