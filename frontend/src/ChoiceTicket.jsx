import React, { useEffect, useState } from "react";
import "./App.css"; // 你原本的 CSS

export default function CheckoutPage() {
  const [event, setEvent] = useState(null);
  const [tickets, setTickets] = useState([]);
  const [selectedTickets, setSelectedTickets] = useState({});
  const [selectedDate, setSelectedDate] = useState("");

  // 取得活動資料
  useEffect(() => {
    fetch("http://localhost:8080/events/5")
      .then(res => res.json())
      .then(data => setEvent(data))
      .catch(err => console.error(err));
  }, []);

  // 取得票種資料
  useEffect(() => {
    fetch("http://localhost:8080/api/tickets")
      .then(res => res.json())
      .then(data => setTickets(data))
      .catch(err => console.error(err));
  }, []);

  // 計算總金額
  const totalPrice = tickets.reduce((sum, t) => {
    const count = Number(selectedTickets[t.name] || 0);
    return sum + t.price * count;
  }, 0);

  // 計算總票數與票種文字
  const totalTickets = tickets.reduce((sum, t) => sum + Number(selectedTickets[t.name] || 0), 0);
  const selectedTicketsText = tickets
    .filter(t => selectedTickets[t.name])
    .map(t => `${t.name} ${selectedTickets[t.name]}張`)
    .join(" / ");

  // 計算可選日期
  const getAvailableDates = () => {
    if (!event) return [];
    const startDate = new Date(event.event_start);
    const endDate = new Date(event.event_end);
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const dates = [];
    for (let d = new Date(startDate); d <= endDate; d.setDate(d.getDate() + 1)) {
      if (d >= today) {
        dates.push(d.toISOString().slice(0, 10));
      }
    }
    return dates;
  };

  return (
    <>
      {/* 頁首 */}
      <header>🎫 線上購票系統</header>

      {/* 活動資訊 */}
      {event && (
        <div className="event-info">
          <div className="event-left">
            <img
              src={`data:image/jpeg;base64,${event.image}`}
              alt="event"
              className="event-image"
            />
          </div>
          <div className="event-center">
            <h5 className="event-title">{event.title}</h5>
            <p>展出期間: {event.event_start} ~ {event.event_end}</p>
            <p>活動地點: {event.address}</p>
          </div>
          <div className="event-right">
            <label htmlFor="datePicker">選擇日期: </label>
            <select
              id="datePicker"
              className="event-date-select"
              value={selectedDate}
              onChange={e => setSelectedDate(e.target.value)}
            >
              <option value="">請選擇日期</option>
              {getAvailableDates().map(date => (
                <option key={date} value={date}>{date}</option>
              ))}
            </select>
          </div>
        </div>
      )}

      {/* 購票區 */}
      <div className="ticketzone">
        <h2>票種選擇</h2>
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
            {tickets.map(ticket => (
              <tr key={ticket.name}>
                <td>{ticket.name}</td>
                <td>{ticket.price}</td>
                <td>
                  <select
                    value={selectedTickets[ticket.name] || ""}
                    onChange={e =>
                      setSelectedTickets({
                        ...selectedTickets,
                        [ticket.name]: e.target.value,
                      })
                    }
                  >
                    <option value="">請選擇張數</option>
                    {[1, 2, 3, 4, 5].map(n => (
                      <option key={n} value={n}>{n}</option>
                    ))}
                  </select>
                </td>
                <td>{ticket.remark || ""}</td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* 總計區 */}
        <div className="totalfee">
          <div>票種: {selectedTicketsText}</div>
          <div>總共張數: {totalTickets}</div>
          <hr />
          <div><strong>總金額: NT${totalPrice}</strong></div>
          <div style={{ marginTop: "10px" }}>
            <button className="btn">前往結帳</button>
          </div>
        </div>
      </div>

      <footer>頁尾區（可放版權、聯絡資訊）</footer>
    </>
  );
}
