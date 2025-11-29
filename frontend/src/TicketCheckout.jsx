// TicketCheckout.jsx（已修正）
// 請直接覆蓋你原本的檔案並重新啟動前端（或重新載入瀏覽器分頁）

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

  // 載入票種資料（已修正：不要使用未定義的 data，並加入容錯與 debug）
  useEffect(() => {
    if (!eventId) return;

    fetch(`${BASE_API_URL}/api/eventtickettype/event_ticket_type/${eventId}`)
      .then((r) => {
        if (!r.ok) throw new Error(`無法取得票種資料，狀態碼: ${r.status}`);
        return r.json();
      })
      .then((resp) => {
        // 在這裡使用 resp（或 data），不要跳到函式外部去使用未宣告的變數
        console.log("API raw response for tickets:", resp);

        // 若 API 回傳是 { data: [...] }、{ items: [...] } 等，先嘗試取出內層陣列
        let ticketsArray = resp;
        if (resp && typeof resp === "object" && !Array.isArray(resp)) {
          ticketsArray = resp.data ?? resp.items ?? resp.tickets ?? ticketsArray;
        }

        if (!Array.isArray(ticketsArray)) {
          throw new Error("API 返回的資料格式不正確，預期為陣列，實際為: " + JSON.stringify(resp));
        }

        // map 並做多種欄位名稱容錯（依你後端 DTO，你可以縮減或調整）
        const withQty = ticketsArray.map((t) => {
          // 支援多種 description 來源（避免欄位命名差異）
          const desc =
            t.description ??
            t.desc ??
            t.note ??
            t.ticketDescription ??
            t.ticket_template?.description ??
            "";

          // 支援多種 price 欄位命名
          const price = t.customprice ?? t.price ?? t.custom_price ?? 0;

          // 優先取 id，若沒有 id 就使用 ticket_template_id 當 key（避免 key 為 undefined）
          const id = t.id ?? t.ticket_template_id ?? null;

          // 印出每筆原始物件與解析結果，方便 debug
          console.log("ticket raw:", t, "=> resolved desc:", desc, "=> id:", id, "=> price:", price);

          return {
            id: id,
            ticket_template_id: t.ticket_template_id ?? null,
            ticketType: t.ticketType ?? t.name ?? "未命名票種",
            customprice: price,
            description: desc,
            selectedQty: 0,
          };
        });

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

    try {
      console.log("開始結帳流程...");
      setMessage("已暫時保留票券，請於 3 分鐘內完成付款。");

      const checkoutItems = selected.map((t) => ({
        ticketTypeId: t.ticket_template_id, // 假設後端需要的是 id
        ticketType: t.ticketType,
        quantity: t.selectedQty,
        price: Number(t.customprice),
      }));

      const payload = {
        eventId: eventId,
        totalAmount: totalAmount,
        totalTickets: totalTickets,
        items: checkoutItems,
      };

      console.log("📝 準備傳送的結帳資料 (JSON):");
      console.log(JSON.stringify(payload, null, 2));
      console.log(payload);
      // 實際導向：window.location.href = "/payment.html";
    } catch (err) {
      setMessage("與伺服器通訊發生錯誤，請稍後再試");
      console.error(err);
    }
  }

  return (
    <div className="ticketpage">
      <h1>🎫 線上購票系統</h1>

      <div className="event-info">
        <div className="event-left">
          {/* 這是讀自己的圖片，非資料庫 */}
          <img className="event-image" alt="event" src={`${BASE_API_URL}${DEFAULT_IMAGE_URL}`} />
        </div>

        <div className="event-center">
          <h5 id="eventTitle" className="event-title">
            {event?.title || "活動標題載入中..."}
          </h5>
          <p id="eventDate">{event ? `展出期間: ${event.event_start} ~ ${event.event_end}` : ""}</p>
          <p id="eventLocation">{event ? `活動地點: ${event.address}` : ""}</p>
        </div>
      </div>

      <div className="main-content-wrapper">
        <div className="ticketzone">
          <h2>票種選擇</h2>

          <div className="ticket-layout">
            <div className="ticket-left">
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
      </div>

      <footer>頁尾區（可放版權、聯絡資訊）</footer>
    </div>
  );
}
